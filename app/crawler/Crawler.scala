package crawler

import java.sql.Date
import java.text.SimpleDateFormat

import com.google.inject.Inject
import models.Tables._
import models.Tables.profile.api._
import org.jsoup.Jsoup
import play.api.Configuration
import play.api.db.NamedDatabase
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.ws.WSClient
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.collection.JavaConverters._

class Crawler @Inject()(ws: WSClient,
                        config: Configuration,
                        @NamedDatabase("server") protected val dbConfigProvider: DatabaseConfigProvider)
                       (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {
  final val cookie = config.get[String]("crawler.cookie")
  final val agent = config.get[String]("crawler.agent")

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

        /*val personWithId = (Person returning Person.map(_.id)) into ((person, id) => person.copy(id = id))

        val action = Person += PersonRow(0, represent._1, represent._2)

        db.run(action)*/

        CompanyRow(0, name, Some(status), Some(represent._1), None, Some(capital), Some(new Date(found_time.getTime)), Some(mail), Some(phone), Some(addr), None, None, Some(img), Some(ref))
      }
    }.foreach { data =>
      db.run(Company.map(_.name).result).foreach { allData =>
        val set = allData.toSet
        db.run(Company ++= data.filterNot(x => set(x.name)))
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
        1 to pages foreach (page => forCompanyByKeyWord(key, page))
      }

  def forBaseInfo(url: String): Unit =
    ws.url(s"https://www.qichacha.com$url")
      .addHttpHeaders(
        "User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36",
        "Cookie" -> cookie)
      .get()
      .foreach { response =>
        val html = Jsoup.parse(response.body)
        // 公司简介
        println(html.select(".m-t-sm.m-b-sm").html())

        // keyNo
        if (html.select(".ba-table-base").size() > 1) {
          val url = html.select(".ba-table-base").get(1).child(0).attr("href")
          println(url.substring(url.indexOf('?') + 1, url.indexOf('&')))
        }

        // 人物信息
        if (html.select(".bname").size() > 0) {
          val name = html.select(".bname").first().ownText()
          val ref = html.select(".bname").first().attr("href")
          val count = html.select(".btouzi").first().child(0).ownText().toInt

          db.run(Person.filter(_.addr === ref).result).foreach { data =>
            if (data.isEmpty) {
              val PersonWithID = (Person returning Person.map(_.id)) += PersonRow(0, name, Some(ref), Some(count))
              db.run(PersonWithID).foreach { id =>
                val q = for {c <- Company if c.ref === url} yield c.represent
                val action = q.update(Some(id))
                db.run(action)
              }
            } else {
              val q = for {c <- Company if c.ref === url} yield c.represent
              val action = q.update(Some(data.head.id))
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
            tr.children().asScala.map(_.ownText()).sliding(2, 2).map(x => x.head -> x(1))
          }.toMap
          println(index)
        }

        // 官方网站
        val website = if (html.select(".webauth-template").size() == 0) None
        else Some(html.select(".webauth-template").next().first().attr("href"))

        println(website)

        // 股东信息
        html.select("#Sockinfo").select("tr").asScala.tail.foreach { data =>
          println(data.child(1).select("a").get(0).ownText())
          if (data.child(1).select("a").size() > 1)
            println(parseInt(data.child(1).select("a").get(1).ownText()))
          println(data.child(2).ownText())
          println(data.child(3).ownText())
          println(data.child(4).ownText())
        }

        // 对外投资
        if (html.select("#touzilist").select("tr").size() > 0)
          html.select("#touzilist").select("tr").asScala.tail.foreach { data =>
            println(data.child(0).child(0).ownText())
            println(data.child(1).child(0).ownText())
            println(data.child(2).ownText())
            println(data.child(3).ownText())
            println(data.child(4).ownText())
            println(data.child(5).child(0).ownText())
          }

        // 主要人员
        if (html.select("#Mainmember").select("tr").size() > 0)
          html.select("#Mainmember").select("tr").asScala.tail.foreach { data =>
            println(data.child(1).select("a").get(0).ownText())
            println(parseInt(data.child(1).select("a").get(1).ownText()))
            println(data.child(2).ownText())
          }

        // 分支机构
        html.select("#Subcom").select("span").forEach { data =>
          println(data.ownText())
        }

        // 变更记录
        if (html.select("#Changelist").select("tr").size() > 0)
          html.select("#Changelist").select("tr").asScala.tail.foreach { data =>
            println(data.child(1).ownText())
            println(data.child(2).html())
            println(data.child(3).html())
            println(data.child(4).html())
          }

      }


  def parseInt(s: String): Int = s.filter(_.isDigit).toInt

}
