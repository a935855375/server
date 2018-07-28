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
  lazy val schema: profile.SchemaDescription = Array(AssociationGraph.schema, AssociationInfo.schema, BasicInfo.schema, Boss.schema, Branch.schema, ChangeRecord.schema, Character.schema, Company.schema, CompanyProfile.schema, EnterpriseOne.schema, EnterpriseThree.schema, EnterpriseTwo.schema, InterestedPeople.schema, InvestmentBoss.schema, InvestmentGraph.schema, InvestmentInfo.schema, MainPersonnel.schema, OutboundInvestment.schema, Relationship.schema, ShareholderInformation.schema, ShortInfo.schema, Temp.schema, User.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl: profile.DDL = schema

  /** Entity class storing rows of table AssociationGraph
   *  @param id Database column id SqlType(INT)
   *  @param bid Database column bid SqlType(INT)
   *  @param value Database column value SqlType(VARCHAR), Length(255,true), Default(None) */
  case class AssociationGraphRow(id: Int, bid: Int, value: Option[String] = None)
  /** GetResult implicit for fetching AssociationGraphRow objects using plain SQL queries */
  implicit def GetResultAssociationGraphRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[AssociationGraphRow] = GR{
    prs => import prs._
    AssociationGraphRow.tupled((<<[Int], <<[Int], <<?[String]))
  }
  /** Table description of table association_graph. Objects of this class serve as prototypes for rows in queries. */
  class AssociationGraph(_tableTag: Tag) extends profile.api.Table[AssociationGraphRow](_tableTag, Some("server"), "association_graph") {
    def * = (id, bid, value) <> (AssociationGraphRow.tupled, AssociationGraphRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(bid), value).shaped.<>({r=>import r._; _1.map(_=> AssociationGraphRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column bid SqlType(INT) */
    val bid: Rep[Int] = column[Int]("bid")
    /** Database column value SqlType(VARCHAR), Length(255,true), Default(None) */
    val value: Rep[Option[String]] = column[Option[String]]("value", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table AssociationGraph */
  lazy val AssociationGraph = new TableQuery(tag => new AssociationGraph(tag))

  /** Entity class storing rows of table AssociationInfo
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param kind Database column kind SqlType(INT), Default(None)
   *  @param realId Database column real_id SqlType(INT), Default(None) */
  case class AssociationInfoRow(id: Int, name: Option[String] = None, kind: Option[Int] = None, realId: Option[Int] = None)
  /** GetResult implicit for fetching AssociationInfoRow objects using plain SQL queries */
  implicit def GetResultAssociationInfoRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]]): GR[AssociationInfoRow] = GR{
    prs => import prs._
    AssociationInfoRow.tupled((<<[Int], <<?[String], <<?[Int], <<?[Int]))
  }
  /** Table description of table association_info. Objects of this class serve as prototypes for rows in queries. */
  class AssociationInfo(_tableTag: Tag) extends profile.api.Table[AssociationInfoRow](_tableTag, Some("server"), "association_info") {
    def * = (id, name, kind, realId) <> (AssociationInfoRow.tupled, AssociationInfoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), name, kind, realId).shaped.<>({r=>import r._; _1.map(_=> AssociationInfoRow.tupled((_1.get, _2, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column kind SqlType(INT), Default(None) */
    val kind: Rep[Option[Int]] = column[Option[Int]]("kind", O.Default(None))
    /** Database column real_id SqlType(INT), Default(None) */
    val realId: Rep[Option[Int]] = column[Option[Int]]("real_id", O.Default(None))
  }
  /** Collection-like TableQuery object for table AssociationInfo */
  lazy val AssociationInfo = new TableQuery(tag => new AssociationInfo(tag))

  /** Entity class storing rows of table BasicInfo
   *  @param cid Database column cid SqlType(INT)
   *  @param openStatus Database column open_status SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param paidCapital Database column paid_capital SqlType(INT), Default(None)
   *  @param socialCreditCode Database column social_credit_code SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param taxpayerIdentificationNumber Database column taxpayer_identification_number SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param registrationNumber Database column registration_number SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param organizationCode Database column organization_code SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param typeOfCompany Database column type_of_company SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param industry Database column industry SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param dateOfApproval Database column date_of_approval SqlType(DATE), Default(None)
   *  @param registrationAuthority Database column registration_authority SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param region Database column region SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param englishName Database column english_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param nameUsedBefore Database column name_used_before SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param operationMode Database column operation_mode SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param personnelScale Database column personnel_scale SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param timeLimitForBusiness Database column time_limit_for_business SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param enterpriseAddress Database column enterprise_address SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param scopeOfOperation Database column scope_of_operation SqlType(TEXT), Default(None) */
  case class BasicInfoRow(cid: Int, openStatus: Option[String] = None, paidCapital: Option[Int] = None, socialCreditCode: Option[String] = None, taxpayerIdentificationNumber: Option[String] = None, registrationNumber: Option[String] = None, organizationCode: Option[String] = None, typeOfCompany: Option[String] = None, industry: Option[String] = None, dateOfApproval: Option[java.sql.Date] = None, registrationAuthority: Option[String] = None, region: Option[String] = None, englishName: Option[String] = None, nameUsedBefore: Option[String] = None, operationMode: Option[String] = None, personnelScale: Option[String] = None, timeLimitForBusiness: Option[String] = None, enterpriseAddress: Option[String] = None, scopeOfOperation: Option[String] = None)
  /** GetResult implicit for fetching BasicInfoRow objects using plain SQL queries */
  implicit def GetResultBasicInfoRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[java.sql.Date]]): GR[BasicInfoRow] = GR{
    prs => import prs._
    BasicInfoRow.tupled((<<[Int], <<?[String], <<?[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[java.sql.Date], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table basic_info. Objects of this class serve as prototypes for rows in queries. */
  class BasicInfo(_tableTag: Tag) extends profile.api.Table[BasicInfoRow](_tableTag, Some("server"), "basic_info") {
    def * = (cid, openStatus, paidCapital, socialCreditCode, taxpayerIdentificationNumber, registrationNumber, organizationCode, typeOfCompany, industry, dateOfApproval, registrationAuthority, region, englishName, nameUsedBefore, operationMode, personnelScale, timeLimitForBusiness, enterpriseAddress, scopeOfOperation) <> (BasicInfoRow.tupled, BasicInfoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cid), openStatus, paidCapital, socialCreditCode, taxpayerIdentificationNumber, registrationNumber, organizationCode, typeOfCompany, industry, dateOfApproval, registrationAuthority, region, englishName, nameUsedBefore, operationMode, personnelScale, timeLimitForBusiness, enterpriseAddress, scopeOfOperation).shaped.<>({r=>import r._; _1.map(_=> BasicInfoRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17, _18, _19)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cid SqlType(INT) */
    val cid: Rep[Int] = column[Int]("cid")
    /** Database column open_status SqlType(VARCHAR), Length(255,true), Default(None) */
    val openStatus: Rep[Option[String]] = column[Option[String]]("open_status", O.Length(255,varying=true), O.Default(None))
    /** Database column paid_capital SqlType(INT), Default(None) */
    val paidCapital: Rep[Option[Int]] = column[Option[Int]]("paid_capital", O.Default(None))
    /** Database column social_credit_code SqlType(VARCHAR), Length(255,true), Default(None) */
    val socialCreditCode: Rep[Option[String]] = column[Option[String]]("social_credit_code", O.Length(255,varying=true), O.Default(None))
    /** Database column taxpayer_identification_number SqlType(VARCHAR), Length(255,true), Default(None) */
    val taxpayerIdentificationNumber: Rep[Option[String]] = column[Option[String]]("taxpayer_identification_number", O.Length(255,varying=true), O.Default(None))
    /** Database column registration_number SqlType(VARCHAR), Length(255,true), Default(None) */
    val registrationNumber: Rep[Option[String]] = column[Option[String]]("registration_number", O.Length(255,varying=true), O.Default(None))
    /** Database column organization_code SqlType(VARCHAR), Length(255,true), Default(None) */
    val organizationCode: Rep[Option[String]] = column[Option[String]]("organization_code", O.Length(255,varying=true), O.Default(None))
    /** Database column type_of_company SqlType(VARCHAR), Length(255,true), Default(None) */
    val typeOfCompany: Rep[Option[String]] = column[Option[String]]("type_of_company", O.Length(255,varying=true), O.Default(None))
    /** Database column industry SqlType(VARCHAR), Length(255,true), Default(None) */
    val industry: Rep[Option[String]] = column[Option[String]]("industry", O.Length(255,varying=true), O.Default(None))
    /** Database column date_of_approval SqlType(DATE), Default(None) */
    val dateOfApproval: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("date_of_approval", O.Default(None))
    /** Database column registration_authority SqlType(VARCHAR), Length(255,true), Default(None) */
    val registrationAuthority: Rep[Option[String]] = column[Option[String]]("registration_authority", O.Length(255,varying=true), O.Default(None))
    /** Database column region SqlType(VARCHAR), Length(255,true), Default(None) */
    val region: Rep[Option[String]] = column[Option[String]]("region", O.Length(255,varying=true), O.Default(None))
    /** Database column english_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val englishName: Rep[Option[String]] = column[Option[String]]("english_name", O.Length(255,varying=true), O.Default(None))
    /** Database column name_used_before SqlType(VARCHAR), Length(255,true), Default(None) */
    val nameUsedBefore: Rep[Option[String]] = column[Option[String]]("name_used_before", O.Length(255,varying=true), O.Default(None))
    /** Database column operation_mode SqlType(VARCHAR), Length(255,true), Default(None) */
    val operationMode: Rep[Option[String]] = column[Option[String]]("operation_mode", O.Length(255,varying=true), O.Default(None))
    /** Database column personnel_scale SqlType(VARCHAR), Length(255,true), Default(None) */
    val personnelScale: Rep[Option[String]] = column[Option[String]]("personnel_scale", O.Length(255,varying=true), O.Default(None))
    /** Database column time_limit_for_business SqlType(VARCHAR), Length(255,true), Default(None) */
    val timeLimitForBusiness: Rep[Option[String]] = column[Option[String]]("time_limit_for_business", O.Length(255,varying=true), O.Default(None))
    /** Database column enterprise_address SqlType(VARCHAR), Length(255,true), Default(None) */
    val enterpriseAddress: Rep[Option[String]] = column[Option[String]]("enterprise_address", O.Length(255,varying=true), O.Default(None))
    /** Database column scope_of_operation SqlType(TEXT), Default(None) */
    val scopeOfOperation: Rep[Option[String]] = column[Option[String]]("scope_of_operation", O.Default(None))

    /** Uniqueness Index over (cid) (database name basic_info_cid_uindex) */
    val index1 = index("basic_info_cid_uindex", cid, unique=true)
  }
  /** Collection-like TableQuery object for table BasicInfo */
  lazy val BasicInfo = new TableQuery(tag => new BasicInfo(tag))

  /** Entity class storing rows of table Boss
   *  @param id Database column id SqlType(INT)
   *  @param bid Database column bid SqlType(INT) */
  case class BossRow(id: Int, bid: Int)
  /** GetResult implicit for fetching BossRow objects using plain SQL queries */
  implicit def GetResultBossRow(implicit e0: GR[Int]): GR[BossRow] = GR{
    prs => import prs._
    BossRow.tupled((<<[Int], <<[Int]))
  }
  /** Table description of table boss. Objects of this class serve as prototypes for rows in queries. */
  class Boss(_tableTag: Tag) extends profile.api.Table[BossRow](_tableTag, Some("server"), "boss") {
    def * = (id, bid) <> (BossRow.tupled, BossRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(bid)).shaped.<>({r=>import r._; _1.map(_=> BossRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column bid SqlType(INT) */
    val bid: Rep[Int] = column[Int]("bid")
  }
  /** Collection-like TableQuery object for table Boss */
  lazy val Boss = new TableQuery(tag => new Boss(tag))

  /** Entity class storing rows of table Branch
   *  @param id Database column id SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true) */
  case class BranchRow(id: Int, name: String)
  /** GetResult implicit for fetching BranchRow objects using plain SQL queries */
  implicit def GetResultBranchRow(implicit e0: GR[Int], e1: GR[String]): GR[BranchRow] = GR{
    prs => import prs._
    BranchRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table branch. Objects of this class serve as prototypes for rows in queries. */
  class Branch(_tableTag: Tag) extends profile.api.Table[BranchRow](_tableTag, Some("server"), "branch") {
    def * = (id, name) <> (BranchRow.tupled, BranchRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name)).shaped.<>({r=>import r._; _1.map(_=> BranchRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
  }
  /** Collection-like TableQuery object for table Branch */
  lazy val Branch = new TableQuery(tag => new Branch(tag))

  /** Entity class storing rows of table ChangeRecord
   *  @param id Database column id SqlType(INT)
   *  @param changeDate Database column change_date SqlType(DATE), Default(None)
   *  @param changeProject Database column change_project SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param beforeChange Database column before_change SqlType(TEXT), Default(None)
   *  @param afterChange Database column after_change SqlType(TEXT), Default(None) */
  case class ChangeRecordRow(id: Int, changeDate: Option[java.sql.Date] = None, changeProject: Option[String] = None, beforeChange: Option[String] = None, afterChange: Option[String] = None)
  /** GetResult implicit for fetching ChangeRecordRow objects using plain SQL queries */
  implicit def GetResultChangeRecordRow(implicit e0: GR[Int], e1: GR[Option[java.sql.Date]], e2: GR[Option[String]]): GR[ChangeRecordRow] = GR{
    prs => import prs._
    ChangeRecordRow.tupled((<<[Int], <<?[java.sql.Date], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table change_record. Objects of this class serve as prototypes for rows in queries. */
  class ChangeRecord(_tableTag: Tag) extends profile.api.Table[ChangeRecordRow](_tableTag, Some("server"), "change_record") {
    def * = (id, changeDate, changeProject, beforeChange, afterChange) <> (ChangeRecordRow.tupled, ChangeRecordRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), changeDate, changeProject, beforeChange, afterChange).shaped.<>({r=>import r._; _1.map(_=> ChangeRecordRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column change_date SqlType(DATE), Default(None) */
    val changeDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("change_date", O.Default(None))
    /** Database column change_project SqlType(VARCHAR), Length(255,true), Default(None) */
    val changeProject: Rep[Option[String]] = column[Option[String]]("change_project", O.Length(255,varying=true), O.Default(None))
    /** Database column before_change SqlType(TEXT), Default(None) */
    val beforeChange: Rep[Option[String]] = column[Option[String]]("before_change", O.Default(None))
    /** Database column after_change SqlType(TEXT), Default(None) */
    val afterChange: Rep[Option[String]] = column[Option[String]]("after_change", O.Default(None))
  }
  /** Collection-like TableQuery object for table ChangeRecord */
  lazy val ChangeRecord = new TableQuery(tag => new ChangeRecord(tag))

  /** Entity class storing rows of table Character
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param associateCount Database column associate_count SqlType(INT), Default(None)
   *  @param avator Database column avator SqlType(VARCHAR), Length(255,true), Default(None) */
  case class CharacterRow(id: Int, name: String, associateCount: Option[Int] = None, avator: Option[String] = None)
  /** GetResult implicit for fetching CharacterRow objects using plain SQL queries */
  implicit def GetResultCharacterRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Int]], e3: GR[Option[String]]): GR[CharacterRow] = GR{
    prs => import prs._
    CharacterRow.tupled((<<[Int], <<[String], <<?[Int], <<?[String]))
  }
  /** Table description of table character. Objects of this class serve as prototypes for rows in queries. */
  class Character(_tableTag: Tag) extends profile.api.Table[CharacterRow](_tableTag, Some("server"), "character") {
    def * = (id, name, associateCount, avator) <> (CharacterRow.tupled, CharacterRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), associateCount, avator).shaped.<>({r=>import r._; _1.map(_=> CharacterRow.tupled((_1.get, _2.get, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column associate_count SqlType(INT), Default(None) */
    val associateCount: Rep[Option[Int]] = column[Option[Int]]("associate_count", O.Default(None))
    /** Database column avator SqlType(VARCHAR), Length(255,true), Default(None) */
    val avator: Rep[Option[String]] = column[Option[String]]("avator", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Character */
  lazy val Character = new TableQuery(tag => new Character(tag))

  /** Entity class storing rows of table Company
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param status Database column status SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param represent Database column represent SqlType(VARCHAR), Length(255,true)
   *  @param capital Database column capital SqlType(DOUBLE), Default(None)
   *  @param foundTime Database column found_time SqlType(DATE), Default(None)
   *  @param mail Database column mail SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param phone Database column phone SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param addr Database column addr SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param website Database column website SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param introduction Database column introduction SqlType(TEXT), Default(None)
   *  @param img Database column img SqlType(VARCHAR), Length(255,true), Default(None) */
  case class CompanyRow(id: Int, name: String, status: Option[String] = None, represent: String, capital: Option[Double] = None, foundTime: Option[java.sql.Date] = None, mail: Option[String] = None, phone: Option[String] = None, addr: Option[String] = None, website: Option[String] = None, introduction: Option[String] = None, img: Option[String] = None)
  /** GetResult implicit for fetching CompanyRow objects using plain SQL queries */
  implicit def GetResultCompanyRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[Double]], e4: GR[Option[java.sql.Date]]): GR[CompanyRow] = GR{
    prs => import prs._
    CompanyRow.tupled((<<[Int], <<[String], <<?[String], <<[String], <<?[Double], <<?[java.sql.Date], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table company. Objects of this class serve as prototypes for rows in queries. */
  class Company(_tableTag: Tag) extends profile.api.Table[CompanyRow](_tableTag, Some("server"), "company") {
    def * = (id, name, status, represent, capital, foundTime, mail, phone, addr, website, introduction, img) <> (CompanyRow.tupled, CompanyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), status, Rep.Some(represent), capital, foundTime, mail, phone, addr, website, introduction, img).shaped.<>({r=>import r._; _1.map(_=> CompanyRow.tupled((_1.get, _2.get, _3, _4.get, _5, _6, _7, _8, _9, _10, _11, _12)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
    val status: Rep[Option[String]] = column[Option[String]]("status", O.Length(255,varying=true), O.Default(None))
    /** Database column represent SqlType(VARCHAR), Length(255,true) */
    val represent: Rep[String] = column[String]("represent", O.Length(255,varying=true))
    /** Database column capital SqlType(DOUBLE), Default(None) */
    val capital: Rep[Option[Double]] = column[Option[Double]]("capital", O.Default(None))
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
  }
  /** Collection-like TableQuery object for table Company */
  lazy val Company = new TableQuery(tag => new Company(tag))

  /** Entity class storing rows of table CompanyProfile
   *  @param id Database column id SqlType(INT)
   *  @param profile Database column profile SqlType(TEXT), Default(None) */
  case class CompanyProfileRow(id: Int, profile: Option[String] = None)
  /** GetResult implicit for fetching CompanyProfileRow objects using plain SQL queries */
  implicit def GetResultCompanyProfileRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[CompanyProfileRow] = GR{
    prs => import prs._
    CompanyProfileRow.tupled((<<[Int], <<?[String]))
  }
  /** Table description of table company_profile. Objects of this class serve as prototypes for rows in queries. */
  class CompanyProfile(_tableTag: Tag) extends profile.api.Table[CompanyProfileRow](_tableTag, Some("server"), "company_profile") {
    def * = (id, profile) <> (CompanyProfileRow.tupled, CompanyProfileRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), profile).shaped.<>({r=>import r._; _1.map(_=> CompanyProfileRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column profile SqlType(TEXT), Default(None) */
    val profile: Rep[Option[String]] = column[Option[String]]("profile", O.Default(None))
  }
  /** Collection-like TableQuery object for table CompanyProfile */
  lazy val CompanyProfile = new TableQuery(tag => new CompanyProfile(tag))

  /** Entity class storing rows of table EnterpriseOne
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param oneName Database column one_name SqlType(VARCHAR), Length(255,true) */
  case class EnterpriseOneRow(id: Int, oneName: String)
  /** GetResult implicit for fetching EnterpriseOneRow objects using plain SQL queries */
  implicit def GetResultEnterpriseOneRow(implicit e0: GR[Int], e1: GR[String]): GR[EnterpriseOneRow] = GR{
    prs => import prs._
    EnterpriseOneRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table enterprise_one. Objects of this class serve as prototypes for rows in queries. */
  class EnterpriseOne(_tableTag: Tag) extends profile.api.Table[EnterpriseOneRow](_tableTag, Some("server"), "enterprise_one") {
    def * = (id, oneName) <> (EnterpriseOneRow.tupled, EnterpriseOneRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(oneName)).shaped.<>({r=>import r._; _1.map(_=> EnterpriseOneRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column one_name SqlType(VARCHAR), Length(255,true) */
    val oneName: Rep[String] = column[String]("one_name", O.Length(255,varying=true))
  }
  /** Collection-like TableQuery object for table EnterpriseOne */
  lazy val EnterpriseOne = new TableQuery(tag => new EnterpriseOne(tag))

  /** Entity class storing rows of table EnterpriseThree
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param threeName Database column three_name SqlType(VARCHAR), Length(255,true)
   *  @param two Database column two SqlType(INT) */
  case class EnterpriseThreeRow(id: Int, threeName: String, two: Int)
  /** GetResult implicit for fetching EnterpriseThreeRow objects using plain SQL queries */
  implicit def GetResultEnterpriseThreeRow(implicit e0: GR[Int], e1: GR[String]): GR[EnterpriseThreeRow] = GR{
    prs => import prs._
    EnterpriseThreeRow.tupled((<<[Int], <<[String], <<[Int]))
  }
  /** Table description of table enterprise_three. Objects of this class serve as prototypes for rows in queries. */
  class EnterpriseThree(_tableTag: Tag) extends profile.api.Table[EnterpriseThreeRow](_tableTag, Some("server"), "enterprise_three") {
    def * = (id, threeName, two) <> (EnterpriseThreeRow.tupled, EnterpriseThreeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(threeName), Rep.Some(two)).shaped.<>({r=>import r._; _1.map(_=> EnterpriseThreeRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column three_name SqlType(VARCHAR), Length(255,true) */
    val threeName: Rep[String] = column[String]("three_name", O.Length(255,varying=true))
    /** Database column two SqlType(INT) */
    val two: Rep[Int] = column[Int]("two")
  }
  /** Collection-like TableQuery object for table EnterpriseThree */
  lazy val EnterpriseThree = new TableQuery(tag => new EnterpriseThree(tag))

  /** Entity class storing rows of table EnterpriseTwo
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param twoName Database column two_name SqlType(VARCHAR), Length(255,true)
   *  @param one Database column one SqlType(INT) */
  case class EnterpriseTwoRow(id: Int, twoName: String, one: Int)
  /** GetResult implicit for fetching EnterpriseTwoRow objects using plain SQL queries */
  implicit def GetResultEnterpriseTwoRow(implicit e0: GR[Int], e1: GR[String]): GR[EnterpriseTwoRow] = GR{
    prs => import prs._
    EnterpriseTwoRow.tupled((<<[Int], <<[String], <<[Int]))
  }
  /** Table description of table enterprise_two. Objects of this class serve as prototypes for rows in queries. */
  class EnterpriseTwo(_tableTag: Tag) extends profile.api.Table[EnterpriseTwoRow](_tableTag, Some("server"), "enterprise_two") {
    def * = (id, twoName, one) <> (EnterpriseTwoRow.tupled, EnterpriseTwoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(twoName), Rep.Some(one)).shaped.<>({r=>import r._; _1.map(_=> EnterpriseTwoRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column two_name SqlType(VARCHAR), Length(255,true) */
    val twoName: Rep[String] = column[String]("two_name", O.Length(255,varying=true))
    /** Database column one SqlType(INT) */
    val one: Rep[Int] = column[Int]("one")
  }
  /** Collection-like TableQuery object for table EnterpriseTwo */
  lazy val EnterpriseTwo = new TableQuery(tag => new EnterpriseTwo(tag))

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
  class InterestedPeople(_tableTag: Tag) extends profile.api.Table[InterestedPeopleRow](_tableTag, Some("server"), "interested_people") {
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

  /** Entity class storing rows of table InvestmentBoss
   *  @param id Database column id SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param value Database column value SqlType(DOUBLE), Default(None) */
  case class InvestmentBossRow(id: Int, name: Option[String] = None, value: Option[Double] = None)
  /** GetResult implicit for fetching InvestmentBossRow objects using plain SQL queries */
  implicit def GetResultInvestmentBossRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Double]]): GR[InvestmentBossRow] = GR{
    prs => import prs._
    InvestmentBossRow.tupled((<<[Int], <<?[String], <<?[Double]))
  }
  /** Table description of table investment_boss. Objects of this class serve as prototypes for rows in queries. */
  class InvestmentBoss(_tableTag: Tag) extends profile.api.Table[InvestmentBossRow](_tableTag, Some("server"), "investment_boss") {
    def * = (id, name, value) <> (InvestmentBossRow.tupled, InvestmentBossRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), name, value).shaped.<>({r=>import r._; _1.map(_=> InvestmentBossRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column value SqlType(DOUBLE), Default(None) */
    val value: Rep[Option[Double]] = column[Option[Double]]("value", O.Default(None))
  }
  /** Collection-like TableQuery object for table InvestmentBoss */
  lazy val InvestmentBoss = new TableQuery(tag => new InvestmentBoss(tag))

  /** Entity class storing rows of table InvestmentGraph
   *  @param id Database column id SqlType(INT)
   *  @param bid Database column bid SqlType(INT)
   *  @param value Database column value SqlType(DOUBLE), Default(None) */
  case class InvestmentGraphRow(id: Int, bid: Int, value: Option[Double] = None)
  /** GetResult implicit for fetching InvestmentGraphRow objects using plain SQL queries */
  implicit def GetResultInvestmentGraphRow(implicit e0: GR[Int], e1: GR[Option[Double]]): GR[InvestmentGraphRow] = GR{
    prs => import prs._
    InvestmentGraphRow.tupled((<<[Int], <<[Int], <<?[Double]))
  }
  /** Table description of table investment_graph. Objects of this class serve as prototypes for rows in queries. */
  class InvestmentGraph(_tableTag: Tag) extends profile.api.Table[InvestmentGraphRow](_tableTag, Some("server"), "investment_graph") {
    def * = (id, bid, value) <> (InvestmentGraphRow.tupled, InvestmentGraphRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(bid), value).shaped.<>({r=>import r._; _1.map(_=> InvestmentGraphRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column bid SqlType(INT) */
    val bid: Rep[Int] = column[Int]("bid")
    /** Database column value SqlType(DOUBLE), Default(None) */
    val value: Rep[Option[Double]] = column[Option[Double]]("value", O.Default(None))
  }
  /** Collection-like TableQuery object for table InvestmentGraph */
  lazy val InvestmentGraph = new TableQuery(tag => new InvestmentGraph(tag))

  /** Entity class storing rows of table InvestmentInfo
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param realId Database column real_id SqlType(INT), Default(None) */
  case class InvestmentInfoRow(id: Int, name: Option[String] = None, realId: Option[Int] = None)
  /** GetResult implicit for fetching InvestmentInfoRow objects using plain SQL queries */
  implicit def GetResultInvestmentInfoRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]]): GR[InvestmentInfoRow] = GR{
    prs => import prs._
    InvestmentInfoRow.tupled((<<[Int], <<?[String], <<?[Int]))
  }
  /** Table description of table investment_info. Objects of this class serve as prototypes for rows in queries. */
  class InvestmentInfo(_tableTag: Tag) extends profile.api.Table[InvestmentInfoRow](_tableTag, Some("server"), "investment_info") {
    def * = (id, name, realId) <> (InvestmentInfoRow.tupled, InvestmentInfoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), name, realId).shaped.<>({r=>import r._; _1.map(_=> InvestmentInfoRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column real_id SqlType(INT), Default(None) */
    val realId: Rep[Option[Int]] = column[Option[Int]]("real_id", O.Default(None))
  }
  /** Collection-like TableQuery object for table InvestmentInfo */
  lazy val InvestmentInfo = new TableQuery(tag => new InvestmentInfo(tag))

  /** Entity class storing rows of table MainPersonnel
   *  @param id Database column id SqlType(INT)
   *  @param cid Database column cid SqlType(INT), Default(None)
   *  @param desc Database column desc SqlType(TEXT), Default(None) */
  case class MainPersonnelRow(id: Int, cid: Option[Int] = None, desc: Option[String] = None)
  /** GetResult implicit for fetching MainPersonnelRow objects using plain SQL queries */
  implicit def GetResultMainPersonnelRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]]): GR[MainPersonnelRow] = GR{
    prs => import prs._
    MainPersonnelRow.tupled((<<[Int], <<?[Int], <<?[String]))
  }
  /** Table description of table main_personnel. Objects of this class serve as prototypes for rows in queries. */
  class MainPersonnel(_tableTag: Tag) extends profile.api.Table[MainPersonnelRow](_tableTag, Some("server"), "main_personnel") {
    def * = (id, cid, desc) <> (MainPersonnelRow.tupled, MainPersonnelRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), cid, desc).shaped.<>({r=>import r._; _1.map(_=> MainPersonnelRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column cid SqlType(INT), Default(None) */
    val cid: Rep[Option[Int]] = column[Option[Int]]("cid", O.Default(None))
    /** Database column desc SqlType(TEXT), Default(None) */
    val desc: Rep[Option[String]] = column[Option[String]]("desc", O.Default(None))
  }
  /** Collection-like TableQuery object for table MainPersonnel */
  lazy val MainPersonnel = new TableQuery(tag => new MainPersonnel(tag))

  /** Entity class storing rows of table OutboundInvestment
   *  @param id Database column id SqlType(INT)
   *  @param investedEnterprise Database column invested_enterprise SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param investmentRepresentative Database column investment_representative SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param registeredCapital Database column registered_capital SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param contribution Database column contribution SqlType(DOUBLE), Default(None)
   *  @param dateOfEstablishment Database column date_of_establishment SqlType(DATE), Default(None)
   *  @param status Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
  case class OutboundInvestmentRow(id: Int, investedEnterprise: Option[String] = None, investmentRepresentative: Option[String] = None, registeredCapital: Option[String] = None, contribution: Option[Double] = None, dateOfEstablishment: Option[java.sql.Date] = None, status: Option[String] = None)
  /** GetResult implicit for fetching OutboundInvestmentRow objects using plain SQL queries */
  implicit def GetResultOutboundInvestmentRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Double]], e3: GR[Option[java.sql.Date]]): GR[OutboundInvestmentRow] = GR{
    prs => import prs._
    OutboundInvestmentRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[Double], <<?[java.sql.Date], <<?[String]))
  }
  /** Table description of table outbound_investment. Objects of this class serve as prototypes for rows in queries. */
  class OutboundInvestment(_tableTag: Tag) extends profile.api.Table[OutboundInvestmentRow](_tableTag, Some("server"), "outbound_investment") {
    def * = (id, investedEnterprise, investmentRepresentative, registeredCapital, contribution, dateOfEstablishment, status) <> (OutboundInvestmentRow.tupled, OutboundInvestmentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), investedEnterprise, investmentRepresentative, registeredCapital, contribution, dateOfEstablishment, status).shaped.<>({r=>import r._; _1.map(_=> OutboundInvestmentRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column invested_enterprise SqlType(VARCHAR), Length(255,true), Default(None) */
    val investedEnterprise: Rep[Option[String]] = column[Option[String]]("invested_enterprise", O.Length(255,varying=true), O.Default(None))
    /** Database column investment_representative SqlType(VARCHAR), Length(255,true), Default(None) */
    val investmentRepresentative: Rep[Option[String]] = column[Option[String]]("investment_representative", O.Length(255,varying=true), O.Default(None))
    /** Database column registered_capital SqlType(VARCHAR), Length(255,true), Default(None) */
    val registeredCapital: Rep[Option[String]] = column[Option[String]]("registered_capital", O.Length(255,varying=true), O.Default(None))
    /** Database column contribution SqlType(DOUBLE), Default(None) */
    val contribution: Rep[Option[Double]] = column[Option[Double]]("contribution", O.Default(None))
    /** Database column date_of_establishment SqlType(DATE), Default(None) */
    val dateOfEstablishment: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("date_of_establishment", O.Default(None))
    /** Database column status SqlType(VARCHAR), Length(255,true), Default(None) */
    val status: Rep[Option[String]] = column[Option[String]]("status", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table OutboundInvestment */
  lazy val OutboundInvestment = new TableQuery(tag => new OutboundInvestment(tag))

  /** Entity class storing rows of table Relationship
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey */
  case class RelationshipRow(id: Int)
  /** GetResult implicit for fetching RelationshipRow objects using plain SQL queries */
  implicit def GetResultRelationshipRow(implicit e0: GR[Int]): GR[RelationshipRow] = GR{
    prs => import prs._
    RelationshipRow(<<[Int])
  }
  /** Table description of table relationship. Objects of this class serve as prototypes for rows in queries. */
  class Relationship(_tableTag: Tag) extends profile.api.Table[RelationshipRow](_tableTag, Some("server"), "relationship") {
    def * = id <> (RelationshipRow, RelationshipRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = Rep.Some(id).shaped.<>(r => r.map(_=> RelationshipRow(r.get)), (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Relationship */
  lazy val Relationship = new TableQuery(tag => new Relationship(tag))

  /** Entity class storing rows of table ShareholderInformation
   *  @param id Database column id SqlType(INT)
   *  @param shareholder Database column shareholder SqlType(INT)
   *  @param shareholdingRatio Database column shareholding_ratio SqlType(DOUBLE), Default(None)
   *  @param subscribedCapitalContribution Database column subscribed_capital_contribution SqlType(DOUBLE), Default(None)
   *  @param dateOfSubscription Database column date_of_subscription SqlType(DATE), Default(None)
   *  @param typeOfShareholder Database column type_of_shareholder SqlType(VARCHAR), Length(255,true), Default(None) */
  case class ShareholderInformationRow(id: Int, shareholder: Int, shareholdingRatio: Option[Double] = None, subscribedCapitalContribution: Option[Double] = None, dateOfSubscription: Option[java.sql.Date] = None, typeOfShareholder: Option[String] = None)
  /** GetResult implicit for fetching ShareholderInformationRow objects using plain SQL queries */
  implicit def GetResultShareholderInformationRow(implicit e0: GR[Int], e1: GR[Option[Double]], e2: GR[Option[java.sql.Date]], e3: GR[Option[String]]): GR[ShareholderInformationRow] = GR{
    prs => import prs._
    ShareholderInformationRow.tupled((<<[Int], <<[Int], <<?[Double], <<?[Double], <<?[java.sql.Date], <<?[String]))
  }
  /** Table description of table shareholder_information. Objects of this class serve as prototypes for rows in queries. */
  class ShareholderInformation(_tableTag: Tag) extends profile.api.Table[ShareholderInformationRow](_tableTag, Some("server"), "shareholder_information") {
    def * = (id, shareholder, shareholdingRatio, subscribedCapitalContribution, dateOfSubscription, typeOfShareholder) <> (ShareholderInformationRow.tupled, ShareholderInformationRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(shareholder), shareholdingRatio, subscribedCapitalContribution, dateOfSubscription, typeOfShareholder).shaped.<>({r=>import r._; _1.map(_=> ShareholderInformationRow.tupled((_1.get, _2.get, _3, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column shareholder SqlType(INT) */
    val shareholder: Rep[Int] = column[Int]("shareholder")
    /** Database column shareholding_ratio SqlType(DOUBLE), Default(None) */
    val shareholdingRatio: Rep[Option[Double]] = column[Option[Double]]("shareholding_ratio", O.Default(None))
    /** Database column subscribed_capital_contribution SqlType(DOUBLE), Default(None) */
    val subscribedCapitalContribution: Rep[Option[Double]] = column[Option[Double]]("subscribed_capital_contribution", O.Default(None))
    /** Database column date_of_subscription SqlType(DATE), Default(None) */
    val dateOfSubscription: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("date_of_subscription", O.Default(None))
    /** Database column type_of_shareholder SqlType(VARCHAR), Length(255,true), Default(None) */
    val typeOfShareholder: Rep[Option[String]] = column[Option[String]]("type_of_shareholder", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table ShareholderInformation */
  lazy val ShareholderInformation = new TableQuery(tag => new ShareholderInformation(tag))

  /** Entity class storing rows of table ShortInfo
   *  @param key Database column key SqlType(CHAR), Length(255,false), Default(None)
   *  @param value Database column value SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
  case class ShortInfoRow(key: Option[String] = None, value: Option[String] = None)
  /** GetResult implicit for fetching ShortInfoRow objects using plain SQL queries */
  implicit def GetResultShortInfoRow(implicit e0: GR[Option[String]]): GR[ShortInfoRow] = GR{
    prs => import prs._
    ShortInfoRow.tupled((<<?[String], <<?[String]))
  }
  /** Table description of table short_info. Objects of this class serve as prototypes for rows in queries. */
  class ShortInfo(_tableTag: Tag) extends profile.api.Table[ShortInfoRow](_tableTag, Some("server"), "short_info") {
    def * = (key, value) <> (ShortInfoRow.tupled, ShortInfoRow.unapply)

    /** Database column key SqlType(CHAR), Length(255,false), Default(None) */
    val key: Rep[Option[String]] = column[Option[String]]("key", O.Length(255,varying=false), O.Default(None))
    /** Database column value SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
    val value: Rep[Option[String]] = column[Option[String]]("value", O.Length(2147483647,varying=true), O.Default(None))

    /** Uniqueness Index over (key) (database name short_info_key_uindex) */
    val index1 = index("short_info_key_uindex", key, unique=true)
  }
  /** Collection-like TableQuery object for table ShortInfo */
  lazy val ShortInfo = new TableQuery(tag => new ShortInfo(tag))

  /** Entity class storing rows of table Temp
   *  @param id Database column id SqlType(INT), Default(None)
   *  @param kind Database column kind SqlType(INT), Default(None)
   *  @param data Database column data SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
  case class TempRow(id: Option[Int] = None, kind: Option[Int] = None, data: Option[String] = None)
  /** GetResult implicit for fetching TempRow objects using plain SQL queries */
  implicit def GetResultTempRow(implicit e0: GR[Option[Int]], e1: GR[Option[String]]): GR[TempRow] = GR{
    prs => import prs._
    TempRow.tupled((<<?[Int], <<?[Int], <<?[String]))
  }
  /** Table description of table temp. Objects of this class serve as prototypes for rows in queries. */
  class Temp(_tableTag: Tag) extends profile.api.Table[TempRow](_tableTag, Some("server"), "temp") {
    def * = (id, kind, data) <> (TempRow.tupled, TempRow.unapply)

    /** Database column id SqlType(INT), Default(None) */
    val id: Rep[Option[Int]] = column[Option[Int]]("id", O.Default(None))
    /** Database column kind SqlType(INT), Default(None) */
    val kind: Rep[Option[Int]] = column[Option[Int]]("kind", O.Default(None))
    /** Database column data SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
    val data: Rep[Option[String]] = column[Option[String]]("data", O.Length(2147483647,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Temp */
  lazy val Temp = new TableQuery(tag => new Temp(tag))

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
  class User(_tableTag: Tag) extends profile.api.Table[UserRow](_tableTag, Some("server"), "user") {
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
