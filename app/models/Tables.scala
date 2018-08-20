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
  lazy val schema: profile.SchemaDescription = Array(AdministrativeLicenseCh.schema, AdministrativeLicenseIc.schema, BasicInfo.schema, BiddingInformation.schema, BossHistoryInvestment.schema, BossHistoryPosition.schema, BossHistoryRepresent.schema, BossHoldingCompany.schema, BossInvestment.schema, BossPosition.schema, BossRepresent.schema, Branch.schema, ChangeRecord.schema, Company.schema, CompanyGraph.schema, CourtNotice.schema, FinancingInformation.schema, InterestedPeople.schema, MainPersonnel.schema, NewsLyrics.schema, OpeningNotice.schema, OutboundInvestment.schema, Person.schema, ProductInformation.schema, PublicNumber.schema, Recruitment.schema, Referee.schema, ResearchReport.schema, ShareholderInformation.schema, ShortInfo.schema, TaxCredit.schema, User.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table AdministrativeLicenseCh
   *  @param cid Database column cid SqlType(INT)
   *  @param project Database column project SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param region Database column region SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param date Database column date SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param content Database column content SqlType(VARCHAR), Length(255,true), Default(None) */
  case class AdministrativeLicenseChRow(cid: Int, project: Option[String] = None, region: Option[String] = None, date: Option[String] = None, content: Option[String] = None)
  /** GetResult implicit for fetching AdministrativeLicenseChRow objects using plain SQL queries */
  implicit def GetResultAdministrativeLicenseChRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[AdministrativeLicenseChRow] = GR{
    prs => import prs._
    AdministrativeLicenseChRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table administrative_license_ch. Objects of this class serve as prototypes for rows in queries. */
  class AdministrativeLicenseCh(_tableTag: Tag) extends profile.api.Table[AdministrativeLicenseChRow](_tableTag, Some("data"), "administrative_license_ch") {
    def * = (cid, project, region, date, content) <> (AdministrativeLicenseChRow.tupled, AdministrativeLicenseChRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), project, region, date, content).shaped.<>({r=>import r._; _1.map(_=> AdministrativeLicenseChRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column project SqlType(VARCHAR), Length(255,true), Default(None) */
    val project: Rep[Option[String]] = column[Option[String]]("project", O.Length(255,varying=true), O.Default(None))
    /** Database column region SqlType(VARCHAR), Length(255,true), Default(None) */
    val region: Rep[Option[String]] = column[Option[String]]("region", O.Length(255,varying=true), O.Default(None))
    /** Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(255,varying=true), O.Default(None))
    /** Database column content SqlType(VARCHAR), Length(255,true), Default(None) */
    val content: Rep[Option[String]] = column[Option[String]]("content", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table AdministrativeLicenseCh */
  lazy val AdministrativeLicenseCh = new TableQuery(tag => new AdministrativeLicenseCh(tag))

  /** Entity class storing rows of table AdministrativeLicenseIc
   *  @param cid Database column cid SqlType(INT)
   *  @param num Database column num SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param fileName Database column file_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param datefrom Database column datefrom SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param dateto Database column dateto SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param authority Database column authority SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param content Database column content SqlType(VARCHAR), Length(255,true), Default(None) */
  case class AdministrativeLicenseIcRow(cid: Int, num: Option[String] = None, fileName: Option[String] = None, datefrom: Option[String] = None, dateto: Option[String] = None, authority: Option[String] = None, content: Option[String] = None)
  /** GetResult implicit for fetching AdministrativeLicenseIcRow objects using plain SQL queries */
  implicit def GetResultAdministrativeLicenseIcRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[AdministrativeLicenseIcRow] = GR{
    prs => import prs._
    AdministrativeLicenseIcRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table administrative_license_ic. Objects of this class serve as prototypes for rows in queries. */
  class AdministrativeLicenseIc(_tableTag: Tag) extends profile.api.Table[AdministrativeLicenseIcRow](_tableTag, Some("data"), "administrative_license_ic") {
    def * = (cid, num, fileName, datefrom, dateto, authority, content) <> (AdministrativeLicenseIcRow.tupled, AdministrativeLicenseIcRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), num, fileName, datefrom, dateto, authority, content).shaped.<>({r=>import r._; _1.map(_=> AdministrativeLicenseIcRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column num SqlType(VARCHAR), Length(255,true), Default(None) */
    val num: Rep[Option[String]] = column[Option[String]]("num", O.Length(255,varying=true), O.Default(None))
    /** Database column file_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val fileName: Rep[Option[String]] = column[Option[String]]("file_name", O.Length(255,varying=true), O.Default(None))
    /** Database column datefrom SqlType(VARCHAR), Length(255,true), Default(None) */
    val datefrom: Rep[Option[String]] = column[Option[String]]("datefrom", O.Length(255,varying=true), O.Default(None))
    /** Database column dateto SqlType(VARCHAR), Length(255,true), Default(None) */
    val dateto: Rep[Option[String]] = column[Option[String]]("dateto", O.Length(255,varying=true), O.Default(None))
    /** Database column authority SqlType(VARCHAR), Length(255,true), Default(None) */
    val authority: Rep[Option[String]] = column[Option[String]]("authority", O.Length(255,varying=true), O.Default(None))
    /** Database column content SqlType(VARCHAR), Length(255,true), Default(None) */
    val content: Rep[Option[String]] = column[Option[String]]("content", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table AdministrativeLicenseIc */
  lazy val AdministrativeLicenseIc = new TableQuery(tag => new AdministrativeLicenseIc(tag))

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

  /** Entity class storing rows of table BiddingInformation
   *  @param cid Database column cid SqlType(INT)
   *  @param desc Database column desc SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param date Database column date SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param region Database column region SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param kind Database column kind SqlType(VARCHAR), Length(255,true), Default(None) */
  case class BiddingInformationRow(cid: Int, desc: Option[String] = None, date: Option[String] = None, region: Option[String] = None, kind: Option[String] = None)
  /** GetResult implicit for fetching BiddingInformationRow objects using plain SQL queries */
  implicit def GetResultBiddingInformationRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[BiddingInformationRow] = GR{
    prs => import prs._
    BiddingInformationRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table bidding_information. Objects of this class serve as prototypes for rows in queries. */
  class BiddingInformation(_tableTag: Tag) extends profile.api.Table[BiddingInformationRow](_tableTag, Some("data"), "bidding_information") {
    def * = (cid, desc, date, region, kind) <> (BiddingInformationRow.tupled, BiddingInformationRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), desc, date, region, kind).shaped.<>({r=>import r._; _1.map(_=> BiddingInformationRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column desc SqlType(VARCHAR), Length(255,true), Default(None) */
    val desc: Rep[Option[String]] = column[Option[String]]("desc", O.Length(255,varying=true), O.Default(None))
    /** Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(255,varying=true), O.Default(None))
    /** Database column region SqlType(VARCHAR), Length(255,true), Default(None) */
    val region: Rep[Option[String]] = column[Option[String]]("region", O.Length(255,varying=true), O.Default(None))
    /** Database column kind SqlType(VARCHAR), Length(255,true), Default(None) */
    val kind: Rep[Option[String]] = column[Option[String]]("kind", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table BiddingInformation */
  lazy val BiddingInformation = new TableQuery(tag => new BiddingInformation(tag))

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

  /** Entity class storing rows of table CourtNotice
   *  @param cid Database column cid SqlType(INT)
   *  @param party Database column party SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param `type` Database column type SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param announcer Database column announcer SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param date Database column date SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param content Database column content SqlType(VARCHAR), Length(255,true), Default(None) */
  case class CourtNoticeRow(cid: Int, party: Option[String] = None, `type`: Option[String] = None, announcer: Option[String] = None, date: Option[String] = None, content: Option[String] = None)
  /** GetResult implicit for fetching CourtNoticeRow objects using plain SQL queries */
  implicit def GetResultCourtNoticeRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[CourtNoticeRow] = GR{
    prs => import prs._
    CourtNoticeRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table court_notice. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class CourtNotice(_tableTag: Tag) extends profile.api.Table[CourtNoticeRow](_tableTag, Some("data"), "court_notice") {
    def * = (cid, party, `type`, announcer, date, content) <> (CourtNoticeRow.tupled, CourtNoticeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), party, `type`, announcer, date, content).shaped.<>({r=>import r._; _1.map(_=> CourtNoticeRow.tupled((_1.get, _2, _3, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column party SqlType(VARCHAR), Length(255,true), Default(None) */
    val party: Rep[Option[String]] = column[Option[String]]("party", O.Length(255,varying=true), O.Default(None))
    /** Database column type SqlType(VARCHAR), Length(255,true), Default(None)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[Option[String]] = column[Option[String]]("type", O.Length(255,varying=true), O.Default(None))
    /** Database column announcer SqlType(VARCHAR), Length(255,true), Default(None) */
    val announcer: Rep[Option[String]] = column[Option[String]]("announcer", O.Length(255,varying=true), O.Default(None))
    /** Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(255,varying=true), O.Default(None))
    /** Database column content SqlType(VARCHAR), Length(255,true), Default(None) */
    val content: Rep[Option[String]] = column[Option[String]]("content", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table CourtNotice */
  lazy val CourtNotice = new TableQuery(tag => new CourtNotice(tag))

  /** Entity class storing rows of table FinancingInformation
   *  @param cid Database column cid SqlType(INT)
   *  @param date Database column date SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param level Database column level SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param money Database column money SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param source Database column source SqlType(VARCHAR), Length(255,true), Default(None) */
  case class FinancingInformationRow(cid: Int, date: Option[String] = None, name: Option[String] = None, level: Option[String] = None, money: Option[String] = None, source: Option[String] = None)
  /** GetResult implicit for fetching FinancingInformationRow objects using plain SQL queries */
  implicit def GetResultFinancingInformationRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[FinancingInformationRow] = GR{
    prs => import prs._
    FinancingInformationRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table financing_information. Objects of this class serve as prototypes for rows in queries. */
  class FinancingInformation(_tableTag: Tag) extends profile.api.Table[FinancingInformationRow](_tableTag, Some("data"), "financing_information") {
    def * = (cid, date, name, level, money, source) <> (FinancingInformationRow.tupled, FinancingInformationRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), date, name, level, money, source).shaped.<>({r=>import r._; _1.map(_=> FinancingInformationRow.tupled((_1.get, _2, _3, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(255,varying=true), O.Default(None))
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column level SqlType(VARCHAR), Length(255,true), Default(None) */
    val level: Rep[Option[String]] = column[Option[String]]("level", O.Length(255,varying=true), O.Default(None))
    /** Database column money SqlType(VARCHAR), Length(255,true), Default(None) */
    val money: Rep[Option[String]] = column[Option[String]]("money", O.Length(255,varying=true), O.Default(None))
    /** Database column source SqlType(VARCHAR), Length(255,true), Default(None) */
    val source: Rep[Option[String]] = column[Option[String]]("source", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table FinancingInformation */
  lazy val FinancingInformation = new TableQuery(tag => new FinancingInformation(tag))

  /** Entity class storing rows of table InterestedPeople
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param img Database column img SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param desc Database column desc SqlType(VARCHAR), Length(255,true), Default(None) */
  case class InterestedPeopleRow(name: Option[String] = None, img: Option[String] = None, desc: Option[String] = None)
  /** GetResult implicit for fetching InterestedPeopleRow objects using plain SQL queries */
  implicit def GetResultInterestedPeopleRow(implicit e0: GR[Option[String]]): GR[InterestedPeopleRow] = GR{
    prs => import prs._
    InterestedPeopleRow.tupled((<<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table interested_people. Objects of this class serve as prototypes for rows in queries. */
  class InterestedPeople(_tableTag: Tag) extends profile.api.Table[InterestedPeopleRow](_tableTag, Some("data"), "interested_people") {
    def * = (name, img, desc) <> (InterestedPeopleRow.tupled, InterestedPeopleRow.unapply)

    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column img SqlType(VARCHAR), Length(255,true), Default(None) */
    val img: Rep[Option[String]] = column[Option[String]]("img", O.Length(255,varying=true), O.Default(None))
    /** Database column desc SqlType(VARCHAR), Length(255,true), Default(None) */
    val desc: Rep[Option[String]] = column[Option[String]]("desc", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table InterestedPeople */
  lazy val InterestedPeople = new TableQuery(tag => new InterestedPeople(tag))

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

  /** Entity class storing rows of table NewsLyrics
   *  @param cid Database column cid SqlType(INT)
   *  @param title Database column title SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param source Database column source SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param date Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
  case class NewsLyricsRow(cid: Int, title: Option[String] = None, source: Option[String] = None, date: Option[String] = None)
  /** GetResult implicit for fetching NewsLyricsRow objects using plain SQL queries */
  implicit def GetResultNewsLyricsRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[NewsLyricsRow] = GR{
    prs => import prs._
    NewsLyricsRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table news_lyrics. Objects of this class serve as prototypes for rows in queries. */
  class NewsLyrics(_tableTag: Tag) extends profile.api.Table[NewsLyricsRow](_tableTag, Some("data"), "news_lyrics") {
    def * = (cid, title, source, date) <> (NewsLyricsRow.tupled, NewsLyricsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), title, source, date).shaped.<>({r=>import r._; _1.map(_=> NewsLyricsRow.tupled((_1.get, _2, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column title SqlType(VARCHAR), Length(255,true), Default(None) */
    val title: Rep[Option[String]] = column[Option[String]]("title", O.Length(255,varying=true), O.Default(None))
    /** Database column source SqlType(VARCHAR), Length(255,true), Default(None) */
    val source: Rep[Option[String]] = column[Option[String]]("source", O.Length(255,varying=true), O.Default(None))
    /** Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table NewsLyrics */
  lazy val NewsLyrics = new TableQuery(tag => new NewsLyrics(tag))

  /** Entity class storing rows of table OpeningNotice
   *  @param cid Database column cid SqlType(INT)
   *  @param num Database column num SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param date Database column date SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param cause Database column cause SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param source Database column source SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param target Database column target SqlType(VARCHAR), Length(255,true), Default(None) */
  case class OpeningNoticeRow(cid: Int, num: Option[String] = None, date: Option[String] = None, cause: Option[String] = None, source: Option[String] = None, target: Option[String] = None)
  /** GetResult implicit for fetching OpeningNoticeRow objects using plain SQL queries */
  implicit def GetResultOpeningNoticeRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[OpeningNoticeRow] = GR{
    prs => import prs._
    OpeningNoticeRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table opening_notice. Objects of this class serve as prototypes for rows in queries. */
  class OpeningNotice(_tableTag: Tag) extends profile.api.Table[OpeningNoticeRow](_tableTag, Some("data"), "opening_notice") {
    def * = (cid, num, date, cause, source, target) <> (OpeningNoticeRow.tupled, OpeningNoticeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), num, date, cause, source, target).shaped.<>({r=>import r._; _1.map(_=> OpeningNoticeRow.tupled((_1.get, _2, _3, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column num SqlType(VARCHAR), Length(255,true), Default(None) */
    val num: Rep[Option[String]] = column[Option[String]]("num", O.Length(255,varying=true), O.Default(None))
    /** Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(255,varying=true), O.Default(None))
    /** Database column cause SqlType(VARCHAR), Length(255,true), Default(None) */
    val cause: Rep[Option[String]] = column[Option[String]]("cause", O.Length(255,varying=true), O.Default(None))
    /** Database column source SqlType(VARCHAR), Length(255,true), Default(None) */
    val source: Rep[Option[String]] = column[Option[String]]("source", O.Length(255,varying=true), O.Default(None))
    /** Database column target SqlType(VARCHAR), Length(255,true), Default(None) */
    val target: Rep[Option[String]] = column[Option[String]]("target", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table OpeningNotice */
  lazy val OpeningNotice = new TableQuery(tag => new OpeningNotice(tag))

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

  /** Entity class storing rows of table ProductInformation
   *  @param cid Database column cid SqlType(INT)
   *  @param src Database column src SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param financing Database column financing SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param foundTime Database column found_time SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param region Database column region SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param introduction Database column introduction SqlType(TEXT), Default(None) */
  case class ProductInformationRow(cid: Int, src: Option[String] = None, name: Option[String] = None, financing: Option[String] = None, foundTime: Option[String] = None, region: Option[String] = None, introduction: Option[String] = None)
  /** GetResult implicit for fetching ProductInformationRow objects using plain SQL queries */
  implicit def GetResultProductInformationRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[ProductInformationRow] = GR{
    prs => import prs._
    ProductInformationRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table product_information. Objects of this class serve as prototypes for rows in queries. */
  class ProductInformation(_tableTag: Tag) extends profile.api.Table[ProductInformationRow](_tableTag, Some("data"), "product_information") {
    def * = (cid, src, name, financing, foundTime, region, introduction) <> (ProductInformationRow.tupled, ProductInformationRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), src, name, financing, foundTime, region, introduction).shaped.<>({r=>import r._; _1.map(_=> ProductInformationRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column src SqlType(VARCHAR), Length(255,true), Default(None) */
    val src: Rep[Option[String]] = column[Option[String]]("src", O.Length(255,varying=true), O.Default(None))
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column financing SqlType(VARCHAR), Length(255,true), Default(None) */
    val financing: Rep[Option[String]] = column[Option[String]]("financing", O.Length(255,varying=true), O.Default(None))
    /** Database column found_time SqlType(VARCHAR), Length(255,true), Default(None) */
    val foundTime: Rep[Option[String]] = column[Option[String]]("found_time", O.Length(255,varying=true), O.Default(None))
    /** Database column region SqlType(VARCHAR), Length(255,true), Default(None) */
    val region: Rep[Option[String]] = column[Option[String]]("region", O.Length(255,varying=true), O.Default(None))
    /** Database column introduction SqlType(TEXT), Default(None) */
    val introduction: Rep[Option[String]] = column[Option[String]]("introduction", O.Default(None))
  }
  /** Collection-like TableQuery object for table ProductInformation */
  lazy val ProductInformation = new TableQuery(tag => new ProductInformation(tag))

  /** Entity class storing rows of table PublicNumber
   *  @param cid Database column cid SqlType(INT)
   *  @param avator Database column avator SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param code Database column code SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param introduction Database column introduction SqlType(TEXT), Default(None) */
  case class PublicNumberRow(cid: Int, avator: Option[String] = None, name: Option[String] = None, code: Option[String] = None, introduction: Option[String] = None)
  /** GetResult implicit for fetching PublicNumberRow objects using plain SQL queries */
  implicit def GetResultPublicNumberRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[PublicNumberRow] = GR{
    prs => import prs._
    PublicNumberRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table public_number. Objects of this class serve as prototypes for rows in queries. */
  class PublicNumber(_tableTag: Tag) extends profile.api.Table[PublicNumberRow](_tableTag, Some("data"), "public_number") {
    def * = (cid, avator, name, code, introduction) <> (PublicNumberRow.tupled, PublicNumberRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), avator, name, code, introduction).shaped.<>({r=>import r._; _1.map(_=> PublicNumberRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column avator SqlType(VARCHAR), Length(255,true), Default(None) */
    val avator: Rep[Option[String]] = column[Option[String]]("avator", O.Length(255,varying=true), O.Default(None))
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column code SqlType(VARCHAR), Length(255,true), Default(None) */
    val code: Rep[Option[String]] = column[Option[String]]("code", O.Length(255,varying=true), O.Default(None))
    /** Database column introduction SqlType(TEXT), Default(None) */
    val introduction: Rep[Option[String]] = column[Option[String]]("introduction", O.Default(None))
  }
  /** Collection-like TableQuery object for table PublicNumber */
  lazy val PublicNumber = new TableQuery(tag => new PublicNumber(tag))

  /** Entity class storing rows of table Recruitment
   *  @param cid Database column cid SqlType(INT)
   *  @param date Database column date SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param position Database column position SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param money Database column money SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param education Database column education SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param experience Database column experience SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param city Database column city SqlType(VARCHAR), Length(255,true), Default(None) */
  case class RecruitmentRow(cid: Int, date: Option[String] = None, position: Option[String] = None, money: Option[String] = None, education: Option[String] = None, experience: Option[String] = None, city: Option[String] = None)
  /** GetResult implicit for fetching RecruitmentRow objects using plain SQL queries */
  implicit def GetResultRecruitmentRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[RecruitmentRow] = GR{
    prs => import prs._
    RecruitmentRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table recruitment. Objects of this class serve as prototypes for rows in queries. */
  class Recruitment(_tableTag: Tag) extends profile.api.Table[RecruitmentRow](_tableTag, Some("data"), "recruitment") {
    def * = (cid, date, position, money, education, experience, city) <> (RecruitmentRow.tupled, RecruitmentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), date, position, money, education, experience, city).shaped.<>({r=>import r._; _1.map(_=> RecruitmentRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(255,varying=true), O.Default(None))
    /** Database column position SqlType(VARCHAR), Length(255,true), Default(None) */
    val position: Rep[Option[String]] = column[Option[String]]("position", O.Length(255,varying=true), O.Default(None))
    /** Database column money SqlType(VARCHAR), Length(255,true), Default(None) */
    val money: Rep[Option[String]] = column[Option[String]]("money", O.Length(255,varying=true), O.Default(None))
    /** Database column education SqlType(VARCHAR), Length(255,true), Default(None) */
    val education: Rep[Option[String]] = column[Option[String]]("education", O.Length(255,varying=true), O.Default(None))
    /** Database column experience SqlType(VARCHAR), Length(255,true), Default(None) */
    val experience: Rep[Option[String]] = column[Option[String]]("experience", O.Length(255,varying=true), O.Default(None))
    /** Database column city SqlType(VARCHAR), Length(255,true), Default(None) */
    val city: Rep[Option[String]] = column[Option[String]]("city", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Recruitment */
  lazy val Recruitment = new TableQuery(tag => new Recruitment(tag))

  /** Entity class storing rows of table Referee
   *  @param cid Database column cid SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param date Database column date SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param num Database column num SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param identity Database column identity SqlType(TEXT), Default(None)
   *  @param court Database column court SqlType(VARCHAR), Length(255,true), Default(None) */
  case class RefereeRow(cid: Int, name: Option[String] = None, date: Option[String] = None, num: Option[String] = None, identity: Option[String] = None, court: Option[String] = None)
  /** GetResult implicit for fetching RefereeRow objects using plain SQL queries */
  implicit def GetResultRefereeRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[RefereeRow] = GR{
    prs => import prs._
    RefereeRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table referee. Objects of this class serve as prototypes for rows in queries. */
  class Referee(_tableTag: Tag) extends profile.api.Table[RefereeRow](_tableTag, Some("data"), "referee") {
    def * = (cid, name, date, num, identity, court) <> (RefereeRow.tupled, RefereeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), name, date, num, identity, court).shaped.<>({r=>import r._; _1.map(_=> RefereeRow.tupled((_1.get, _2, _3, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(255,varying=true), O.Default(None))
    /** Database column num SqlType(VARCHAR), Length(255,true), Default(None) */
    val num: Rep[Option[String]] = column[Option[String]]("num", O.Length(255,varying=true), O.Default(None))
    /** Database column identity SqlType(TEXT), Default(None) */
    val identity: Rep[Option[String]] = column[Option[String]]("identity", O.Default(None))
    /** Database column court SqlType(VARCHAR), Length(255,true), Default(None) */
    val court: Rep[Option[String]] = column[Option[String]]("court", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Referee */
  lazy val Referee = new TableQuery(tag => new Referee(tag))

  /** Entity class storing rows of table ResearchReport
   *  @param cid Database column cid SqlType(INT)
   *  @param content Database column content SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param date Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
  case class ResearchReportRow(cid: Int, content: Option[String] = None, date: Option[String] = None)
  /** GetResult implicit for fetching ResearchReportRow objects using plain SQL queries */
  implicit def GetResultResearchReportRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[ResearchReportRow] = GR{
    prs => import prs._
    ResearchReportRow.tupled((<<[Int], <<?[String], <<?[String]))
  }
  /** Table description of table research_report. Objects of this class serve as prototypes for rows in queries. */
  class ResearchReport(_tableTag: Tag) extends profile.api.Table[ResearchReportRow](_tableTag, Some("data"), "research_report") {
    def * = (cid, content, date) <> (ResearchReportRow.tupled, ResearchReportRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), content, date).shaped.<>({r=>import r._; _1.map(_=> ResearchReportRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column content SqlType(VARCHAR), Length(255,true), Default(None) */
    val content: Rep[Option[String]] = column[Option[String]]("content", O.Length(255,varying=true), O.Default(None))
    /** Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table ResearchReport */
  lazy val ResearchReport = new TableQuery(tag => new ResearchReport(tag))

  /** Entity class storing rows of table ShareholderInformation
   *  @param cid Database column cid SqlType(INT)
   *  @param shareholderName Database column shareholder_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param shareholderRef Database column shareholder_ref SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param count Database column count SqlType(INT), Default(None)
   *  @param ratio Database column ratio SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param contribution Database column contribution SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param date Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
  case class ShareholderInformationRow(cid: Int, shareholderName: Option[String] = None, shareholderRef: Option[String] = None, count: Option[Int] = None, ratio: Option[String] = None, contribution: Option[String] = None, date: Option[String] = None)
  /** GetResult implicit for fetching ShareholderInformationRow objects using plain SQL queries */
  implicit def GetResultShareholderInformationRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]]): GR[ShareholderInformationRow] = GR{
    prs => import prs._
    ShareholderInformationRow.tupled((<<[Int], <<?[String], <<?[String], <<?[Int], <<?[String], <<?[String], <<?[String]))
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
    /** Database column contribution SqlType(VARCHAR), Length(255,true), Default(None) */
    val contribution: Rep[Option[String]] = column[Option[String]]("contribution", O.Length(255,varying=true), O.Default(None))
    /** Database column date SqlType(VARCHAR), Length(255,true), Default(None) */
    val date: Rep[Option[String]] = column[Option[String]]("date", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table ShareholderInformation */
  lazy val ShareholderInformation = new TableQuery(tag => new ShareholderInformation(tag))

  /** Entity class storing rows of table ShortInfo
   *  @param key Database column key SqlType(VARCHAR), Length(255,true)
   *  @param info Database column info SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
  case class ShortInfoRow(key: String, info: Option[String] = None)
  /** GetResult implicit for fetching ShortInfoRow objects using plain SQL queries */
  implicit def GetResultShortInfoRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[ShortInfoRow] = GR{
    prs => import prs._
    ShortInfoRow.tupled((<<[String], <<?[String]))
  }
  /** Table description of table short_info. Objects of this class serve as prototypes for rows in queries. */
  class ShortInfo(_tableTag: Tag) extends profile.api.Table[ShortInfoRow](_tableTag, Some("data"), "short_info") {
    def * = (key, info) <> (ShortInfoRow.tupled, ShortInfoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(key), info).shaped.<>({r=>import r._; _1.map(_=> ShortInfoRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column key SqlType(VARCHAR), Length(255,true) */
    val key: Rep[String] = column[String]("key", O.Length(255,varying=true))
    /** Database column info SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
    val info: Rep[Option[String]] = column[Option[String]]("info", O.Length(2147483647,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table ShortInfo */
  lazy val ShortInfo = new TableQuery(tag => new ShortInfo(tag))

  /** Entity class storing rows of table TaxCredit
   *  @param cid Database column cid SqlType(INT)
   *  @param year Database column year SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param code Database column code SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param level Database column level SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param unit Database column unit SqlType(VARCHAR), Length(255,true), Default(None) */
  case class TaxCreditRow(cid: Int, year: Option[String] = None, code: Option[String] = None, level: Option[String] = None, unit: Option[String] = None)
  /** GetResult implicit for fetching TaxCreditRow objects using plain SQL queries */
  implicit def GetResultTaxCreditRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[TaxCreditRow] = GR{
    prs => import prs._
    TaxCreditRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table tax_credit. Objects of this class serve as prototypes for rows in queries. */
  class TaxCredit(_tableTag: Tag) extends profile.api.Table[TaxCreditRow](_tableTag, Some("data"), "tax_credit") {
    def * = (cid, year, code, level, unit) <> (TaxCreditRow.tupled, TaxCreditRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), year, code, level, unit).shaped.<>({r=>import r._; _1.map(_=> TaxCreditRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column year SqlType(VARCHAR), Length(255,true), Default(None) */
    val year: Rep[Option[String]] = column[Option[String]]("year", O.Length(255,varying=true), O.Default(None))
    /** Database column code SqlType(VARCHAR), Length(255,true), Default(None) */
    val code: Rep[Option[String]] = column[Option[String]]("code", O.Length(255,varying=true), O.Default(None))
    /** Database column level SqlType(VARCHAR), Length(255,true), Default(None) */
    val level: Rep[Option[String]] = column[Option[String]]("level", O.Length(255,varying=true), O.Default(None))
    /** Database column unit SqlType(VARCHAR), Length(255,true), Default(None) */
    val unit: Rep[Option[String]] = column[Option[String]]("unit", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table TaxCredit */
  lazy val TaxCredit = new TableQuery(tag => new TaxCredit(tag))

  /** Entity class storing rows of table User
   *  @param id Database column id SqlType(INT)
   *  @param username Database column username SqlType(VARCHAR), Length(255,true)
   *  @param password Database column password SqlType(VARCHAR), Length(255,true)
   *  @param nickname Database column nickname SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param mail Database column mail SqlType(VARCHAR), Length(255,true) */
  case class UserRow(id: Int, username: String, password: String, nickname: Option[String] = None, mail: String)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Int], <<[String], <<[String], <<?[String], <<[String]))
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends profile.api.Table[UserRow](_tableTag, Some("data"), "user") {
    def * = (id, username, password, nickname, mail) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(username), Rep.Some(password), nickname, Rep.Some(mail)).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3.get, _4, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column username SqlType(VARCHAR), Length(255,true) */
    val username: Rep[String] = column[String]("username", O.Length(255,varying=true))
    /** Database column password SqlType(VARCHAR), Length(255,true) */
    val password: Rep[String] = column[String]("password", O.Length(255,varying=true))
    /** Database column nickname SqlType(VARCHAR), Length(255,true), Default(None) */
    val nickname: Rep[Option[String]] = column[Option[String]]("nickname", O.Length(255,varying=true), O.Default(None))
    /** Database column mail SqlType(VARCHAR), Length(255,true) */
    val mail: Rep[String] = column[String]("mail", O.Length(255,varying=true))
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))
}
