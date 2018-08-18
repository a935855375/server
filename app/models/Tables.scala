package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(BasicInfo.schema, BossHistoryInvestment.schema, BossHistoryPosition.schema, BossHistoryRepresent.schema, BossHoldingCompany.schema, BossInvestment.schema, BossPosition.schema, BossRepresent.schema, Branch.schema, ChangeRecord.schema, Company.schema, CompanyGraph.schema, MainPersonnel.schema, OutboundInvestment.schema, Person.schema, ShareholderInformation.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table BasicInfo
   *  @param cid Database column cid SqlType(INT)
   *  @param openStatus Database column open_status SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param paidCapital Database column paid_capital SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param socialCreditCode Database column social_credit_code SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param taxpayerIdentificationNumber Database column taxpayer_identification_number SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param registrationNumber Database column registration_number SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param organizationCode Database column organization_code SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param typeOfCompany Database column type_of_company SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param industry Database column industry SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param dateOfApproval Database column date_of_approval SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param registrationAuthority Database column registration_authority SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param region Database column region SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param englishName Database column english_name SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param nameUsedBefore Database column name_used_before SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param numberOfParticipants Database column number_of_participants SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param personnelScale Database column personnel_scale SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param timeLimitForBusiness Database column time_limit_for_business SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param enterpriseAddress Database column enterprise_address SqlType(VARCHAR), Length(255,true), Default(Some(-))
   *  @param scopeOfOperation Database column scope_of_operation SqlType(TEXT), Default(None) */
  case class BasicInfoRow(cid: Int, openStatus: Option[String] = Some("-"), paidCapital: Option[String] = Some("-"), socialCreditCode: Option[String] = Some("-"), taxpayerIdentificationNumber: Option[String] = Some("-"), registrationNumber: Option[String] = Some("-"), organizationCode: Option[String] = Some("-"), typeOfCompany: Option[String] = Some("-"), industry: Option[String] = Some("-"), dateOfApproval: Option[String] = Some("-"), registrationAuthority: Option[String] = Some("-"), region: Option[String] = Some("-"), englishName: Option[String] = Some("-"), nameUsedBefore: Option[String] = Some("-"), numberOfParticipants: Option[String] = Some("-"), personnelScale: Option[String] = Some("-"), timeLimitForBusiness: Option[String] = Some("-"), enterpriseAddress: Option[String] = Some("-"), scopeOfOperation: Option[String] = None)
  /** GetResult implicit for fetching BasicInfoRow objects using plain SQL queries */
  implicit def GetResultBasicInfoRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[BasicInfoRow] = GR{
    prs => import prs._
    BasicInfoRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table basic_info. Objects of this class serve as prototypes for rows in queries. */
  class BasicInfo(_tableTag: Tag) extends profile.api.Table[BasicInfoRow](_tableTag, Some("data"), "basic_info") {
    def * = (cid, openStatus, paidCapital, socialCreditCode, taxpayerIdentificationNumber, registrationNumber, organizationCode, typeOfCompany, industry, dateOfApproval, registrationAuthority, region, englishName, nameUsedBefore, numberOfParticipants, personnelScale, timeLimitForBusiness, enterpriseAddress, scopeOfOperation) <> (BasicInfoRow.tupled, BasicInfoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), openStatus, paidCapital, socialCreditCode, taxpayerIdentificationNumber, registrationNumber, organizationCode, typeOfCompany, industry, dateOfApproval, registrationAuthority, region, englishName, nameUsedBefore, numberOfParticipants, personnelScale, timeLimitForBusiness, enterpriseAddress, scopeOfOperation).shaped.<>({r=>import r._; _1.map(_=> BasicInfoRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17, _18, _19)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column open_status SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val openStatus: Rep[Option[String]] = column[Option[String]]("open_status", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column paid_capital SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val paidCapital: Rep[Option[String]] = column[Option[String]]("paid_capital", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column social_credit_code SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val socialCreditCode: Rep[Option[String]] = column[Option[String]]("social_credit_code", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column taxpayer_identification_number SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val taxpayerIdentificationNumber: Rep[Option[String]] = column[Option[String]]("taxpayer_identification_number", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column registration_number SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val registrationNumber: Rep[Option[String]] = column[Option[String]]("registration_number", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column organization_code SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val organizationCode: Rep[Option[String]] = column[Option[String]]("organization_code", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column type_of_company SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val typeOfCompany: Rep[Option[String]] = column[Option[String]]("type_of_company", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column industry SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val industry: Rep[Option[String]] = column[Option[String]]("industry", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column date_of_approval SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val dateOfApproval: Rep[Option[String]] = column[Option[String]]("date_of_approval", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column registration_authority SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val registrationAuthority: Rep[Option[String]] = column[Option[String]]("registration_authority", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column region SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val region: Rep[Option[String]] = column[Option[String]]("region", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column english_name SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val englishName: Rep[Option[String]] = column[Option[String]]("english_name", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column name_used_before SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val nameUsedBefore: Rep[Option[String]] = column[Option[String]]("name_used_before", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column number_of_participants SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val numberOfParticipants: Rep[Option[String]] = column[Option[String]]("number_of_participants", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column personnel_scale SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val personnelScale: Rep[Option[String]] = column[Option[String]]("personnel_scale", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column time_limit_for_business SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val timeLimitForBusiness: Rep[Option[String]] = column[Option[String]]("time_limit_for_business", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column enterprise_address SqlType(VARCHAR), Length(255,true), Default(Some(-)) */
    val enterpriseAddress: Rep[Option[String]] = column[Option[String]]("enterprise_address", O.Length(255,varying=true), O.Default(Some("-")))
    /** Database column scope_of_operation SqlType(TEXT), Default(None) */
    val scopeOfOperation: Rep[Option[String]] = column[Option[String]]("scope_of_operation", O.Default(None))

    /** Uniqueness Index over (cid) (database name basic_info_cid_uindex) */
    val index1 = index("basic_info_cid_uindex", cid, unique=true)
  }
  /** Collection-like TableQuery object for table BasicInfo */
  lazy val BasicInfo = new TableQuery(tag => new BasicInfo(tag))

  /** Entity class storing rows of table BossHistoryInvestment
   *  @param bid Database column bid SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ref Database column ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param capital Database column capital SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param represent Database column represent SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param representRef Database column represent_ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param status Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
  case class BossHistoryInvestmentRow(bid: Int, name: Option[String] = None, ref: Option[String] = None, capital: Option[String] = None, represent: Option[String] = None, representRef: Option[String] = None, status: Option[String] = None)
  /** GetResult implicit for fetching BossHistoryInvestmentRow objects using plain SQL queries */
  implicit def GetResultBossHistoryInvestmentRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[BossHistoryInvestmentRow] = GR{
    prs => import prs._
    BossHistoryInvestmentRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table boss_history_investment. Objects of this class serve as prototypes for rows in queries. */
  class BossHistoryInvestment(_tableTag: Tag) extends profile.api.Table[BossHistoryInvestmentRow](_tableTag, Some("data"), "boss_history_investment") {
    def * = (bid, name, ref, capital, represent, representRef, status) <> (BossHistoryInvestmentRow.tupled, BossHistoryInvestmentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(bid), name, ref, capital, represent, representRef, status).shaped.<>({r=>import r._; _1.map(_=> BossHistoryInvestmentRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column bid SqlType(INT) */
    val bid: Rep[Int] = column[Int]("bid")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val ref: Rep[Option[String]] = column[Option[String]]("ref", O.Length(255,varying=true), O.Default(None))
    /** Database column capital SqlType(VARCHAR), Length(255,true), Default(None) */
    val capital: Rep[Option[String]] = column[Option[String]]("capital", O.Length(255,varying=true), O.Default(None))
    /** Database column represent SqlType(VARCHAR), Length(255,true), Default(None) */
    val represent: Rep[Option[String]] = column[Option[String]]("represent", O.Length(255,varying=true), O.Default(None))
    /** Database column represent_ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val representRef: Rep[Option[String]] = column[Option[String]]("represent_ref", O.Length(255,varying=true), O.Default(None))
    /** Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
    val status: Rep[Option[String]] = column[Option[String]]("status", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table BossHistoryInvestment */
  lazy val BossHistoryInvestment = new TableQuery(tag => new BossHistoryInvestment(tag))

  /** Entity class storing rows of table BossHistoryPosition
   *  @param bid Database column bid SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ref Database column ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param position Database column position SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param capital Database column capital SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param represent Database column represent SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param representRef Database column represent_ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param status Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
  case class BossHistoryPositionRow(bid: Int, name: Option[String] = None, ref: Option[String] = None, position: Option[String] = None, capital: Option[String] = None, represent: Option[String] = None, representRef: Option[String] = None, status: Option[String] = None)
  /** GetResult implicit for fetching BossHistoryPositionRow objects using plain SQL queries */
  implicit def GetResultBossHistoryPositionRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[BossHistoryPositionRow] = GR{
    prs => import prs._
    BossHistoryPositionRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table boss_history_position. Objects of this class serve as prototypes for rows in queries. */
  class BossHistoryPosition(_tableTag: Tag) extends profile.api.Table[BossHistoryPositionRow](_tableTag, Some("data"), "boss_history_position") {
    def * = (bid, name, ref, position, capital, represent, representRef, status) <> (BossHistoryPositionRow.tupled, BossHistoryPositionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(bid), name, ref, position, capital, represent, representRef, status).shaped.<>({r=>import r._; _1.map(_=> BossHistoryPositionRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column bid SqlType(INT) */
    val bid: Rep[Int] = column[Int]("bid")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val ref: Rep[Option[String]] = column[Option[String]]("ref", O.Length(255,varying=true), O.Default(None))
    /** Database column position SqlType(VARCHAR), Length(255,true), Default(None) */
    val position: Rep[Option[String]] = column[Option[String]]("position", O.Length(255,varying=true), O.Default(None))
    /** Database column capital SqlType(VARCHAR), Length(255,true), Default(None) */
    val capital: Rep[Option[String]] = column[Option[String]]("capital", O.Length(255,varying=true), O.Default(None))
    /** Database column represent SqlType(VARCHAR), Length(255,true), Default(None) */
    val represent: Rep[Option[String]] = column[Option[String]]("represent", O.Length(255,varying=true), O.Default(None))
    /** Database column represent_ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val representRef: Rep[Option[String]] = column[Option[String]]("represent_ref", O.Length(255,varying=true), O.Default(None))
    /** Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
    val status: Rep[Option[String]] = column[Option[String]]("status", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table BossHistoryPosition */
  lazy val BossHistoryPosition = new TableQuery(tag => new BossHistoryPosition(tag))

  /** Entity class storing rows of table BossHistoryRepresent
   *  @param bid Database column bid SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ref Database column ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param capital Database column capital SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param region Database column region SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param kind Database column kind SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param status Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
  case class BossHistoryRepresentRow(bid: Int, name: Option[String] = None, ref: Option[String] = None, capital: Option[String] = None, region: Option[String] = None, kind: Option[String] = None, status: Option[String] = None)
  /** GetResult implicit for fetching BossHistoryRepresentRow objects using plain SQL queries */
  implicit def GetResultBossHistoryRepresentRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[BossHistoryRepresentRow] = GR{
    prs => import prs._
    BossHistoryRepresentRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table boss_history_represent. Objects of this class serve as prototypes for rows in queries. */
  class BossHistoryRepresent(_tableTag: Tag) extends profile.api.Table[BossHistoryRepresentRow](_tableTag, Some("data"), "boss_history_represent") {
    def * = (bid, name, ref, capital, region, kind, status) <> (BossHistoryRepresentRow.tupled, BossHistoryRepresentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(bid), name, ref, capital, region, kind, status).shaped.<>({r=>import r._; _1.map(_=> BossHistoryRepresentRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column bid SqlType(INT) */
    val bid: Rep[Int] = column[Int]("bid")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val ref: Rep[Option[String]] = column[Option[String]]("ref", O.Length(255,varying=true), O.Default(None))
    /** Database column capital SqlType(VARCHAR), Length(255,true), Default(None) */
    val capital: Rep[Option[String]] = column[Option[String]]("capital", O.Length(255,varying=true), O.Default(None))
    /** Database column region SqlType(VARCHAR), Length(255,true), Default(None) */
    val region: Rep[Option[String]] = column[Option[String]]("region", O.Length(255,varying=true), O.Default(None))
    /** Database column kind SqlType(VARCHAR), Length(255,true), Default(None) */
    val kind: Rep[Option[String]] = column[Option[String]]("kind", O.Length(255,varying=true), O.Default(None))
    /** Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
    val status: Rep[Option[String]] = column[Option[String]]("status", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table BossHistoryRepresent */
  lazy val BossHistoryRepresent = new TableQuery(tag => new BossHistoryRepresent(tag))

  /** Entity class storing rows of table BossHoldingCompany
   *  @param bid Database column bid SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ref Database column ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ratio Database column ratio SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param chain Database column chain SqlType(TEXT), Default(None) */
  case class BossHoldingCompanyRow(bid: Int, name: Option[String] = None, ref: Option[String] = None, ratio: Option[String] = None, chain: Option[String] = None)
  /** GetResult implicit for fetching BossHoldingCompanyRow objects using plain SQL queries */
  implicit def GetResultBossHoldingCompanyRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[BossHoldingCompanyRow] = GR{
    prs => import prs._
    BossHoldingCompanyRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table boss_holding_company. Objects of this class serve as prototypes for rows in queries. */
  class BossHoldingCompany(_tableTag: Tag) extends profile.api.Table[BossHoldingCompanyRow](_tableTag, Some("data"), "boss_holding_company") {
    def * = (bid, name, ref, ratio, chain) <> (BossHoldingCompanyRow.tupled, BossHoldingCompanyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(bid), name, ref, ratio, chain).shaped.<>({r=>import r._; _1.map(_=> BossHoldingCompanyRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column bid SqlType(INT) */
    val bid: Rep[Int] = column[Int]("bid")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val ref: Rep[Option[String]] = column[Option[String]]("ref", O.Length(255,varying=true), O.Default(None))
    /** Database column ratio SqlType(VARCHAR), Length(255,true), Default(None) */
    val ratio: Rep[Option[String]] = column[Option[String]]("ratio", O.Length(255,varying=true), O.Default(None))
    /** Database column chain SqlType(TEXT), Default(None) */
    val chain: Rep[Option[String]] = column[Option[String]]("chain", O.Default(None))
  }
  /** Collection-like TableQuery object for table BossHoldingCompany */
  lazy val BossHoldingCompany = new TableQuery(tag => new BossHoldingCompany(tag))

  /** Entity class storing rows of table BossInvestment
   *  @param bid Database column bid SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ref Database column ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ratio Database column ratio SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param capital Database column capital SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param region Database column region SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param kind Database column kind SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param represent Database column represent SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param representRef Database column represent_ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param status Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
  case class BossInvestmentRow(bid: Int, name: Option[String] = None, ref: Option[String] = None, ratio: Option[String] = None, capital: Option[String] = None, region: Option[String] = None, kind: Option[String] = None, represent: Option[String] = None, representRef: Option[String] = None, status: Option[String] = None)
  /** GetResult implicit for fetching BossInvestmentRow objects using plain SQL queries */
  implicit def GetResultBossInvestmentRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[BossInvestmentRow] = GR{
    prs => import prs._
    BossInvestmentRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table boss_investment. Objects of this class serve as prototypes for rows in queries. */
  class BossInvestment(_tableTag: Tag) extends profile.api.Table[BossInvestmentRow](_tableTag, Some("data"), "boss_investment") {
    def * = (bid, name, ref, ratio, capital, region, kind, represent, representRef, status) <> (BossInvestmentRow.tupled, BossInvestmentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(bid), name, ref, ratio, capital, region, kind, represent, representRef, status).shaped.<>({r=>import r._; _1.map(_=> BossInvestmentRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9, _10)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column bid SqlType(INT) */
    val bid: Rep[Int] = column[Int]("bid")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val ref: Rep[Option[String]] = column[Option[String]]("ref", O.Length(255,varying=true), O.Default(None))
    /** Database column ratio SqlType(VARCHAR), Length(255,true), Default(None) */
    val ratio: Rep[Option[String]] = column[Option[String]]("ratio", O.Length(255,varying=true), O.Default(None))
    /** Database column capital SqlType(VARCHAR), Length(255,true), Default(None) */
    val capital: Rep[Option[String]] = column[Option[String]]("capital", O.Length(255,varying=true), O.Default(None))
    /** Database column region SqlType(VARCHAR), Length(255,true), Default(None) */
    val region: Rep[Option[String]] = column[Option[String]]("region", O.Length(255,varying=true), O.Default(None))
    /** Database column kind SqlType(VARCHAR), Length(255,true), Default(None) */
    val kind: Rep[Option[String]] = column[Option[String]]("kind", O.Length(255,varying=true), O.Default(None))
    /** Database column represent SqlType(VARCHAR), Length(255,true), Default(None) */
    val represent: Rep[Option[String]] = column[Option[String]]("represent", O.Length(255,varying=true), O.Default(None))
    /** Database column represent_ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val representRef: Rep[Option[String]] = column[Option[String]]("represent_ref", O.Length(255,varying=true), O.Default(None))
    /** Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
    val status: Rep[Option[String]] = column[Option[String]]("status", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table BossInvestment */
  lazy val BossInvestment = new TableQuery(tag => new BossInvestment(tag))

  /** Entity class storing rows of table BossPosition
   *  @param bid Database column bid SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ref Database column ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param position Database column position SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param capital Database column capital SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param region Database column region SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param kind Database column kind SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param represent Database column represent SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param representRef Database column represent_ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param status Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
  case class BossPositionRow(bid: Int, name: Option[String] = None, ref: Option[String] = None, position: Option[String] = None, capital: Option[String] = None, region: Option[String] = None, kind: Option[String] = None, represent: Option[String] = None, representRef: Option[String] = None, status: Option[String] = None)
  /** GetResult implicit for fetching BossPositionRow objects using plain SQL queries */
  implicit def GetResultBossPositionRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[BossPositionRow] = GR{
    prs => import prs._
    BossPositionRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table boss_position. Objects of this class serve as prototypes for rows in queries. */
  class BossPosition(_tableTag: Tag) extends profile.api.Table[BossPositionRow](_tableTag, Some("data"), "boss_position") {
    def * = (bid, name, ref, position, capital, region, kind, represent, representRef, status) <> (BossPositionRow.tupled, BossPositionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(bid), name, ref, position, capital, region, kind, represent, representRef, status).shaped.<>({r=>import r._; _1.map(_=> BossPositionRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9, _10)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column bid SqlType(INT) */
    val bid: Rep[Int] = column[Int]("bid")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val ref: Rep[Option[String]] = column[Option[String]]("ref", O.Length(255,varying=true), O.Default(None))
    /** Database column position SqlType(VARCHAR), Length(255,true), Default(None) */
    val position: Rep[Option[String]] = column[Option[String]]("position", O.Length(255,varying=true), O.Default(None))
    /** Database column capital SqlType(VARCHAR), Length(255,true), Default(None) */
    val capital: Rep[Option[String]] = column[Option[String]]("capital", O.Length(255,varying=true), O.Default(None))
    /** Database column region SqlType(VARCHAR), Length(255,true), Default(None) */
    val region: Rep[Option[String]] = column[Option[String]]("region", O.Length(255,varying=true), O.Default(None))
    /** Database column kind SqlType(VARCHAR), Length(255,true), Default(None) */
    val kind: Rep[Option[String]] = column[Option[String]]("kind", O.Length(255,varying=true), O.Default(None))
    /** Database column represent SqlType(VARCHAR), Length(255,true), Default(None) */
    val represent: Rep[Option[String]] = column[Option[String]]("represent", O.Length(255,varying=true), O.Default(None))
    /** Database column represent_ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val representRef: Rep[Option[String]] = column[Option[String]]("represent_ref", O.Length(255,varying=true), O.Default(None))
    /** Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
    val status: Rep[Option[String]] = column[Option[String]]("status", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table BossPosition */
  lazy val BossPosition = new TableQuery(tag => new BossPosition(tag))

  /** Entity class storing rows of table BossRepresent
   *  @param bid Database column bid SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ref Database column ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ratio Database column ratio SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param capital Database column capital SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param region Database column region SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param kind Database column kind SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param status Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
  case class BossRepresentRow(bid: Int, name: Option[String] = None, ref: Option[String] = None, ratio: Option[String] = None, capital: Option[String] = None, region: Option[String] = None, kind: Option[String] = None, status: Option[String] = None)
  /** GetResult implicit for fetching BossRepresentRow objects using plain SQL queries */
  implicit def GetResultBossRepresentRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[BossRepresentRow] = GR{
    prs => import prs._
    BossRepresentRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table boss_represent. Objects of this class serve as prototypes for rows in queries. */
  class BossRepresent(_tableTag: Tag) extends profile.api.Table[BossRepresentRow](_tableTag, Some("data"), "boss_represent") {
    def * = (bid, name, ref, ratio, capital, region, kind, status) <> (BossRepresentRow.tupled, BossRepresentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(bid), name, ref, ratio, capital, region, kind, status).shaped.<>({r=>import r._; _1.map(_=> BossRepresentRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column bid SqlType(INT) */
    val bid: Rep[Int] = column[Int]("bid")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val ref: Rep[Option[String]] = column[Option[String]]("ref", O.Length(255,varying=true), O.Default(None))
    /** Database column ratio SqlType(VARCHAR), Length(255,true), Default(None) */
    val ratio: Rep[Option[String]] = column[Option[String]]("ratio", O.Length(255,varying=true), O.Default(None))
    /** Database column capital SqlType(VARCHAR), Length(255,true), Default(None) */
    val capital: Rep[Option[String]] = column[Option[String]]("capital", O.Length(255,varying=true), O.Default(None))
    /** Database column region SqlType(VARCHAR), Length(255,true), Default(None) */
    val region: Rep[Option[String]] = column[Option[String]]("region", O.Length(255,varying=true), O.Default(None))
    /** Database column kind SqlType(VARCHAR), Length(255,true), Default(None) */
    val kind: Rep[Option[String]] = column[Option[String]]("kind", O.Length(255,varying=true), O.Default(None))
    /** Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
    val status: Rep[Option[String]] = column[Option[String]]("status", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table BossRepresent */
  lazy val BossRepresent = new TableQuery(tag => new BossRepresent(tag))

  /** Entity class storing rows of table Branch
   *  @param cid Database column cid SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ref Database column ref SqlType(VARCHAR), Length(255,true), Default(None) */
  case class BranchRow(cid: Int, name: Option[String] = None, ref: Option[String] = None)
  /** GetResult implicit for fetching BranchRow objects using plain SQL queries */
  implicit def GetResultBranchRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[BranchRow] = GR{
    prs => import prs._
    BranchRow.tupled((<<[Int], <<?[String], <<?[String]))
  }
  /** Table description of table branch. Objects of this class serve as prototypes for rows in queries. */
  class Branch(_tableTag: Tag) extends profile.api.Table[BranchRow](_tableTag, Some("data"), "branch") {
    def * = (cid, name, ref) <> (BranchRow.tupled, BranchRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), name, ref).shaped.<>({r=>import r._; _1.map(_=> BranchRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val ref: Rep[Option[String]] = column[Option[String]]("ref", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Branch */
  lazy val Branch = new TableQuery(tag => new Branch(tag))

  /** Entity class storing rows of table ChangeRecord
   *  @param cid Database column cid SqlType(INT)
   *  @param date Database column date SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param project Database column project SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param before Database column before SqlType(TEXT), Default(None)
   *  @param after Database column after SqlType(TEXT), Default(None) */
  case class ChangeRecordRow(cid: Int, date: Option[String] = None, project: Option[String] = None, before: Option[String] = None, after: Option[String] = None)
  /** GetResult implicit for fetching ChangeRecordRow objects using plain SQL queries */
  implicit def GetResultChangeRecordRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[ChangeRecordRow] = GR{
    prs => import prs._
    ChangeRecordRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table change_record. Objects of this class serve as prototypes for rows in queries. */
  class ChangeRecord(_tableTag: Tag) extends profile.api.Table[ChangeRecordRow](_tableTag, Some("data"), "change_record") {
    def * = (cid, date, project, before, after) <> (ChangeRecordRow.tupled, ChangeRecordRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), date, project, before, after).shaped.<>({r=>import r._; _1.map(_=> ChangeRecordRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(255,varying=true), O.Default(None))
    /** Database column project SqlType(VARCHAR), Length(255,true), Default(None) */
    val project: Rep[Option[String]] = column[Option[String]]("project", O.Length(255,varying=true), O.Default(None))
    /** Database column before SqlType(TEXT), Default(None) */
    val before: Rep[Option[String]] = column[Option[String]]("before", O.Default(None))
    /** Database column after SqlType(TEXT), Default(None) */
    val after: Rep[Option[String]] = column[Option[String]]("after", O.Default(None))
  }
  /** Collection-like TableQuery object for table ChangeRecord */
  lazy val ChangeRecord = new TableQuery(tag => new ChangeRecord(tag))

  /** Entity class storing rows of table Company
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param status Database column status SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param representname Database column representName SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param represent Database column represent SqlType(INT), Default(None)
   *  @param capital Database column capital SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param foundTime Database column found_time SqlType(DATE), Default(None)
   *  @param mail Database column mail SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param phone Database column phone SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param addr Database column addr SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param website Database column website SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param introduction Database column introduction SqlType(TEXT), Default(None)
   *  @param img Database column img SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ref Database column ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param keyno Database column keyNo SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param money Database column money SqlType(DOUBLE), Default(Some(0.0))
   *  @param weight Database column weight SqlType(INT), Default(Some(0)) */
  case class CompanyRow(id: Int, name: String, status: Option[String] = None, representname: Option[String] = None, represent: Option[Int] = None, capital: Option[String] = None, foundTime: Option[java.sql.Date] = None, mail: Option[String] = None, phone: Option[String] = None, addr: Option[String] = None, website: Option[String] = None, introduction: Option[String] = None, img: Option[String] = None, ref: Option[String] = None, keyno: Option[String] = None, money: Option[Double] = Some(0.0), weight: Option[Int] = Some(0))
  /** GetResult implicit for fetching CompanyRow objects using plain SQL queries */
  implicit def GetResultCompanyRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[Int]], e4: GR[Option[java.sql.Date]], e5: GR[Option[Double]]): GR[CompanyRow] = GR{
    prs => import prs._
    CompanyRow.tupled((<<[Int], <<[String], <<?[String], <<?[String], <<?[Int], <<?[String], <<?[java.sql.Date], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[Double], <<?[Int]))
  }
  /** Table description of table company. Objects of this class serve as prototypes for rows in queries. */
  class Company(_tableTag: Tag) extends profile.api.Table[CompanyRow](_tableTag, Some("data"), "company") {
    def * = (id, name, status, representname, represent, capital, foundTime, mail, phone, addr, website, introduction, img, ref, keyno, money, weight) <> (CompanyRow.tupled, CompanyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), status, representname, represent, capital, foundTime, mail, phone, addr, website, introduction, img, ref, keyno, money, weight).shaped.<>({r=>import r._; _1.map(_=> CompanyRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
    val status: Rep[Option[String]] = column[Option[String]]("status", O.Length(255,varying=true), O.Default(None))
    /** Database column representName SqlType(VARCHAR), Length(255,true), Default(None) */
    val representname: Rep[Option[String]] = column[Option[String]]("representName", O.Length(255,varying=true), O.Default(None))
    /** Database column represent SqlType(INT), Default(None) */
    val represent: Rep[Option[Int]] = column[Option[Int]]("represent", O.Default(None))
    /** Database column capital SqlType(VARCHAR), Length(255,true), Default(None) */
    val capital: Rep[Option[String]] = column[Option[String]]("capital", O.Length(255,varying=true), O.Default(None))
    /** Database column found_time SqlType(DATE), Default(None) */
    val foundTime: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("found_time", O.Default(None))
    /** Database column mail SqlType(VARCHAR), Length(255,true), Default(None) */
    val mail: Rep[Option[String]] = column[Option[String]]("mail", O.Length(255,varying=true), O.Default(None))
    /** Database column phone SqlType(VARCHAR), Length(255,true), Default(None) */
    val phone: Rep[Option[String]] = column[Option[String]]("phone", O.Length(255,varying=true), O.Default(None))
    /** Database column addr SqlType(VARCHAR), Length(255,true), Default(None) */
    val addr: Rep[Option[String]] = column[Option[String]]("addr", O.Length(255,varying=true), O.Default(None))
    /** Database column website SqlType(VARCHAR), Length(255,true), Default(None) */
    val website: Rep[Option[String]] = column[Option[String]]("website", O.Length(255,varying=true), O.Default(None))
    /** Database column introduction SqlType(TEXT), Default(None) */
    val introduction: Rep[Option[String]] = column[Option[String]]("introduction", O.Default(None))
    /** Database column img SqlType(VARCHAR), Length(255,true), Default(None) */
    val img: Rep[Option[String]] = column[Option[String]]("img", O.Length(255,varying=true), O.Default(None))
    /** Database column ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val ref: Rep[Option[String]] = column[Option[String]]("ref", O.Length(255,varying=true), O.Default(None))
    /** Database column keyNo SqlType(VARCHAR), Length(255,true), Default(None) */
    val keyno: Rep[Option[String]] = column[Option[String]]("keyNo", O.Length(255,varying=true), O.Default(None))
    /** Database column money SqlType(DOUBLE), Default(Some(0.0)) */
    val money: Rep[Option[Double]] = column[Option[Double]]("money", O.Default(Some(0.0)))
    /** Database column weight SqlType(INT), Default(Some(0)) */
    val weight: Rep[Option[Int]] = column[Option[Int]]("weight", O.Default(Some(0)))
  }
  /** Collection-like TableQuery object for table Company */
  lazy val Company = new TableQuery(tag => new Company(tag))

  /** Entity class storing rows of table CompanyGraph
   *  @param cid Database column cid SqlType(INT)
   *  @param `type` Database column type SqlType(INT)
   *  @param data Database column data SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
  case class CompanyGraphRow(cid: Int, `type`: Int, data: Option[String] = None)
  /** GetResult implicit for fetching CompanyGraphRow objects using plain SQL queries */
  implicit def GetResultCompanyGraphRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[CompanyGraphRow] = GR{
    prs => import prs._
    CompanyGraphRow.tupled((<<[Int], <<[Int], <<?[String]))
  }
  /** Table description of table company_graph. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class CompanyGraph(_tableTag: Tag) extends profile.api.Table[CompanyGraphRow](_tableTag, Some("data"), "company_graph") {
    def * = (cid, `type`, data) <> (CompanyGraphRow.tupled, CompanyGraphRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), Rep.Some(`type`), data).shaped.<>({r=>import r._; _1.map(_=> CompanyGraphRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column type SqlType(INT)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[Int] = column[Int]("type")
    /** Database column data SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
    val data: Rep[Option[String]] = column[Option[String]]("data", O.Length(2147483647,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table CompanyGraph */
  lazy val CompanyGraph = new TableQuery(tag => new CompanyGraph(tag))

  /** Entity class storing rows of table MainPersonnel
   *  @param cid Database column cid SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param href Database column href SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param count Database column count SqlType(INT), Default(None)
   *  @param position Database column position SqlType(VARCHAR), Length(255,true), Default(None) */
  case class MainPersonnelRow(cid: Int, name: Option[String] = None, href: Option[String] = None, count: Option[Int] = None, position: Option[String] = None)
  /** GetResult implicit for fetching MainPersonnelRow objects using plain SQL queries */
  implicit def GetResultMainPersonnelRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]]): GR[MainPersonnelRow] = GR{
    prs => import prs._
    MainPersonnelRow.tupled((<<[Int], <<?[String], <<?[String], <<?[Int], <<?[String]))
  }
  /** Table description of table main_personnel. Objects of this class serve as prototypes for rows in queries. */
  class MainPersonnel(_tableTag: Tag) extends profile.api.Table[MainPersonnelRow](_tableTag, Some("data"), "main_personnel") {
    def * = (cid, name, href, count, position) <> (MainPersonnelRow.tupled, MainPersonnelRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), name, href, count, position).shaped.<>({r=>import r._; _1.map(_=> MainPersonnelRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column href SqlType(VARCHAR), Length(255,true), Default(None) */
    val href: Rep[Option[String]] = column[Option[String]]("href", O.Length(255,varying=true), O.Default(None))
    /** Database column count SqlType(INT), Default(None) */
    val count: Rep[Option[Int]] = column[Option[Int]]("count", O.Default(None))
    /** Database column position SqlType(VARCHAR), Length(255,true), Default(None) */
    val position: Rep[Option[String]] = column[Option[String]]("position", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table MainPersonnel */
  lazy val MainPersonnel = new TableQuery(tag => new MainPersonnel(tag))

  /** Entity class storing rows of table OutboundInvestment
   *  @param cid Database column cid SqlType(INT)
   *  @param source Database column source SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param sourceHref Database column source_href SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param target Database column target SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param targetHref Database column target_href SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param capital Database column capital SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ratio Database column ratio SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param foundTime Database column found_time SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param status Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
  case class OutboundInvestmentRow(cid: Int, source: Option[String] = None, sourceHref: Option[String] = None, target: Option[String] = None, targetHref: Option[String] = None, capital: Option[String] = None, ratio: Option[String] = None, foundTime: Option[String] = None, status: Option[String] = None)
  /** GetResult implicit for fetching OutboundInvestmentRow objects using plain SQL queries */
  implicit def GetResultOutboundInvestmentRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[OutboundInvestmentRow] = GR{
    prs => import prs._
    OutboundInvestmentRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table outbound_investment. Objects of this class serve as prototypes for rows in queries. */
  class OutboundInvestment(_tableTag: Tag) extends profile.api.Table[OutboundInvestmentRow](_tableTag, Some("data"), "outbound_investment") {
    def * = (cid, source, sourceHref, target, targetHref, capital, ratio, foundTime, status) <> (OutboundInvestmentRow.tupled, OutboundInvestmentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), source, sourceHref, target, targetHref, capital, ratio, foundTime, status).shaped.<>({r=>import r._; _1.map(_=> OutboundInvestmentRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column source SqlType(VARCHAR), Length(255,true), Default(None) */
    val source: Rep[Option[String]] = column[Option[String]]("source", O.Length(255,varying=true), O.Default(None))
    /** Database column source_href SqlType(VARCHAR), Length(255,true), Default(None) */
    val sourceHref: Rep[Option[String]] = column[Option[String]]("source_href", O.Length(255,varying=true), O.Default(None))
    /** Database column target SqlType(VARCHAR), Length(255,true), Default(None) */
    val target: Rep[Option[String]] = column[Option[String]]("target", O.Length(255,varying=true), O.Default(None))
    /** Database column target_href SqlType(VARCHAR), Length(255,true), Default(None) */
    val targetHref: Rep[Option[String]] = column[Option[String]]("target_href", O.Length(255,varying=true), O.Default(None))
    /** Database column capital SqlType(VARCHAR), Length(255,true), Default(None) */
    val capital: Rep[Option[String]] = column[Option[String]]("capital", O.Length(255,varying=true), O.Default(None))
    /** Database column ratio SqlType(VARCHAR), Length(255,true), Default(None) */
    val ratio: Rep[Option[String]] = column[Option[String]]("ratio", O.Length(255,varying=true), O.Default(None))
    /** Database column found_time SqlType(VARCHAR), Length(255,true), Default(None) */
    val foundTime: Rep[Option[String]] = column[Option[String]]("found_time", O.Length(255,varying=true), O.Default(None))
    /** Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
    val status: Rep[Option[String]] = column[Option[String]]("status", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table OutboundInvestment */
  lazy val OutboundInvestment = new TableQuery(tag => new OutboundInvestment(tag))

  /** Entity class storing rows of table Person
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param addr Database column addr SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param count Database column count SqlType(INT), Default(None)
   *  @param avator Database column avator SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param introduction Database column introduction SqlType(TEXT), Default(None)
   *  @param flag Database column flag SqlType(BIT), Default(Some(false)) */
  case class PersonRow(id: Int, name: String, addr: Option[String] = None, count: Option[Int] = None, avator: Option[String] = None, introduction: Option[String] = None, flag: Option[Boolean] = Some(false))
  /** GetResult implicit for fetching PersonRow objects using plain SQL queries */
  implicit def GetResultPersonRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[Int]], e4: GR[Option[Boolean]]): GR[PersonRow] = GR{
    prs => import prs._
    PersonRow.tupled((<<[Int], <<[String], <<?[String], <<?[Int], <<?[String], <<?[String], <<?[Boolean]))
  }
  /** Table description of table person. Objects of this class serve as prototypes for rows in queries. */
  class Person(_tableTag: Tag) extends profile.api.Table[PersonRow](_tableTag, Some("data"), "person") {
    def * = (id, name, addr, count, avator, introduction, flag) <> (PersonRow.tupled, PersonRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), addr, count, avator, introduction, flag).shaped.<>({r=>import r._; _1.map(_=> PersonRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column addr SqlType(VARCHAR), Length(255,true), Default(None) */
    val addr: Rep[Option[String]] = column[Option[String]]("addr", O.Length(255,varying=true), O.Default(None))
    /** Database column count SqlType(INT), Default(None) */
    val count: Rep[Option[Int]] = column[Option[Int]]("count", O.Default(None))
    /** Database column avator SqlType(VARCHAR), Length(255,true), Default(None) */
    val avator: Rep[Option[String]] = column[Option[String]]("avator", O.Length(255,varying=true), O.Default(None))
    /** Database column introduction SqlType(TEXT), Default(None) */
    val introduction: Rep[Option[String]] = column[Option[String]]("introduction", O.Default(None))
    /** Database column flag SqlType(BIT), Default(Some(false)) */
    val flag: Rep[Option[Boolean]] = column[Option[Boolean]]("flag", O.Default(Some(false)))
  }
  /** Collection-like TableQuery object for table Person */
  lazy val Person = new TableQuery(tag => new Person(tag))

  /** Entity class storing rows of table ShareholderInformation
   *  @param cid Database column cid SqlType(INT)
   *  @param shareholderName Database column shareholder_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param shareholderRef Database column shareholder_ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param count Database column count SqlType(INT), Default(None)
   *  @param ratio Database column ratio SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param contribution Database column contribution SqlType(DOUBLE), Default(None)
   *  @param date Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
  case class ShareholderInformationRow(cid: Int, shareholderName: Option[String] = None, shareholderRef: Option[String] = None, count: Option[Int] = None, ratio: Option[String] = None, contribution: Option[Double] = None, date: Option[String] = None)
  /** GetResult implicit for fetching ShareholderInformationRow objects using plain SQL queries */
  implicit def GetResultShareholderInformationRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[Double]]): GR[ShareholderInformationRow] = GR{
    prs => import prs._
    ShareholderInformationRow.tupled((<<[Int], <<?[String], <<?[String], <<?[Int], <<?[String], <<?[Double], <<?[String]))
  }
  /** Table description of table shareholder_information. Objects of this class serve as prototypes for rows in queries. */
  class ShareholderInformation(_tableTag: Tag) extends profile.api.Table[ShareholderInformationRow](_tableTag, Some("data"), "shareholder_information") {
    def * = (cid, shareholderName, shareholderRef, count, ratio, contribution, date) <> (ShareholderInformationRow.tupled, ShareholderInformationRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), shareholderName, shareholderRef, count, ratio, contribution, date).shaped.<>({r=>import r._; _1.map(_=> ShareholderInformationRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column shareholder_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val shareholderName: Rep[Option[String]] = column[Option[String]]("shareholder_name", O.Length(255,varying=true), O.Default(None))
    /** Database column shareholder_ref SqlType(VARCHAR), Length(255,true), Default(None) */
    val shareholderRef: Rep[Option[String]] = column[Option[String]]("shareholder_ref", O.Length(255,varying=true), O.Default(None))
    /** Database column count SqlType(INT), Default(None) */
    val count: Rep[Option[Int]] = column[Option[Int]]("count", O.Default(None))
    /** Database column ratio SqlType(VARCHAR), Length(255,true), Default(None) */
    val ratio: Rep[Option[String]] = column[Option[String]]("ratio", O.Length(255,varying=true), O.Default(None))
    /** Database column contribution SqlType(DOUBLE), Default(None) */
    val contribution: Rep[Option[Double]] = column[Option[Double]]("contribution", O.Default(None))
    /** Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table ShareholderInformation */
  lazy val ShareholderInformation = new TableQuery(tag => new ShareholderInformation(tag))
}
