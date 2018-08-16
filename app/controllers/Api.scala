package controllers

import com.google.inject.Inject
import crawler.Crawler
import play.api.db.NamedDatabase
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class Api @Inject()(cc: MessagesControllerComponents,
                    crawler: Crawler,
                    @NamedDatabase("server") protected val dbConfigProvider: DatabaseConfigProvider)
                   (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  def getAllCompany: Action[AnyContent] = Action.async { implicit request =>
    crawler.forBossInfo("/pl_p1910534b4ae98fea35ddbeb1d61cd44.html", 11)
    Future.successful(Ok)
  }
}
