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

  lazy val companyParser: RowParser[Company] = Macro.namedParser[Company]

  lazy val basicInfoParser: RowParser[BasicInfo] = Macro.namedParser[BasicInfo]

  lazy val shareholderInformationParser: RowParser[ShareholderInformation] = Macro.namedParser[ShareholderInformation]

  lazy val outboundInvestmentParser: RowParser[OutboundInvestment] = Macro.namedParser[OutboundInvestment]

  lazy val branchParser: RowParser[Branch] = Macro.namedParser[Branch]

  lazy val changeRecordParser: RowParser[ChangeRecord] = Macro.namedParser[ChangeRecord]

  lazy val mainPersonnelParser: RowParser[MainPersonnel] = Macro.namedParser[MainPersonnel]

  lazy val companyProfileParser: RowParser[CompanyProfile] = Macro.namedParser[CompanyProfile]

  lazy val interestedPeopleParser: RowParser[InterestedPeople] = Macro.namedParser[InterestedPeople]

  lazy val enterpriseGraphParser: RowParser[EnterpriseGraph] = Macro.namedParser[EnterpriseGraph]

  lazy val investmentGraphParser: RowParser[InvestmentGraph] = Macro.namedParser[InvestmentGraph]

  lazy val investmentBossParser: RowParser[InvestmentBoss] = Macro.namedParser[InvestmentBoss]

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

  def getCompanyInfoById(id: Int) = Future {
    db.withConnection { implicit conn =>
      SQL("""select * from basic_info where cid = {id}""")
        .on('id -> id)
        .as(basicInfoParser.singleOpt)
    }
  }

  def getShareholderInformationById(id: Int) = Future {
    db.withConnection { implicit conn =>
      SQL("""select * from shareholder_information, `character` where shareholder_information.id = {id} and shareholder = `character`.id""")
        .on('id -> id)
        .as(shareholderInformationParser.*)
    }
  }

  def getOutboundInvestmentById(id: Int) = Future {
    db.withConnection { implicit conn =>
      SQL("""select * from outbound_investment where id = {id}""")
        .on('id -> id)
        .as(outboundInvestmentParser.*)
    }
  }

  def getBranchById(id: Int) = Future {
    db.withConnection { implicit conn =>
      SQL("""select * from branch where id = {id}""")
        .on('id -> id)
        .as(branchParser.*)
    }
  }

  def getChangeRecordById(id: Int) = Future {
    db.withConnection { implicit conn =>
      SQL("""select * from change_record where id = {id}""")
        .on('id -> id)
        .as(changeRecordParser.*)
    }
  }

  def getMainPersonnelById(id: Int) = Future {
    db.withConnection { implicit conn =>
      SQL("""select * from main_personnel, `character` where cid = `character`.id and main_personnel.id = {id}""")
        .on('id -> id)
        .as(mainPersonnelParser.*)
    }
  }

  def getCompanyProfileById(id: Int) = Future {
    db.withConnection { implicit conn =>
      SQL("""select * from company_profile where id = {id}""")
        .on('id -> id)
        .as(companyProfileParser.singleOpt)
    }
  }

  def getInterestedPeople = Future {
    db.withConnection { implicit conn =>
      SQL("""select * from interested_people""")
        .as(interestedPeopleParser.*)
    }
  }

  def getEnterpriseGraphById(id: Int) = Future {
    db.withConnection { implicit conn =>
      val enterpriseGraphs = SQL(
        """
          |select *
          |from enterprise_one, enterprise_two, enterprise_three
          |where enterprise_one.id = enterprise_two.one and enterprise_two.id = enterprise_three.two and enterprise_one.id = {id}
        """.stripMargin
      ).on('id -> id).as(enterpriseGraphParser.*)
      val trees = enterpriseGraphs.groupBy(_.two_name)
        .map(x => Tree(x._1, Some(x._2.map(y => Tree(y.three_name, None, Some(0)))), None)).toList
      Tree(enterpriseGraphs.head.one_name, Some(trees), None)
    }
  }

  def getInvestmentGraphById(id: Int): Future[Tree] = {
    val dataFuture = Future {
      db.withConnection { implicit conn =>
        val investmentGraph = SQL(
          """
            |select
            |  q.name as q_name,
            |  a.value as value_a,
            |  w.name as w_name,
            |  b.value as value_b,
            |  e.name as e_name
            |from (investment_graph as a left join investment_graph as b on a.bid = b.id
            |  , investment_info q, investment_info w) left join investment_info e on b.bid = e.id
            |where a.id = {id} and a.id = q.id and a.bid = w.id
          """.stripMargin
        ).on('id -> id).as(investmentGraphParser.*)
        val trees = investmentGraph.groupBy(_.w_name)
          .map { x =>
            if (x._2.size > 1)
              Tree(x._1.get, Some(x._2.map(y => Tree(y.e_name.get, None, y.value_b))), x._2.head.value_a)
            else
              Tree(x._1.get, None, x._2.head.value_a)
          }.toList
        Tree(investmentGraph.head.q_name.get, Some(trees), None)
      }
    }

    val bossFuture = Future {
      db.withConnection { implicit conn =>
        SQL("""select * from investment_boss""")
          .as(investmentBossParser.*)
      }
    }

    for {
      data <- dataFuture
      boss <- bossFuture
    } yield {
      val tree = boss.map(x => Tree(x.name, None, x.value))
      Tree(data.name, Some(List(Tree("对外投资", data.children, None), Tree("股东", Some(tree), None))), None)
    }
  }
}

