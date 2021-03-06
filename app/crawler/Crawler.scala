package crawler

import java.sql.Date
import java.text.SimpleDateFormat

import com.google.inject.Inject
import models.Entities.{LinkResult, NodeResult, _}
import models.Tables
import models.Tables._
import models.Tables.profile.api._
import org.jsoup.Jsoup
import play.api.Configuration
import play.api.db.NamedDatabase
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.{JsArray, JsObject, JsValue, Json}
import play.api.libs.ws.WSClient
import slick.jdbc.JdbcProfile
import util.Formats._

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class Crawler @Inject()(ws: WSClient,
                        config: Configuration,
                        @NamedDatabase("server") protected val dbConfigProvider: DatabaseConfigProvider)
                       (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  final val cookie = config.get[String]("crawler.cookie")
  final val agent = config.get[String]("crawler.agent")
  final val baseUrl = config.get[String]("es.baseUrl")

  // 4 => represent 1 => default 2 => company 3 => 6 => 高管 8=>品牌
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
        val pages = Try(parseInt(Jsoup.parse(response.body).select("#ajaxpage").last().ownText())).getOrElse(1)
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

        keyNo match {
          case Some(key) => forEquityStructureGraph(key, id)
          case None =>
        }

        // 官方网站
        val website = if (html.select(".webauth-template").size() == 0) None
        else Some(html.select(".webauth-template").next().first().attr("href"))

        // 人物信息
        if (html.select(".bname").size() > 0) {
          val name = html.select(".bname").text()
          val ref = html.select(".bname").first().attr("href")
          val count = html.select(".btouzi").first().child(0).ownText().toInt
          val avator: String = html.select(".bheadimg").attr("src") match {
            case s: String if s.startsWith("/") => s"https://www.qichacha.com$s"
            case s => s
          }

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
        val scope = if (html.select("#Cominfo").select("tr").size() > 0) {
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
          index.get("经营范围")
        } else None

        // 股东信息
        val shareholderInformation = if (html.select("#Sockinfo").select("tr").size() > 0) {
          val d = html.select("#Sockinfo").select("tr").asScala.tail.map { data =>
            val name = data.child(1).select("a").get(0).text()
            val ref = Try(data.child(1).select("a").get(0).attr("href")).map(Some(_)).getOrElse(None)
            val count = if (data.child(1).select("a").size() > 1)
              Some(parseInt(data.child(1).select("a").get(1).ownText()))
            else None
            val ratio = Some(data.child(2).ownText())
            val contribution = Some(data.child(3).text())
            val date = Some(data.child(4).ownText())
            ShareholderInformationRow(id, Some(name), ref, count, ratio, contribution, date)
          }
          db.run(ShareholderInformation ++= d)
          Some(d)
        } else None

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
        val mainPersonnel = if (html.select("#Mainmember").select("tr").size() > 0) {
          val d = html.select("#Mainmember").select("tr").asScala.tail.map { data =>
            val name = data.child(1).select("a").get(0).text()
            val href = data.child(1).select("a").get(0).attr("href")
            val count = Try(parseInt(data.child(1).select("a").get(1).ownText())).map(Some(_)).getOrElse(None)
            val position = data.child(2).ownText()
            MainPersonnelRow(id, Some(name), Some(href), count, Some(position))
          }
          db.run(MainPersonnel ++= d)
          Some(d)
        } else None

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

        // 更新搜索引擎中的数据
        db.run(Company.filter(_.id === id).result.head).foreach { company =>
          val json = Json.toJson(company).as[JsObject]
          val ans = json.+("main_personnel" -> Json.toJson(mainPersonnel))
            .+("shareholder_information" -> Json.toJson(shareholderInformation))
            .++(Json.obj("scope_of_operation" -> scope))
            .++(Json.obj("introduction" -> introduction))
          ws.url(baseUrl + "data/company/" + company.id).put(ans)
        }
      }

  def forLegalAction(key: String, companyName: String, id: Int): Unit =
    ws.url(s"https://www.qichacha.com/company_getinfos?unique=$key&companyname=$companyName&tab=susong")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .foreach { response =>
        val html = Jsoup.parse(response.body)

        //裁判文书 referee
        if (html.select("#wenshulist").select("tr").size() > 0) {
          val d = html.select("#wenshulist").select("tr").asScala.tail.map { tr =>
            val name = tr.child(1).text()
            val date = tr.child(2).text()
            val num = tr.child(3).text()
            val identity = tr.child(4).html()
            val court = tr.child(5).text()
            RefereeRow(id, Some(name), Some(date), Some(num), Some(identity), Some(court))
          }
          db.run(Referee ++= d)
        }

        //法院公告 court_notice
        if (html.select("#gonggaolist").select("tr").size() > 0) {
          val d = html.select("#gonggaolist").select("tr").asScala.tail.map { tr =>
            val party = tr.child(1).text()
            val `type` = tr.child(2).text()
            val announcer = tr.child(3).text()
            val date = tr.child(4).html()
            val content = tr.child(5).text()
            CourtNoticeRow(id, Some(party), Some(`type`), Some(announcer), Some(date), Some(content))
          }
          db.run(CourtNotice ++= d)
        }

        //开庭公告 opening_notice
        if (html.select("#noticelist").select("tr").size() > 0) {
          val d = html.select("#noticelist").select("tr").asScala.tail.map { tr =>
            val num = tr.child(1).text()
            val date = tr.child(2).text()
            val cause = tr.child(3).text()
            val source = tr.child(4).html()
            val target = tr.child(5).text()
            OpeningNoticeRow(id, Some(num), Some(date), Some(cause), Some(source), Some(target))
          }
          db.run(OpeningNotice ++= d)
        }
      }

  def forOperatingConditions(key: String, companyName: String, id: Int): Unit =
    ws.url(s"https://www.qichacha.com/company_getinfos?unique=$key&companyname=$companyName&tab=run")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .foreach { response =>
        val html = Jsoup.parse(response.body)
        //行政许可 [工商局] administrative_license_ic
        if (html.select("#permissionlist").select("tr").size() > 0) {
          val d = html.select("#permissionlist").select("tr").asScala.tail.filter(_.children().size() > 0).map { tr =>
            val num = tr.child(1).text()
            val file_name = tr.child(2).text()
            val dateFrom = tr.child(3).text()
            val dateTo = tr.child(4).text()
            val authority = tr.child(5).text()
            val content = tr.child(6).text()
            AdministrativeLicenseIcRow(id, Some(num), Some(file_name), Some(dateFrom), Some(dateTo), Some(authority), Some(content))
          }
          db.run(AdministrativeLicenseIc ++= d)
        }

        //行政许可 [信用中国] administrative_license_ch
        if (html.select("#permissionlist").next().select("tr").size() > 0) {
          val d = html.select("#permissionlist").next().select("tr").asScala.tail.filter(_.children().size() > 0).map { tr =>
            val project = tr.child(1).text()
            val region = tr.child(2).text()
            val date = tr.child(3).text()
            val content = tr.child(4).text()
            AdministrativeLicenseChRow(id, Some(project), Some(region), Some(date), Some(content))
          }
          db.run(AdministrativeLicenseCh ++= d)
        }

        //税务信用 tax_credit
        if (html.select("#taxCreditList").select("tr").size() > 0) {
          val d = html.select("#taxCreditList").select("tr").asScala.tail.filter(_.children().size() > 0).map { tr =>
            val year = tr.child(1).text()
            val code = tr.child(2).text()
            val level = tr.child(3).text()
            val unit = tr.child(4).text()
            TaxCreditRow(id, Some(year), Some(code), Some(level), Some(unit))
          }
          db.run(TaxCredit ++= d)
        }

        //产品信息  product_information
        if (html.select("#productlist").select("tr").size() > 0) {
          val d = html.select("#productlist").select("tr").asScala.tail.map { tr =>
            val src = tr.child(1).child(0).attr("src")
            val name = tr.child(2).text()
            val financing = tr.child(3).text()
            val found_time = tr.child(4).text()
            val region = tr.child(5).text()
            val introduction = tr.child(6).text()
            ProductInformationRow(id, Some(src), Some(name), Some(financing), Some(found_time), Some(region), Some(introduction))
          }
          db.run(ProductInformation ++= d)
        }

        //融资信息  financing_information
        if (html.select("#financingList").select("tr").size() > 0) {
          val d = html.select("#financingList").select("tr").asScala.tail.map { tr =>
            val date = tr.child(1).text()
            val name = tr.child(2).text()
            val level = tr.child(3).text()
            val moneny = tr.child(4).text()
            val source = tr.child(5).html()
            FinancingInformationRow(id, Some(date), Some(name), Some(level), Some(moneny), Some(source))
          }
          db.run(FinancingInformation ++= d)
        }

        //招投标信息  bidding_information
        if (html.select("#tenderlist").select("tr").size() > 0) {
          val d = html.select("#tenderlist").select("tr").asScala.tail.map { tr =>
            val desc = tr.child(1).text()
            val date = tr.child(2).text()
            val region = tr.child(3).text()
            val kind = tr.child(4).text()
            BiddingInformationRow(id, Some(desc), Some(date), Some(region), Some(kind))
          }
          db.run(BiddingInformation ++= d)
        }

        //招聘  recruitment
        if (html.select("#joblist").select("tr").size() > 0) {
          val d = html.select("#joblist").select("tr").asScala.tail.map { tr =>
            val date = tr.child(1).text()
            val position = tr.child(2).text()
            val money = tr.child(3).text()
            val education = tr.child(4).text()
            val experience = tr.child(5).text()
            val city = tr.child(6).text()
            RecruitmentRow(id, Some(date), Some(position), Some(money), Some(education), Some(experience), Some(city))
          }
          db.run(Recruitment ++= d)
        }

        //微信公众号  public_number
        if (html.select("#wechatlist").select("tr").size() > 0) {
          val d = html.select("#wechatlist").select("tr").asScala.tail.filter(_.children().size() > 0).map { tr =>
            val avator = tr.child(1).child(0).attr("src")
            val name = tr.child(2).text()
            val code = tr.child(3).text()
            val introduction = tr.child(4).text()
            PublicNumberRow(id, Some(avator), Some(name), Some(code), Some(introduction))
          }
          db.run(PublicNumber ++= d)
        }

        //新闻舆情  public_number
        if (html.select("#newslist").select("tr").size() > 0) {
          val d = html.select("#newslist").select("tr").asScala.map { tr =>
            val title = tr.child(0).select(".title").text()
            val source = tr.child(0).select(".clear.subtitle").get(0).ownText()
            val date = tr.child(0).select(".pull-right").get(0).ownText()
            NewsLyricsRow(id, Some(title), Some(source), Some(date))
          }
          db.run(NewsLyrics ++= d)
        }

      }

  def forBossInfo(url: String, id: Int): Unit =
    ws.url(s"https://www.qichacha.com$url")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .foreach { response =>
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

  def forEquityStructureGraph(key: String, id: Int): Future[JsValue] =
    ws.url(s"https://www.qichacha.com/cms_guquanakzr?keyNo=$key")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .map { res =>
        val data = Json.parse(res.body
          .replace("CompanyName", "name")
          .replace("DetailList", "children")
          .replace("Name", "name"))
        db.run(CompanyGraph += CompanyGraphRow(id, 0, Some(data.toString())))
        data
      }

  def forEnterpriseGraph(key: String, id: Int): Future[JsValue] =
    ws.url(s"https://www.qichacha.com/cms_businessmap?keyNo=$key")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .map { res =>
        val data = Json.parse(res.body)
        db.run(CompanyGraph += CompanyGraphRow(id, 1, Some(data.toString())))
        data
      }

  def forInvestmentGraph(key: String, id: Int): Future[JsValue] =
    ws.url(s"https://www.qichacha.com/cms_map?keyNo=$key&upstreamCount=4&downstreamCount=4")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .map { res =>
        val data = Json.parse(res.body)
        db.run(CompanyGraph += CompanyGraphRow(id, 2, Some(data.toString())))
        data
      }


  def forAssociationGraph(key: String, id: Int): Future[JsObject] =
    ws.url(s"https://www.qichacha.com/company_muhouAction?keyNo=$key")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .map { res =>
        val json = res.json.\("success").\("results")(0).\("data")(0).\("graph")
        val node = json.\("nodes").as[Seq[TempNode]].groupBy(_.id).map(x => x._2.head)
        val link = json.\("relationships").as[Seq[TempLink]]

        val nodes = node.map { x =>
          var cate = x.labels(0) match {
            case "Company" => 0
            case "Person" => 1
            case _ => 2
          }

          if (x.properties.keyNo == key) cate = 2

          NodeResult(x.id, x.properties.keyNo, x.properties.name, cate)
        }

        val index = nodes.zipWithIndex.toMap

        val map = nodes.map(x => x.id -> x).toMap


        val links = link.distinct.map { x =>
          val relation = x.`type` match {
            case "EMPLOY" => x.properties.role.getOrElse("任职")
            case "INVEST" => "投资"
            case _ => "投资"
          }

          LinkResult(index(map(x.startNode)), index(map(x.endNode)), relation)
        }

        val merge = links.groupBy(x => (x.source, x.target))
          .map(x => LinkResult(x._1._1, x._1._2, x._2
            .map(_.relation).distinct.mkString("、")))

        val data = Json.obj("nodes" -> nodes, "links" -> merge)

        db.run(CompanyGraph += CompanyGraphRow(id, 3, Some(data.toString)))

        data
      }

  def forSecondEquityStructureGraph(key: String, id: Int): Future[JsValue] =
    ws.url(s"https://www.qichacha.com/cms_guquanmap2?keyNo=$key")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .map { res =>
        val data = Json.parse(res.body)
        db.run(CompanyGraph += CompanyGraphRow(id, 4, Some(data.toString())))
        data
      }

  def forCompanyShortInfo(key: String): Future[JsValue] =
    ws.url(s"https://www.qichacha.com/more_findRelationsDetail?keyNo=$key")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .map { res =>
        val data = Json.parse(res.body)
        db.run(ShortInfo += ShortInfoRow(key, Some(data.toString())))
        data
      }


  def forMultipleAssociationGraph(url: String): Future[JsObject] =
    ws.url(s"https://www.qichacha.com/more_findRelations2?nodes=$url")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .map { res =>
        val data = res.json.\("Result").\("results")(0).\("data")
        val array = data.get.asInstanceOf[JsArray].value
        val node = array.flatMap(json => json.\("graph").\("nodes").as[Seq[TempNode]]).groupBy(_.id).map(x => x._2.head)
        val link = array.flatMap(json => json.\("graph").\("relationships").as[Seq[TempLink]]).distinct

        // val highlight = url.split(",").flatMap(_.split("_")).toSet

        val nodes = node.map { x =>
          val cate = x.labels(0) match {
            case "Company" => 0
            case "Person" => 1
            case _ => 2
          }

          NodeResult(x.id, x.properties.keyNo, x.properties.name, cate)
        }

        val index = nodes.zipWithIndex.toMap

        val map = nodes.map(x => x.id -> x).toMap

        val links = link.distinct.map { x =>
          val relation = x.`type` match {
            case "EMPLOY" => x.properties.role.getOrElse("任职")
            case "INVEST" => "投资"
            case _ => "投资"
          }

          LinkResult(index(map(x.startNode)), index(map(x.endNode)), relation)
        }

        val merge = links.groupBy(x => (x.source, x.target))
          .map(x => LinkResult(x._1._1, x._1._2, x._2
            .map(_.relation).distinct.mkString("、")))

        Json.obj("nodes" -> nodes, "links" -> merge)
      }

  def forNews: Future[JsValue] =
    ws.url("https://www.qichacha.com/news")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .map { response =>
        val html = Jsoup.parse(response.body)

        val d = html.select(".list-group-item.clearfix").asScala.take(8).map { div =>
          val img = Try(div.child(0).select("img").attr("src")).toOption
          val title = div.select(".clear").get(0).child(0).child(0).ownText()
          val ref = div.select(".clear").get(0).child(0).child(0).attr("href")
          val from = div.select("small").get(0).child(0).ownText()
          val time = div.select("small").get(0).child(1).ownText()
          NewsRow(img, Some(title), Some(ref), Some(from), Some(time))
        }
        db.run(DBIO.seq(News.delete, News ++= d))
        Json.toJson(d)
      }

  def forNewsBody(url: String): Future[JsValue] =
    ws.url(s"https://www.qichacha.com$url")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .map { response =>
        val html = Jsoup.parse(response.body)

        val body = html.select(".panel.b-a").get(0).html()

        val row = NewsBodyRow(url, body)

        db.run(NewsBody += row)

        Json.toJson(row)
      }

  def forBrands(key: String): Unit =
    ws.url(s"https://www.qichacha.com/more_brand?key=$key")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .foreach { response =>
        val pages = Try(parseInt(Jsoup.parse(response.body).select("#ajaxpage").last().ownText())).getOrElse(1)
        1 to math.min(10, pages) foreach (page => forBrand(key, page))
      }

  def forBrand(key: String, page: Int): Unit =
    ws.url(s"https://www.qichacha.com/more_brand?key=$key&ajaxflag=true&p=$page")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .foreach { response =>
        val html = Jsoup.parse(response.body)

        if (html.select("#searchlist").size() > 0) {
          val d = html.select("#searchlist").asScala.map { line =>
            val srcSource = line.select(".pull-left.thumb-lg.m-r").first().child(0).attr("src")
            val src = if (srcSource.startsWith("/")) "https://www.qichacha.com" + srcSource else srcSource
            val ref = line.child(0).attr("href")
            val name = line.select(".name").text()
            val numAndDate = line.select("small").first().ownText().split(" ")
            val num = numAndDate(1)
            val date = numAndDate(3)
            val kindAll = line.select("small").get(1).text()
            val kind = kindAll.substring(kindAll.indexOf(" "))
            val status = if (line.select(".nstatus").size() > 0) Some(line.select(".nstatus").text()) else None
            val applicant = line.select("footer a").text()
            BrandRow(0, Some(src), Some(ref), Some(name), Some(num), Some(date), Some(applicant), Some(kind), status)
          }

          // 去除重复
          db.run(Brand.map(_.ref).result).foreach { allData =>
            val set = allData.toSet
            val toSave = d.filterNot(x => set(x.ref))

            // 添加cid
            db.run(Company.filter(_.name.inSet(toSave.flatMap(_.applicant))).result).foreach { companies =>
              val index = companies.map(x => x.name -> x.id).toMap
              val dd = toSave.map(x => if (index.contains(x.applicant.get)) x.copy(cid = Some(index(x.applicant.get))) else x)
              val BrandWithId = Brand returning Brand.map(_.id) into ((brand, id) => brand.copy(id = id))
              db.run(BrandWithId ++= dd).foreach(_.foreach(b => ws.url(baseUrl + "brand/doc/" + b.id).put(Json.toJson(b))))
            }
          }
        }
      }

  def forLoseCredits(key: String): Unit =
    ws.url(s"https://www.qichacha.com/more_brand?key=$key")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .foreach { response =>
        val pages = Try(parseInt(Jsoup.parse(response.body).select("#ajaxpage").last().ownText())).getOrElse(1)
        1 to math.min(10, pages) foreach (page => forLoseCredit(key, page))
      }

  def forLoseCredit(key: String, page: Int): Unit =
    ws.url(s"https://www.qichacha.com/more_shixin?key=$key&ajaxflag=true&p=$page")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .foreach { response =>
        val html = Jsoup.parse(response.body)

        if (html.select("#searchlist").size() > 0) {
          val d = html.select("#searchlist").asScala.map { line =>
            val ref = line.child(0).attr("href")
            val name = line.select(".name").text()
            val numAll = line.select("small").get(0).text()
            val num = numAll.substring(numAll.indexOf("：") + 1)
            val statusAll = line.select("small").get(1).text().split(" ").flatMap(_.split("："))
            val status = statusAll(1)
            val date = statusAll(3)
            val courtAll = line.select(".panel-footer.clear").text()
            val court = courtAll.substring(courtAll.indexOf("：") + 1)
            LoseCreditRow(0, Some(name), Some(num), Some(status), Some(date), Some(court), Some(ref))
          }

          // 去除重复
          db.run(LoseCredit.map(_.ref).result).foreach { allData =>
            val set = allData.toSet
            val toSave = d.filterNot(x => set(x.ref))
            val LoseCreditWithId = LoseCredit returning LoseCredit.map(_.id) into ((credit, id) => credit.copy(id = id))
            db.run(LoseCreditWithId ++= toSave).foreach(_.foreach(b => ws.url(baseUrl + "credit/doc/" + b.id).put(Json.toJson(b))))
          }
        }
      }

  def forBrandBody(id: Int, url: String): Future[Tables.BrandBodyRow] =
    ws.url(s"https://www.qichacha.com$url")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .map { response =>
        val html = Jsoup.parse(response.body)

        val body = html.select("#searchlist").html()

        val d = BrandBodyRow(id, Some(body))

        db.run(BrandBody += d)

        d
      }

  def forLoseCreditBody(id: Int, url: String): Future[Tables.LoseCreditBodyRow] =
    ws.url(s"https://www.qichacha.com$url")
      .addHttpHeaders(
        "User-Agent" -> agent,
        "Cookie" -> cookie)
      .get()
      .map { response =>
        val html = Jsoup.parse(response.body)
        println(html)

        val body = html.select("#searchlist").html()

        val d = LoseCreditBodyRow(id, Some(body))

        db.run(LoseCreditBody += d)

        d
      }

  def parseInt(s: String): Int = s.filter(_.isDigit).toInt

  def parseDouble(s: String): Double = s.filter(x => x.isDigit || x == '.').toDouble

  def parseMoney(company: CompanyRow): CompanyRow = company.capital.get match {
    case s: String if s.indexOf("人民币") != -1 => company.copy(money = Some(parseDouble(s)))
    case s: String if s.indexOf("美元") != -1 => company.copy(money = Some(parseDouble(s) * 6.8982))
    case s: String if s.indexOf("台币") != -1 => company.copy(money = Some(parseDouble(s) * 0.224))
    case _ => company
  }


}
