package models

import javax.inject._

import anorm.SqlParser._
import anorm._
import play.api.db.DBApi

import scala.concurrent.Future
import scala.language.postfixOps


@Singleton
class Database @Inject()(dbapi: DBApi)(implicit ec: DatabaseExecutionContext) {
  private val db = dbapi.database("default")

  private lazy val UserSample = get[Int]("id") ~
    get[String]("username") ~
    get[String]("password") ~
    get[Option[String]]("nickname") map {
    case id ~ username ~ password ~ nickname => User(id, username, password, nickname)
  }

  val companyParser: RowParser[Company] = Macro.namedParser[Company]

  def getUserByUsername(username: String, password: String) = Future {
    db.withConnection { implicit conn =>
      SQL("""select * from user where username = {username} and password = {password}""")
        .on('username -> username, 'password -> password)
        .as(UserSample.singleOpt)
    }
  }

  def getUserByUsernameOrMail(username: String, mail: String) = Future {
    db.withConnection { implicit conn =>
      SQL("""select * from user where username = {username} or mail = {mail}""")
        .on('username -> username, 'mail -> mail)
        .as(UserSample.singleOpt)
    }
  }

  def addUser(username: String, mail: String, password: String) = Future {
    db.withConnection { implicit conn =>
      SQL("""insert into user(username, mail, password) values({username}, {mail}, {password})""")
        .on('username -> username, 'mail -> mail, 'password -> password)
        .executeUpdate()
    }
  }

  def getCompanyById(id: Int) = Future {
    db.withConnection { implicit conn =>
      SQL("""select * from company where id = {id}""")
        .on('id -> id)
        .as(companyParser.singleOpt)
    }
  }

}

