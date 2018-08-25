package controllers

import com.google.inject.Inject
import crawler.Crawler
import models.Entities._
import models.Tables
import models.Tables._
import models.Tables.profile.api._
import play.api.Configuration
import play.api.db.NamedDatabase
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.{JsArray, Json}
import play.api.libs.ws.WSClient
import play.api.mvc._
import service.AuthService
import slick.jdbc.JdbcProfile
import util.Formats._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

class Api @Inject()(cc: MessagesControllerComponents,
                    crawler: Crawler,
                    auth: AuthService,
                    config: Configuration,
                    ws: WSClient,
                    @NamedDatabase("server") protected val dbConfigProvider: DatabaseConfigProvider)
                   (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  final lazy val baseUrl = config.get[String]("es.baseUrl")

  def index: Action[AnyContent] = Action.async { _ =>
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

  def query(key: String, kind: Int, sort: Int, size: Int = 100): Action[AnyContent] = Action.async { _ =>
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
      // 全部查询
      case 0 =>
        ws.url(baseUrl + "data/company/_search").withBody(Json.toJson(Json.obj(
          "query" -> Json.obj("multi_match" -> Json.obj("query" -> key,
            "fields" -> Json.arr("name", "representname", "phone", "addr",
              "main_personnel.name", "shareholder_information.shareholderName"))),
          "sort" -> Json.obj(s -> Json.obj("order" -> sc)), "size" -> size))).get()
          .map(x => Ok(Json.parse(x.body).\("hits").\("hits").as[JsArray]))
      case 1 =>
        // 查找公司
        ws.url(baseUrl + "data/company/_search").withBody(Json.obj(
          "query" -> Json.obj("match" -> Json.obj("name" -> key)),
          "sort" -> Json.obj(s -> Json.obj("order" -> sc)), "size" -> size)).get()
          .map(x => Ok(x.json.\("hits").\("hits").as[JsArray]))
      case 2 =>
        // 查找法人和股东
        ws.url(baseUrl + "data/company/_search").withBody(Json.toJson(Json.obj(
          "query" -> Json.obj("multi_match" -> Json.obj("query" -> key,
            "fields" -> Json.arr("representname", "shareholder_information.shareholderName"))),
          "sort" -> Json.obj(s -> Json.obj("order" -> sc)), "size" -> size
        ))).get().map(x => Ok(Json.parse(x.body).\("hits").\("hits").as[JsArray]))
      case 3 =>
        // 查找高管
        ws.url(baseUrl + "data/company/_search").withBody(Json.obj(
          "query" -> Json.obj("match" -> Json.obj("main_personnel.name" -> key)),
          "sort" -> Json.obj(s -> Json.obj("order" -> sc)), "size" -> size)).get()
          .map(x => Ok(x.json.\("hits").\("hits").as[JsArray]))
      case 4 =>
        // 查找品牌和产品
        ws.url(baseUrl + "brand/doc/_search").withBody(Json.obj(
          "query" -> Json.obj("match" -> Json.obj("name" -> key)), "size" -> size)).get()
          .map(x => x.json.\("hits").\("hits").as[JsArray].value.map(_.\("_source").\("cid").asOpt[Int]))
          .flatMap(data => db.run(Company.filter(_.id inSet data.flatten.distinct).result)
            .map(x => Ok(Json.toJson(x.map(z => Json.obj("_source" -> z))))))
      case 5 =>
        // 查找地址和电话
        ws.url(baseUrl + "data/company/_search").withBody(Json.toJson(Json.obj(
          "query" -> Json.obj("multi_match" -> Json.obj("query" -> key,
            "fields" -> Json.arr("addr", "phone"))),
          "sort" -> Json.obj(s -> Json.obj("order" -> sc)), "size" -> size
        ))).get().map(x => Ok(Json.parse(x.body).\("hits").\("hits").as[JsArray]))
      case 6 =>
        // 查找经营范围
        ws.url(baseUrl + "data/company/_search").withBody(Json.toJson(Json.obj(
          "query" -> Json.obj("match" -> Json.obj("scope_of_operation" -> key)), "size" -> size))).get()
          .map(x => Ok(Json.parse(x.body)))
      case _ => Future.successful(NoContent)
    }
  }

  def login: Action[AnyContent] = Action.async { request =>
    val user = request.body.asJson.get.as[UserBean]
    db.run(Tables.User.filter(x => x.username === user.username && x.password === user.password).result.headOption).map {
      case Some(u) =>
        val jwt = auth.createToken(Json.toJson(Token(u.username, 1800)).toString())
        val token = LoginRes(0, Some(jwt), Some(1800), u.nickname)
        Ok(Json.toJson(token))
      case None =>
        Ok(Json.toJson(LoginRes(1, None, None, None)))
    }
  }

  def register: Action[AnyContent] = Action.async { implicit request =>
    val registerInput = request.body.asJson.get.as[RegisterInput]
    db.run(Tables.User.filter(x => x.username === registerInput.username || x.mail === registerInput.mail).result.headOption).flatMap {
      case Some(u) =>
        if (u.username.equals(registerInput.username))
          Future.successful(Ok(Json.toJson(CommonRes(1, "此用户名已被其他用户注册。"))).as(JSON))
        else
          Future.successful(Ok(Json.toJson(CommonRes(2, "此e-mail地址已被其他用户注册。"))).as(JSON))
      case None =>
        db.run(Tables.User += UserRow(0, registerInput.username, registerInput.password, None, registerInput.mail)).map {
          case 1 => Ok(Json.toJson(CommonRes(0, "注册成功！")))
          case _ => Ok(Json.toJson(CommonRes(3, "未知错误。")))
        }
    }
  }

  def getAllCompany: Action[AnyContent] = Action.async { _ =>
    crawler.forSearchPage("苏州朗动网络科技有限公司", 2)
    Future.successful(Ok)
  }

  def getCompanyBaseInfo(id: Int): Action[AnyContent] = Action.async { _ =>
    db.run(Company.filter(_.id === id).result.head).flatMap { c =>
      if (c.keyno.isEmpty) {
        crawler.forBaseInfo(c.ref.get, c.id)
        Future.successful(Ok(Json.obj("status" -> false)))
      } else {
        val main_personnelFuture = db.run(MainPersonnel.filter(_.cid === id).result)
        val outbound_investmentFuture = db.run(OutboundInvestment.filter(_.cid === id).result)
        val change_recordFuture = db.run(ChangeRecord.filter(_.cid === id).result)
        val branchFuture = db.run(Branch.filter(_.cid === id).result)
        val shareholder_informationFuture = db.run(ShareholderInformation.filter(_.cid === id).result)
        val basic_infoFuture = db.run(BasicInfo.filter(_.cid === id).result.head)
        val boss_infoFuture = db.run(Person.filter(_.id === c.represent.get).result.head)

        for {
          main_personnel <- main_personnelFuture
          outbound_investment <- outbound_investmentFuture
          change_record <- change_recordFuture
          branch <- branchFuture
          shareholder_information <- shareholder_informationFuture
          basic_info <- basic_infoFuture
          boss_info <- boss_infoFuture
        } yield {
          val json = Json.obj(
            "status" -> true,
            "main_personnel" -> main_personnel,
            "outbound_investment" -> outbound_investment,
            "change_record" -> change_record,
            "branch" -> branch,
            "shareholder_information" -> shareholder_information,
            "company" -> c,
            "base_info" -> basic_info,
            "boss_info" -> boss_info
          )
          Ok(json)
        }
      }
    }
  }

  def getLegalAction(id: Int): Action[AnyContent] = Action.async { _ =>
    db.run(Company.filter(_.id === id).result.head).flatMap { c =>
      val RefereeFuture = db.run(Referee.filter(_.cid === id).result)
      val CourtNoticeFuture = db.run(CourtNotice.filter(_.cid === id).result)
      val OpeningNoticeFuture = db.run(OpeningNotice.filter(_.cid === id).result)

      for {
        referee <- RefereeFuture
        courtNotice <- CourtNoticeFuture
        openingNotice <- OpeningNoticeFuture
      } yield {
        val length = referee.size + courtNotice.size + openingNotice.size
        if (length == 0) crawler.forLegalAction(c.keyno.get, c.name, c.id)
        val json = Json.obj(
          "status" -> true,
          "referee" -> referee,
          "courtNotice" -> courtNotice,
          "openingNotice" -> openingNotice,
        )
        Ok(json)
      }
    }
  }

  def getOperatingConditions(id: Int): Action[AnyContent] = Action.async { _ =>
    db.run(Company.filter(_.id === id).result.head).flatMap { c =>
      val AdministrativeLicenseIcFuture = db.run(AdministrativeLicenseIc.filter(_.cid === id).result)
      val AdministrativeLicenseChFuture = db.run(AdministrativeLicenseCh.filter(_.cid === id).result)
      val TaxCreditFuture = db.run(TaxCredit.filter(_.cid === id).result)
      val ProductInformationFuture = db.run(ProductInformation.filter(_.cid === id).result)
      val FinancingInformationFuture = db.run(FinancingInformation.filter(_.cid === id).result)
      val BiddingInformationFuture = db.run(BiddingInformation.filter(_.cid === id).result)
      val RecruitmentFuture = db.run(Recruitment.filter(_.cid === id).result)
      val PublicNumberFuture = db.run(PublicNumber.filter(_.cid === id).result)
      val NewsLyricsFuture = db.run(NewsLyrics.filter(_.cid === id).result)

      for {
        administrativeLicenseIc <- AdministrativeLicenseIcFuture
        administrativeLicenseCh <- AdministrativeLicenseChFuture
        taxCredit <- TaxCreditFuture
        productInformation <- ProductInformationFuture
        financingInformation <- FinancingInformationFuture
        biddingInformation <- BiddingInformationFuture
        recruitment <- RecruitmentFuture
        publicNumber <- PublicNumberFuture
        newsLyrics <- NewsLyricsFuture
      } yield {
        val length = administrativeLicenseIc.size + administrativeLicenseCh.size + taxCredit.size
        +productInformation.size + financingInformation.size
        +biddingInformation.size + recruitment.size
        +publicNumber.size + newsLyrics.size
        if (length == 0) crawler.forOperatingConditions(c.keyno.get, c.name, c.id)
        val json = Json.obj(
          "status" -> true,
          "administrativeLicenseIc" -> administrativeLicenseIc,
          "administrativeLicenseCh" -> administrativeLicenseCh,
          "taxCredit" -> taxCredit,
          "productInformation" -> productInformation,
          "financingInformation" -> financingInformation,
          "biddingInformation" -> biddingInformation,
          "recruitment" -> recruitment,
          "publicNumber" -> publicNumber,
          "newsLyrics" -> newsLyrics,
        )
        Ok(json)
      }
    }
  }

  def getBossInfo(id: Int): Action[AnyContent] = Action.async { _ =>
    db.run(Person.filter(_.id === id).result.head).flatMap { person =>
      if (!person.flag.get) {
        crawler.forBossInfo(person.addr.get, person.id)
        Future.successful(Ok(Json.obj("status" -> false)))
      } else {
        val boss_history_investmentFuture = db.run(BossHistoryInvestment.filter(_.bid === id).result)
        val boss_history_positionFuture = db.run(BossHistoryPosition.filter(_.bid === id).result)
        val boss_history_representFuture = db.run(BossHistoryRepresent.filter(_.bid === id).result)
        val boss_holding_companyFuture = db.run(BossHoldingCompany.filter(_.bid === id).result)
        val boss_investmentFuture = db.run(BossInvestment.filter(_.bid === id).result)
        val boss_positionFuture = db.run(BossPosition.filter(_.bid === id).result)
        val boss_representFuture = db.run(BossRepresent.filter(_.bid === id).result)

        for {
          boss_history_investment <- boss_history_investmentFuture
          boss_history_position <- boss_history_positionFuture
          boss_history_represent <- boss_history_representFuture
          boss_holding_company <- boss_holding_companyFuture
          boss_investment <- boss_investmentFuture
          boss_position <- boss_positionFuture
          boss_represent <- boss_representFuture
        } yield {
          val json = Json.obj(
            "status" -> true,
            "person" -> person,
            "boss_history_investment" -> boss_history_investment,
            "boss_history_position" -> boss_history_position,
            "boss_history_represent" -> boss_history_represent,
            "boss_holding_company" -> boss_holding_company,
            "boss_investment" -> boss_investment,
            "boss_position" -> boss_position,
            "boss_represent" -> boss_represent
          )
          Ok(json)
        }
      }
    }
  }

  def getBossGraph(id: Int): Action[AnyContent] = Action.async { _ =>
    val personFuture = db.run(Person.filter(_.id === id).result.head)
    val representFuture = db.run(BossRepresent.filter(_.bid === id).result)
    val positionFuture = db.run(BossPosition.filter(_.bid === id).result)
    val investmentFuture = db.run(BossInvestment.filter(_.bid === id).result)

    for {
      person <- personFuture
      represent <- representFuture
      position <- positionFuture
      investment <- investmentFuture
    } yield {
      val r = represent.map(x => Children(x.name.get, None, None))
      val i = investment.map(x => Children(x.name.get, x.ratio, None))
      val p = position.map(x => Children(x.name.get, x.position, None))
      val data = Children(person.name, None, Some(Seq(Children("担任法定代表人", None, Some(r)),
        Children("对外投资", None, Some(i)),
        Children("在外任职", None, Some(p)))))

      Ok(Json.toJson(data))
    }
  }

  def getEquityStructureGraph(id: Int): Action[AnyContent] = Action.async { _ =>
    db.run(CompanyGraph.filter(x => x.cid === id && x.`type` === 0).result.headOption).flatMap {
      case Some(json) =>
        Future.successful(Ok(json.data.get).as(JSON))
      case None =>
        db.run(Company.filter(_.id === id).result.head)
          .flatMap(x => crawler.forEquityStructureGraph(x.keyno.get, id).map(Ok(_)))
    }
  }

  def getEnterpriseGraph(id: Int): Action[AnyContent] = Action.async { _ =>
    db.run(CompanyGraph.filter(x => x.cid === id && x.`type` === 1).result.headOption).flatMap {
      case Some(json) =>
        Future.successful(Ok(json.data.get).as(JSON))
      case None =>
        db.run(Company.filter(_.id === id).result.head)
          .flatMap(x => crawler.forEnterpriseGraph(x.keyno.get, id).map(Ok(_)))
    }
  }

  def getInvestmentGraph(id: Int): Action[AnyContent] = Action.async { _ =>
    db.run(CompanyGraph.filter(x => x.cid === id && x.`type` === 2).result.headOption).flatMap {
      case Some(json) =>
        Future.successful(Ok(json.data.get).as(JSON))
      case None =>
        db.run(Company.filter(_.id === id).result.head)
          .flatMap(x => crawler.forInvestmentGraph(x.keyno.get, id).map(Ok(_)))
    }
  }

  def getAssociationGraph(id: Int): Action[AnyContent] = Action.async { _ =>
    db.run(CompanyGraph.filter(x => x.cid === id && x.`type` === 3).result.headOption).flatMap {
      case Some(json) =>
        Future.successful(Ok(json.data.get).as(JSON))
      case None =>
        db.run(Company.filter(_.id === id).result.head)
          .flatMap(x => crawler.forAssociationGraph(x.keyno.get, id).map(Ok(_)))
    }
  }

  def getSecondEquityStructureGraph(id: Int): Action[AnyContent] = Action.async { _ =>
    db.run(CompanyGraph.filter(x => x.cid === id && x.`type` === 4).result.headOption).flatMap {
      case Some(json) =>
        Future.successful(Ok(json.data.get).as(JSON))
      case None =>
        db.run(Company.filter(_.id === id).result.head)
          .flatMap(x => crawler.forSecondEquityStructureGraph(x.keyno.get, id).map(Ok(_)))
    }
  }

  def getCompanyShortInfo(key: String): Action[AnyContent] = Action.async { _ =>
    db.run(ShortInfo.filter(x => x.key === key).result.headOption).flatMap {
      case Some(json) =>
        Future.successful(Ok(json.info.get).as(JSON))
      case None =>
        crawler.forCompanyShortInfo(key).map(Ok(_))
    }
  }

  def getNews: Action[AnyContent] = Action.async { _ =>
    db.run(News.result).flatMap {
      case news if news.nonEmpty => Future.successful(Ok(Json.toJson(news)))
      case _ => crawler.forNews.map(Ok(_))
    }
  }

  def getNewsBody(url: String): Action[AnyContent] = Action.async { _ =>
    db.run(NewsBody.filter(_.url === url).result.headOption).flatMap {
      case Some(body) => Future.successful(Ok(Json.toJson(body)))
      case None => crawler.forNewsBody(url).map(Ok(_))
    }
  }

  def getMultipleAssociationGraph(nodes: String): Action[AnyContent] = Action.async { _ =>
    crawler.forMultipleAssociationGraph(nodes).map(Ok(_))
  }

  def getHintCompany(name: String): Action[AnyContent] = Action.async { _ =>
    db.run(Company.filter(x => x.keyno.isDefined && x.name.like(s"%$name%"))
      .sortBy(_.money.desc).take(7).result)
      .map(x => Ok(Json.toJson(x)))
  }

  def getHintBoss(id: Int): Action[AnyContent] = Action.async { _ =>
    val a = MainPersonnel.filter(_.cid === id).map(_.name)
    val b = ShareholderInformation.filter(_.cid === id).map(_.shareholderName)
    val c = a.union(b)
    db.run(c.result).map(x => Ok(Json.toJson(x.zipWithIndex.map(z => Json.obj("id" -> (z._2 + 1).toString, "itemName" -> z._1)))))
  }

  def getSearchHint(key: String, kind: Int): Action[AnyContent] = this.query(key, kind, 0, 5)

  def getInterestedPeople: Action[AnyContent] = Action.async { _ =>
    db.run(InterestedPeople.result).map(data => getRandomList(data.length).map(data.apply)).map(x => Ok(Json.toJson(x)))
  }

  def searchBrand(key: String): Action[AnyContent] = Action.async { _ =>
    db.run(SearchBrandHistory.filter(_.key === key).result.headOption).foreach {
      case Some(d) => db.run(SearchBrandHistory.filter(_.key === key).map(_.count).update(d.count + 1))
      case None => crawler.forBrands(key)
    }
    ws.url(baseUrl + "brand/doc/_search").withBody(Json.obj(
      "query" -> Json.obj("multi_match" -> Json.obj("query" -> key,
        "fields" -> Json.arr("name", "num", "applicant"))), "size" -> 100)).get()
      .map(x => Ok(x.json.\("hits").\("hits").as[JsArray]))
  }

  def getBrandBody(id: Int): Action[AnyContent] = Action.async { _ =>
    db.run(BrandBody.filter(_.bid === id).result.headOption).flatMap {
      case Some(data) =>
        Future.successful(Ok(Json.toJson(data)))
      case None =>
        db.run(Brand.filter(_.id === id).result.head)
          .flatMap(x => crawler.forBrandBody(x.id, x.ref.get).map(x => Ok(Json.toJson(x))))
    }
  }


  def getRandomList(n: Int): List[Int] =
    (1 to n * 10)
      .map(_ => Random.nextInt(n))
      .++(0 until n)
      .groupBy(x => x)
      .mapValues(_.size).toList
      .sortBy(_._2).map(_._1)
}

