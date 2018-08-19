package controllers

import com.google.inject.Inject
import javax.inject.Singleton
import models.Format._
import models._
import play.api.Configuration
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.{JsArray, Json, OFormat}
import play.api.libs.ws.WSClient
import play.api.mvc._
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random
import models.OldTables$.profile.api._
import play.api.db.NamedDatabase
import service.AuthService

// import scala.collection.JavaConverters._

@Singleton
class Application @Inject()(cc: MessagesControllerComponents,
                            database: MyDatabase,
                            auth: AuthService,
                            config: Configuration,
                            ws: WSClient,
                            @NamedDatabase("mysql") protected val dbConfigProvider: DatabaseConfigProvider)
                           (implicit ec: ExecutionContext) extends MessagesAbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  final val baseUrl = config.get[String]("es.baseUrl")

  case class User(username: String, password: String)

  implicit val userFormat: OFormat[User] = Json.format[User]

  def index: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok("GG"))
  }

  def loginAuth[A](action: Action[A]): Action[A] = Action.async(action.parser) { request =>
    request.headers.get("Authorization") match {
      case Some(token) =>
        if (auth.isValidToken(token))
          action(request)
        else
          Future.successful(Unauthorized("Invalid credential"))
      case None =>
        Future.successful(Forbidden("Only Authorized requests allowed"))
    }
  }

  def authLogin(implicit ec: ExecutionContext): ActionRefiner[Request, UserRequest] = new ActionRefiner[Request, UserRequest] {
    override protected def refine[A](request: Request[A]): Future[Either[Result, UserRequest[A]]] = {
      Future.successful {
        request.headers.get("Authorization") match {
          case Some(token) =>
            if (auth.isValidToken(token)) {
              val username = Json.parse(auth.decodePayload(token).get).\("subject").as[String]
              Right(new UserRequest(username, request))
            } else
              Left(Unauthorized("Invalid credential"))
          case None =>
            Left(Forbidden("Only Authorized requests allowed"))
        }
      }
    }

    override protected def executionContext: ExecutionContext = ec
  }


  def login: Action[AnyContent] = Action.async { request =>
    val user = request.body.asJson.get.as[UserBean]
    database.getUserByUsername(user.username, user.password).map {
      case Some(u) =>
        val jwt = auth.createToken(Json.toJson(Token(u.username, 1800)).toString())
        val token = LoginRes(0, Some(jwt), Some(1800), u.nickname)
        Ok(Json.toJson(token)).as(JSON)
      case None =>
        Ok(Json.toJson(LoginRes(1, None, None, None))).as(JSON)
    }
  }

  def register: Action[AnyContent] = Action.async { implicit request =>
    val registerInput = request.body.asJson.get.as[RegisterInput]
    database.getUserByUsernameOrMail(registerInput.username, registerInput.mail).flatMap {
      case Some(u) =>
        if (u.username.equals(registerInput.username))
          Future.successful(Ok(Json.toJson(CommonRes(1, "此用户名已被其他用户注册。"))).as(JSON))
        else
          Future.successful(Ok(Json.toJson(CommonRes(2, "此e-mail地址已被其他用户注册。"))).as(JSON))
      case None =>
        database.addUser(registerInput.username, registerInput.mail, registerInput.password).map {
          case 1 => Ok(Json.toJson(CommonRes(0, "注册成功！"))).as(JSON)
          case _ => Ok(Json.toJson(CommonRes(3, "未知错误。"))).as(JSON)
        }
    }
  }

  def query(key: String, kind: Int, sort: Int): Action[AnyContent] = Action.async { implicit request =>
    var sc: String = "desc"
    var s: String = "id"
    sort match {
      case 1 => s = "foundTime"; sc = "asc"
      case 2 => s = "foundTime"; sc = "desc"
      case 3 => s = "money"; sc = "asc"
      case 4 => s = "money"; sc = "desc"
      case _ => s = "_score"; sc = "desc"
    }
    kind match {
      case 0 =>
        ws.url(baseUrl + "data/company/_search").withBody(Json.toJson(Json.obj(
          "query" -> Json.obj("multi_match" -> Json.obj("query" -> key, "fields" -> Json.arr("name", "represent"))),
          "sort" -> Json.obj(s -> Json.obj("order" -> sc))
        ))).get().map(x => Ok(Json.parse(x.body).\("hits").\("hits").as[JsArray]))
      case 1 =>
        // 查找公司
        ws.url(baseUrl + "data/company/_search").withBody(Json.obj(
          "query" -> Json.obj("match" -> Json.obj("name" -> key)),
          "sort" -> Json.obj(s -> Json.obj("order" -> sc)), "size" -> 100)).get()
          .map(x => Ok(x.json.\("hits").\("hits").as[JsArray]))
      case 2 =>
        ws.url(baseUrl + "data/company/" + key).delete().map(x => Ok(x.body))
      case 3 =>
        database.getCompanyById(key.toInt).map(Json.toJson(_)).flatMap { data =>
          ws.url(baseUrl + "data/company/" + key).put(data)
        }.map(x => Ok(x.body))
      case 4 =>
        ws.url(baseUrl + "data/company/_search").withBody(Json.toJson(Json.obj(
          "query" -> Json.obj("match" -> Json.obj("name" -> key))))).get()
          .map(x => Ok(Json.parse(x.body)))
      case _ => Future.successful(Ok)
    }
  }

  // mov
  def getCompanyInfo(id: Int): Action[AnyContent] = Action.async { implicit request =>
    // 所有的信息查询都是异步的 下面九个数据库查询语句都是同时进行的
    // 实际查询的效果比同步查询越快7-8倍 查询的量越多效果越明显
    val companyFuture = database.getCompanyById(id)
    val basicInfoFuture = database.getCompanyInfoById(id)
    val shareholderInformationFuture = database.getShareholderInformationById(id)
    val outboundInvestmentFuture = database.getOutboundInvestmentById(id)
    val branchFuture = database.getBranchById(id)
    val changeRecordFuture = database.getChangeRecordById(id)
    val mainPersonFuture = database.getMainPersonnelById(id)
    val companyProfileFuture = database.getCompanyProfileById(id)
    val bossFuture = database.getBossById(id)

    // 等到最后一个查询语句完成 会自动应用已经查询好的数据 组合成前端需要的Json数据
    val json = for {
      company <- companyFuture
      basicInfo <- basicInfoFuture
      shareholderInformation <- shareholderInformationFuture
      outboundInvestment <- outboundInvestmentFuture
      branch <- branchFuture
      changeRecord <- changeRecordFuture
      mainPersonnel <- mainPersonFuture
      companyProfile <- companyProfileFuture
      boss <- bossFuture
    } yield {
      // 组合查询的数据 并使用json序列化对象
      Json.obj("company" -> company,
        "basicInfo" -> basicInfo,
        "shareholderInformation" -> shareholderInformation,
        "outboundInvestment" -> outboundInvestment,
        "branch" -> branch,
        "changeRecord" -> changeRecord,
        "mainPersonnel" -> mainPersonnel,
        "companyProfile" -> companyProfile,
        "boss" -> boss
      )
    }
    json.map(Ok(_))
  }

  def getInterestedPeople: Action[AnyContent] = Action.async { implicit request =>
    database.getInterestedPeople.map(data => getRandomList(data.length).map(data.apply)).map(x => Ok(Json.toJson(x)))
  }

  def getRandomList(n: Int): List[Int] = (1 to n * 10)
    .map(_ => Random.nextInt(n))
    .++(0 until n)
    .groupBy(x => x)
    .mapValues(_.size).toList
    .sortBy(_._2).map(_._1)


  // mov
  def getEnterpriseGraph(id: Int): Action[AnyContent] = Action.async { implicit request =>
    database.getEnterpriseGraphById(id).map(x => Ok(Json.toJson(x)))
  }

  // mov
  def getInvestmentGraph(id: Int): Action[AnyContent] = Action.async { implicit request =>
    database.getInvestmentGraphById(id).map(x => Ok(Json.toJson(x)))
  }

  // mov
  def getAssociationGraph(id: Int): Action[AnyContent] = Action.async { implicit request =>
    database.getAssociationGraphById(id).map { x =>
      Ok(Json.obj("nodes" -> x._1, "links" -> x._2))
    }
  }

  // mov
  def getEquityStructureGraph(id: Int): Action[AnyContent] = Action.async { implicit request =>
    //val sql = Tables.Temp.filter()
    database.getEquityStructureGraphById(id).map(x => Ok(Json.toJson(x)))
  }

  // mov
  def getSuspectedController(id: Int): Action[AnyContent] = Action.async { implicit request =>
    val company = database.getCompanyById(id)
    val person = database.getSuspectedControllerById(id)
    for {
      c <- company
      p <- person
    } yield {
      Ok(Json.obj("person" -> p, "company" -> c))
    }
  }

  def getPersonalGraph(id: Int, kind: Int): Action[AnyContent] = Action.async { implicit request =>
    val sql = OldTables$.Temp.filter(x => x.id === id && x.kind === kind).result
    db.run(sql).map(x => Ok(x.head.data.get).as(JSON))
  }
}
