object Gen extends App{
  val profile = "slick.jdbc.MySQLProfile"
  val jdbcDriver = "com.mysql.cj.jdbc.Driver"
  val url = "jdbc:mysql://localhost:3306/data?characterEncoding=UTF8&useSSL=false&nullNamePatternMatchesAll=true"
  val outputFolder = "app"
  val pkg = "models"
  val user = "root"
  val password = "199729"
  slick.codegen.SourceCodeGenerator.main(
    Array(profile, jdbcDriver, url, outputFolder, pkg, user, password)
  )
  System.err.println("dump finished")
}
