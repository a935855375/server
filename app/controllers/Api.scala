package controllers

import com.google.inject.Inject
import crawler.Crawler
import models.Children
import play.api.db.NamedDatabase
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}
import slick.jdbc.JdbcProfile
import models.Tables._
import models.Tables.profile.api._
import play.api.Configuration
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import util.Formats._

import scala.concurrent.{ExecutionContext, Future}

class Api @Inject()(cc: MessagesControllerComponents,
                    crawler: Crawler,
                    config: Configuration,
                    ws: WSClient,
                    @NamedDatabase("server") protected val dbConfigProvider: DatabaseConfigProvider)
                   (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  final val cookie = config.get[String]("crawler.cookie")
  final val agent = config.get[String]("crawler.agent")

  def getAllCompany: Action[AnyContent] = Action.async { implicit request =>
    crawler.forSearchPage("苏州朗动网络科技有限公司", 2)
    Future.successful(Ok)
  }

  def getCompanyBaseInfo(id: Int): Action[AnyContent] = Action.async { implicit request =>
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

  def getBossInfo(id: Int): Action[AnyContent] = Action.async { implicit request =>
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

  def getBossGraph(id: Int): Action[AnyContent] = Action.async { implicit request =>
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

  def getEquityStructureGraph(id: Int): Action[AnyContent] = Action.async { implicit request =>
    db.run(CompanyGraph.filter(x => x.cid === id && x.`type` === 0).result.headOption).flatMap {
      case Some(json) =>
        Future.successful(Ok(json.data.get).as(JSON))
      case None =>
        db.run(Company.filter(_.id === id).result.head)
          .flatMap(x => crawler.forEquityStructureGraph(x.keyno.get, id).map(Ok(_)))
    }
  }

  def getEnterpriseGraph(id: Int): Action[AnyContent] = Action.async { implicit request =>
    db.run(CompanyGraph.filter(x => x.cid === id && x.`type` === 1).result.headOption).flatMap {
      case Some(json) =>
        Future.successful(Ok(json.data.get).as(JSON))
      case None =>
        db.run(Company.filter(_.id === id).result.head)
          .flatMap(x => crawler.forEnterpriseGraph(x.keyno.get, id).map(Ok(_)))
    }
  }

  def getInvestmentGraph(id: Int): Action[AnyContent] = Action.async { implicit request =>
    db.run(CompanyGraph.filter(x => x.cid === id && x.`type` === 2).result.headOption).flatMap {
      case Some(json) =>
        Future.successful(Ok(json.data.get).as(JSON))
      case None =>
        db.run(Company.filter(_.id === id).result.head)
          .flatMap(x => crawler.forInvestmentGraph(x.keyno.get, id).map(Ok(_)))
    }
  }

  def getAssociationGraph(id: Int): Action[AnyContent] = Action.async { implicit request =>
    db.run(CompanyGraph.filter(x => x.cid === id && x.`type` === 3).result.headOption).flatMap {
      case Some(json) =>
        Future.successful(Ok(json.data.get).as(JSON))
      case None =>
        db.run(Company.filter(_.id === id).result.head)
          .flatMap(x => crawler.forAssociationGraph(x.keyno.get, id).map(Ok(_)))
    }
  }

  def getCompanyShortInfo(key: String): Action[AnyContent] = Action.async { implicit request =>
    db.run(ShortInfo.filter(x => x.key === key).result.headOption).flatMap {
      case Some(json) =>
        Future.successful(Ok(json.info.get).as(JSON))
      case None =>
        crawler.forCompanyShortInfo(key).map(Ok(_))
    }
  }

  def getMultipleAssociationGraph(nodes: String): Action[AnyContent] = Action.async { implicit request =>
    crawler.forMultipleAssociationGraph(nodes).map(Ok(_))
  }

  def test: Action[AnyContent] = Action.async { implicit request =>
    ws.url("http://localhost:9200/data/company/_search").withBody(Json.obj(
      "query" -> Json.obj("match" -> Json.obj("name" -> "小米")),
      "size" -> 100)).get().map(x => Ok(Json.prettyPrint(x.json)))
  }
}

