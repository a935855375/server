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
    crawler.forBaseInfo("/firm_9cce0780ab7644008b73bc2120479d31.html")
    Future.successful(Ok)
  }
}
