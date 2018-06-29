package models

import javax.inject._
import anorm.SqlParser._
import anorm._
import play.api.db.DBApi

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
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

  private lazy val associationInfoSample = get[Int]("id") ~
    get[String]("name") ~
    get[Int]("kind") map {
    case id ~ name ~ kind => (id, name, kind)
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

  lazy val associationParser: RowParser[Association] = Macro.namedParser[Association]

  lazy val bossParser: RowParser[Boss] = Macro.namedParser[Boss]

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

  def getBossById(id: Int) = Future {
    db.withConnection { implicit conn =>
      SQL(
        """
          |select *
          |from boss, `character`
          |where boss.bid = `character`.id and boss.id = {id}
        """.stripMargin)
        .on('id -> id)
        .as(bossParser.single)
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
        val real = SQL(
          """
            |select investment_info.id
            |from company, investment_info
            |where company.id = investment_info.real_id and company.id = {id}
          """.stripMargin)
          .on('id -> id)
          .as(scalar[Int].single)

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
        ).on('id -> real).as(investmentGraphParser.*)
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
        SQL("""select * from investment_boss where id = {id}""")
          .on('id -> id)
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

  def getAssociationGraphById(id: Int) = Future {
    val queue = mutable.Queue[Int]()
    val set = mutable.Set[(Int, String, Int)]()
    val relationBuffer = ListBuffer[Association]()
    queue += id

    val first_node = db.withConnection { implicit conn =>
      val real = SQL(
        """
          |select association_info.id
          |from company, association_info
          |where company.id = association_info.real_id and company.id = {id}
        """.stripMargin)
        .on('id -> id)
        .as(scalar[Int].single)

      SQL(
        """
          |select * from association_info where id = {id}
        """.stripMargin)
        .on('id -> real)
        .as(associationInfoSample.single)
    }

    set += first_node

    while (queue.nonEmpty) {
      val head = queue.dequeue()
      val associations = db.withConnection { implicit conn =>
        SQL(
          """
            |select
            |  association_graph.id  as id,
            |  association_graph.bid as bid,
            |  value,
            |  a.name                as name_a,
            |  b.name                as name_b,
            |  a.kind                as kind_a,
            |  b.kind                as kind_b
            |from association_graph, association_info as a, association_info as b
            |where association_graph.id = a.id and association_graph.bid = b.id and (association_graph.id = {id} or association_graph.bid = {id})
          """.stripMargin)
          .on('id -> head)
          .as(associationParser.*)
      }

      val (first, second) = associations.partition(_.id == head)

      relationBuffer ++= first
      relationBuffer ++= second

      first.map(x => (x.bid, x.name_b, x.kind_b)).filterNot(set).foreach { x =>
        queue += x._1
        set += x
      }
      second.map(x => (x.id, x.name_a, x.kind_a)).filterNot(set).foreach { x =>
        queue += x._1
        set += x
      }
    }

    val listSet = set.toList

    val index = listSet.map(_._1).zipWithIndex.toMap

    val links = relationBuffer.distinct.toList.map(x => Links(index(x.id), index(x.bid), x.value))

    val data = listSet.map{x =>
      if(x._2 != first_node._2)
        Nodes(x._2, x._3, draggable = true)
      else
        Nodes(x._2, 2, draggable = true)
    }

    (data, links)
  }

  def getEquityStructureGraphById(id: Int): Future[ColorTree] = {
    val outboundInvestmentFuture = getOutboundInvestmentById(id)

    val shareholderInformationFuture = getShareholderInformationById(id)

    val companyFuture = getCompanyById(id)
    for {
      outboundInvestment <- outboundInvestmentFuture
      shareholderInformation <- shareholderInformationFuture
      company <- companyFuture
    } yield {
      val z = 16
      ColorTree(company.get.name, Some(List(ColorTree("股东",
        Option(shareholderInformation.map(x => ColorTree(x.name, None, Option(x.shareholding_ratio), List(x.name.length * z, 30)))) , None, List("股东".length * z, 30)),
        ColorTree("对外投资",
          Option(outboundInvestment.map(x => ColorTree(x.invested_enterprise, None, x.contribution, List(x.invested_enterprise.length * z, 30)))) , None, List("对外投资".length * z, 30)))), None ,List(company.get.name.length * z, 30))
    }
  }

  def getSuspectedControllerById(id: Int): Future[ShareholderInformation] = {
    getShareholderInformationById(id).map(_.maxBy(_.shareholding_ratio))
  }

}

