package crawler

import java.sql.Date
import java.text.SimpleDateFormat

import com.google.inject.Inject
import models.Tables
import models.Tables._
import models.Tables.profile.api._
import org.jsoup.Jsoup
import play.api.Configuration
import play.api.db.NamedDatabase
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.collection.JavaConverters._
import scala.util.Try

class Crawler @Inject()(ws: WSClient,
                        config: Configuration,
                        @NamedDatabase("server") protected val dbConfigProvider: DatabaseConfigProvider)
                       (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  final val cookie = config.get[String]("crawler.cookie")
  final val agent = config.get[String]("crawler.agent")
  final val baseUrl = config.get[String]("es.baseUrl")

  def forCompanyByKeyWord(key: String, page: Int): Unit =
    ws.url(s"https://www.qichacha.com/search_index?key=$key&ajaxflag=1&p=$page&")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get.map { response =>
      Jsoup.parse(response.body).select(".m_srchList tbody tr").asScala.map { company =>
        val img = company.child(0).child(0).attr("src") // img src

        val title = company.child(1).child(0)

        val name = title.text

        val ref = title.attr("href")

        val second = company.child(1).child(2)

        // 法定代表人
        val represent = if (second.child(0).tagName() != "a") {
          (second.ownText().substring(second.ownText().indexOf("：") + 1).trim, None)
        } else {
          (second.child(0).text().trim, Some(second.child(0).attr("href")))
        }

        // 注册资本和成立时间
        val spans = second.select("span")

        val capital = spans.get(0).text().substring(5)

        val found_time = new SimpleDateFormat("yyyy-MM-dd").parse(spans.get(1).text().substring(5))

        // 邮箱和电话
        val third = company.child(1).child(3)

        val mail = third.ownText().substring(3)

        val phone = third.child(0).ownText().substring(3)

        // 地址
        val addr = company.child(1).child(4).ownText().substring(3)

        // 状态
        val status = company.child(2).child(0).text()

        CompanyRow(0, name, Some(status), Some(represent._1), None, Some(capital), Some(new Date(found_time.getTime)), Some(mail), Some(phone), Some(addr), img = Some(img), ref = Some(ref))
      }
    }.foreach { data =>
      db.run(Company.map(_.name).result).foreach { allData =>
        val set = allData.toSet
        val CompanyWithId = Company returning Company.map(_.id) into ((cp, id) => cp.copy(id = id))
        import util.Formats._
        data.filterNot(x => set(x.name)).foreach { c =>
          db.run(CompanyWithId += parseMoney(c)).foreach { cid =>
            ws.url(baseUrl + "data/company/" + cid.id).put(Json.toJson(cid))
          }
        }
      }
    }

  def forSearchPage(key: String, index: Int): Unit =
    ws.url(s"https://www.qichacha.com/search?key=$key#index:$index&")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .foreach { response =>
        val pages = parseInt(Jsoup.parse(response.body).select("#ajaxpage").last().ownText())
        1 to math.min(10, pages) foreach (page => forCompanyByKeyWord(key, page))
      }

  def forBaseInfo(url: String, id: Int): Unit =
    ws.url(s"https://www.qichacha.com$url")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .foreach { response =>
        val html = Jsoup.parse(response.body)
        // 公司简介
        val introduction = if (html.select(".m-t-sm.m-b-sm").size() != 0)
          Some(html.select(".m-t-sm.m-b-sm").html())
        else
          None

        // keyNo
        val keyNo = if (html.select(".ba-table-base").size() > 1) {
          val url = html.select(".ba-table-base").get(1).child(0).attr("href")
          Some(url.substring(url.indexOf('=') + 1, url.indexOf('&')))
        } else None

        // 官方网站
        val website = if (html.select(".webauth-template").size() == 0) None
        else Some(html.select(".webauth-template").next().first().attr("href"))

        // 人物信息
        if (html.select(".bname").size() > 0) {
          val name = html.select(".bname").text()
          val ref = html.select(".bname").first().attr("href")
          val count = html.select(".btouzi").first().child(0).ownText().toInt
          val avator = html.select(".bheadimg").attr("src")

          db.run(Person.filter(_.addr === ref).result).foreach { data =>
            if (data.isEmpty) {
              val PersonWithID = (Person returning Person.map(_.id)) += PersonRow(0, name, Some(ref), Some(count), Some(avator))
              db.run(PersonWithID).foreach { id =>
                val q = for {c <- Company if c.ref === url} yield (c.represent, c.introduction, c.keyno, c.website)
                val action = q.update(Some(id), introduction, keyNo, website)
                db.run(action)
                forBossInfo(ref, id)
              }
            } else {
              val q = for {c <- Company if c.ref === url} yield (c.represent, c.introduction, c.keyno, c.website)
              val action = q.update(Some(data.head.id), introduction, keyNo, website)
              db.run(action)
            }
          }
        }

        // 工商信息
        if (html.select("#Cominfo").select("tr").size() > 0) {
          val table = if (html.select("#Cominfo").select("table").size() == 1)
            html.select("#Cominfo").select("table").get(0)
          else
            html.select("#Cominfo").select("table").get(1)

          val index = table.select("tr").asScala.flatMap { tr =>
            tr.children().asScala.map(_.text).sliding(2, 2).map(x => x.head -> x(1))
          }.toMap

          var c = BasicInfoRow(id)
          index foreach { x =>
            x._1 match {
              case "经营状态：" => c = c.copy(openStatus = Some(x._2))
              case "实缴资本：" => c = c.copy(paidCapital = Some(x._2))
              case "统一社会信用代码：" => c = c.copy(socialCreditCode = Some(x._2))
              case "纳税人识别号：" => c = c.copy(taxpayerIdentificationNumber = Some(x._2))
              case "注册号：" => c = c.copy(registrationNumber = Some(x._2))
              case "组织机构代码：" => c = c.copy(organizationCode = Some(x._2))
              case "公司类型：" => c = c.copy(typeOfCompany = Some(x._2))
              case "所属行业：" => c = c.copy(industry = Some(x._2))
              case "核准日期：" => c = c.copy(dateOfApproval = Some(x._2))
              case "登记机关：" => c = c.copy(registrationAuthority = Some(x._2))
              case "所属地区：" => c = c.copy(region = Some(x._2))
              case "英文名：" => c = c.copy(englishName = Some(x._2))
              case "曾用名" => c = c.copy(nameUsedBefore = Some(x._2))
              case "参保人数" => c = c.copy(numberOfParticipants = Some(x._2))
              case "人员规模" => c = c.copy(personnelScale = Some(x._2))
              case "营业期限" => c = c.copy(timeLimitForBusiness = Some(x._2))
              case "企业地址：" => c = c.copy(enterpriseAddress = Some(x._2))
              case "经营范围：" => c = c.copy(scopeOfOperation = Some(x._2))
              case _ =>
            }
          }
          db.run(BasicInfo += c)
        }

        // 股东信息
        if (html.select("#Sockinfo").select("tr").size() > 0) {
          val d = html.select("#Sockinfo").select("tr").asScala.tail.map { data =>
            val name = data.child(1).select("a").get(0).text()
            val ref = Try(data.child(1).select("a").get(0).attr("href")).map(Some(_)).getOrElse(None)
            val count = if (data.child(1).select("a").size() > 1)
              Some(parseInt(data.child(1).select("a").get(1).ownText()))
            else None
            val ratio = Some(data.child(2).ownText())
            val contribution = Some(data.child(3).ownText().toDouble)
            val date = Some(data.child(4).ownText())
            ShareholderInformationRow(id, Some(name), ref, count, ratio, contribution, date)
          }
          db.run(ShareholderInformation ++= d)
        }

        // 对外投资
        if (html.select("#touzilist").select("tr").size() > 0) {
          val d = html.select("#touzilist").select("tr").asScala.tail.map { data =>
            val name = data.child(0).child(0).text()
            val href = data.child(0).child(0).attr("href")
            val name2 = data.child(1).child(0).ownText()
            val href2 = data.child(1).child(0).attr("href")
            val capital = data.child(2).ownText()
            val ratio = data.child(3).ownText()
            val found_time = data.child(4).ownText()
            val status = data.child(5).child(0).ownText()
            OutboundInvestmentRow(id, Some(name), Some(href), Some(name2), Some(href2), Some(capital), Some(ratio), Some(found_time), Some(status))
          }
          db.run(OutboundInvestment ++= d)
        }

        // 主要人员
        if (html.select("#Mainmember").select("tr").size() > 0) {
          val d = html.select("#Mainmember").select("tr").asScala.tail.map { data =>
            val name = data.child(1).select("a").get(0).text()
            val href = data.child(1).select("a").get(0).attr("href")
            val count = Try(parseInt(data.child(1).select("a").get(1).ownText())).map(Some(_)).getOrElse(None)
            val position = data.child(2).ownText()
            MainPersonnelRow(id, Some(name), Some(href), count, Some(position))
          }
          db.run(MainPersonnel ++= d)
        }

        // 分支机构
        if (html.select("#Subcom").select("td").size() > 0) {
          val d = html.select("#Subcom").select("a").asScala.map { data =>
            val name = data.text()
            val href = data.attr("href")
            BranchRow(id, Some(name), Some(href))
          }
          db.run(Branch ++= d)
        }

        // 变更记录
        if (html.select("#Changelist").select("tr").size() > 0) {
          val d = html.select("#Changelist").select("tr").asScala.tail.map { data =>
            val date = data.child(1).ownText()
            val project = data.child(2).html()
            val before = data.child(3).html()
            val after = data.child(4).html()
            ChangeRecordRow(id, Some(date), Some(project), Some(before), Some(after))
          }
          db.run(ChangeRecord ++= d)
        }
      }

  def forBossInfo(url: String, id: Int): Unit =
    ws.url(s"https://www.qichacha.com$url")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .foreach { response =>
        println(response.body)
        val html = Jsoup.parse(response.body)

        val introduction = if (html.select("#peopleModal").size() != 0)
          Some(html.select("#peopleModal").select("section").text())
        else None

        db.run(Person.filter(_.id === id).map(x => (x.introduction, x.flag)).update((introduction, Some(true))))

        // 担任法定代表人
        if (html.select("#legal").select("tr").size() != 0) {
          val d = html.select("#legal").select("tr").asScala.tail.map { tr =>
            val name = tr.child(1).child(0).ownText()
            val href = tr.child(1).child(0).attr("href")
            val ratio = tr.child(2).ownText()
            val capital = tr.child(3).ownText()
            val region = tr.child(4).ownText()
            val kind = tr.child(5).ownText()
            val status = tr.child(6).child(0).ownText()

            BossRepresentRow(id, Some(name), Some(href), Some(ratio), Some(capital), Some(region), Some(kind), Some(status))
          }
          db.run(BossRepresent ++= d)
        }

        // 对外投资
        if (html.select("#invest").select("tr").size() != 0) {
          val d = html.select("#invest").select("tr").asScala.tail.map { tr =>
            val name = tr.child(1).child(0).ownText()
            val href = tr.child(1).child(0).attr("href")
            val ratio = tr.child(2).ownText()
            val capital = tr.child(3).ownText()
            val region = tr.child(4).ownText()
            val kind = tr.child(5).ownText()
            val represent = if (tr.child(6).children().size() == 0) tr.child(6).ownText()
            else tr.child(6).child(0).ownText()
            val represent_href = if (tr.child(6).children().size() == 0) None
            else Some(tr.child(6).child(0).attr("href"))
            val status = tr.child(7).child(0).ownText()

            BossInvestmentRow(id, Some(name), Some(href), Some(ratio), Some(capital), Some(region), Some(kind), Some(represent), represent_href, Some(status))
          }
          db.run(BossInvestment ++= d)
        }

        // 在外任职
        if (html.select("#postOffice").select("tr").size() != 0) {
          val d = html.select("#postOffice").select("tr").asScala.tail.map { tr =>
            val name = tr.child(1).child(0).ownText()
            val href = tr.child(1).child(0).attr("href")
            val position = tr.child(2).ownText()
            val capital = tr.child(3).ownText()
            val region = tr.child(4).ownText()
            val kind = tr.child(5).ownText()
            val represent = if (tr.child(6).children().size() == 0) tr.child(6).ownText()
            else tr.child(6).child(0).ownText()
            val represent_href = if (tr.child(6).children().size() == 0) None
            else Some(tr.child(6).child(0).attr("href"))
            val status = tr.child(7).child(0).ownText()

            BossPositionRow(id, Some(name), Some(href), Some(position), Some(capital), Some(region), Some(kind), Some(represent), represent_href, Some(status))
          }
          db.run(BossPosition ++= d)
        }

        // 历史担任法定代表人
        if (html.select("#history").select("tr").size() != 0) {
          val d = html.select("#history").select("tr").asScala.tail.map { tr =>
            val name = tr.child(1).child(0).ownText()
            val href = tr.child(1).child(0).attr("href")
            val capital = tr.child(2).ownText()
            val region = tr.child(3).ownText()
            val kind = tr.child(4).ownText()
            val status = tr.child(5).text()

            BossHistoryRepresentRow(id, Some(name), Some(href), Some(capital), Some(region), Some(kind), Some(status))
          }
          db.run(BossHistoryRepresent ++= d)
        }

        // 历史对外投资
        if (html.select("[name=hisinvest]").select("tr").size() != 0) {
          val d = html.select("[name=hisinvest]").select("tr").asScala.tail.map { tr =>
            val name = tr.child(1).child(0).ownText()
            val href = tr.child(1).child(0).attr("href")
            val capital = tr.child(2).ownText()
            val represent = if (tr.child(3).children().size() == 0) tr.child(3).ownText()
            else tr.child(3).child(0).ownText()
            val represent_href = if (tr.child(3).children().size() == 0) None
            else Some(tr.child(3).child(0).attr("href"))
            val status = tr.child(4).child(0).ownText()

            BossHistoryInvestmentRow(id, Some(name), Some(href), Some(capital), Some(represent), represent_href, Some(status))
          }
          db.run(BossHistoryInvestment ++= d)
        }

        // 历史在外任职
        if (html.select("#hispostOffice").select("tr").size() != 0) {
          val d = html.select("#hispostOffice").select("tr").asScala.tail.map { tr =>
            val name = tr.child(1).child(0).text()
            val href = tr.child(1).child(0).attr("href")
            val position = tr.child(2).ownText()
            val capital = tr.child(3).ownText()
            val represent = if (tr.child(4).children().size() == 0) tr.child(4).ownText()
            else tr.child(4).child(0).ownText()
            val represent_href = if (tr.child(4).children().size() == 0) None
            else Some(tr.child(4).child(0).attr("href"))
            val status = tr.child(5).text()

            BossHistoryPositionRow(id, Some(name), Some(href), Some(position), Some(capital), Some(represent), represent_href, Some(status))
          }
          db.run(BossHistoryPosition ++= d)
        }

        // 控股企业
        if (html.select("#holdcolist").select("tr").size() != 0) {
          val d = html.select("#holdcolist").select("tr").asScala.tail.map { tr =>
            val name = tr.child(1).child(0).ownText()
            val href = tr.child(1).child(0).attr("href")
            val ratio = tr.child(2).ownText()
            val chain = tr.child(3).html()

            BossHoldingCompanyRow(id, Some(name), Some(href), Some(ratio), Some(chain))
          }
          db.run(BossHoldingCompany ++= d)
        }
      }


  def parseInt(s: String): Int = s.filter(_.isDigit).toInt

  def parseDouble(s: String): Double = s.filter(x => x.isDigit || x == '.').toDouble

  def parseMoney(company: CompanyRow): Tables.CompanyRow = company.capital.get match {
    case s: String if s.indexOf("人民币") != -1 => company.copy(money = Some(parseDouble(s)))
    case s: String if s.indexOf("美元") != -1 => company.copy(money = Some(parseDouble(s) * 6.8982))
    case s: String if s.indexOf("台币") != -1 => company.copy(money = Some(parseDouble(s) * 0.224))
    case _ => company
  }


}
