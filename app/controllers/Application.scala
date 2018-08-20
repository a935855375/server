package controllers

import com.google.inject.Inject
import javax.inject.Singleton
import models.Format._
import models._
import play.api.Configuration
import play.api.libs.json.{JsArray, Json, OFormat}
import play.api.libs.ws.WSClient
import play.api.mvc._
import service.AuthService

import scala.concurrent.{ExecutionContext, Future}

// import scala.collection.JavaConverters._

@Singleton
class Application @Inject()(cc: MessagesControllerComponents,
                            database: MyDatabase,
                            auth: AuthService,
                            config: Configuration,
                            ws: WSClient)
                           (implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

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
}
