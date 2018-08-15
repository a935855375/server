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
  lazy val schema: profile.SchemaDescription = Company.schema ++ Person.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

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
   *  @param ref Database column ref SqlType(VARCHAR), Length(255,true), Default(None) */
  case class CompanyRow(id: Int, name: String, status: Option[String] = None, representname: Option[String] = None, represent: Option[Int] = None, capital: Option[String] = None, foundTime: Option[java.sql.Date] = None, mail: Option[String] = None, phone: Option[String] = None, addr: Option[String] = None, website: Option[String] = None, introduction: Option[String] = None, img: Option[String] = None, ref: Option[String] = None)
  /** GetResult implicit for fetching CompanyRow objects using plain SQL queries */
  implicit def GetResultCompanyRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[Int]], e4: GR[Option[java.sql.Date]]): GR[CompanyRow] = GR{
    prs => import prs._
    CompanyRow.tupled((<<[Int], <<[String], <<?[String], <<?[String], <<?[Int], <<?[String], <<?[java.sql.Date], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table company. Objects of this class serve as prototypes for rows in queries. */
  class Company(_tableTag: Tag) extends profile.api.Table[CompanyRow](_tableTag, Some("data"), "company") {
    def * = (id, name, status, representname, represent, capital, foundTime, mail, phone, addr, website, introduction, img, ref) <> (CompanyRow.tupled, CompanyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), status, representname, represent, capital, foundTime, mail, phone, addr, website, introduction, img, ref).shaped.<>({r=>import r._; _1.map(_=> CompanyRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

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
  }
  /** Collection-like TableQuery object for table Company */
  lazy val Company = new TableQuery(tag => new Company(tag))

  /** Entity class storing rows of table Person
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param addr Database column addr SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param count Database column count SqlType(INT), Default(None) */
  case class PersonRow(id: Int, name: String, addr: Option[String] = None, count: Option[Int] = None)
  /** GetResult implicit for fetching PersonRow objects using plain SQL queries */
  implicit def GetResultPersonRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[Int]]): GR[PersonRow] = GR{
    prs => import prs._
    PersonRow.tupled((<<[Int], <<[String], <<?[String], <<?[Int]))
  }
  /** Table description of table person. Objects of this class serve as prototypes for rows in queries. */
  class Person(_tableTag: Tag) extends profile.api.Table[PersonRow](_tableTag, Some("data"), "person") {
    def * = (id, name, addr, count) <> (PersonRow.tupled, PersonRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), addr, count).shaped.<>({r=>import r._; _1.map(_=> PersonRow.tupled((_1.get, _2.get, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column addr SqlType(VARCHAR), Length(255,true), Default(None) */
    val addr: Rep[Option[String]] = column[Option[String]]("addr", O.Length(255,varying=true), O.Default(None))
    /** Database column count SqlType(INT), Default(None) */
    val count: Rep[Option[Int]] = column[Option[Int]]("count", O.Default(None))
  }
  /** Collection-like TableQuery object for table Person */
  lazy val Person = new TableQuery(tag => new Person(tag))
}
