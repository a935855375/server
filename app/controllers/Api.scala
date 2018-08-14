package controllers

import com.google.inject.Inject
import org.jsoup.Jsoup
import play.api.db.NamedDatabase
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

//import scala.collection.JavaConverters._

class Api @Inject()(cc: MessagesControllerComponents,
                    ws: WSClient,
                    @NamedDatabase("server") protected val dbConfigProvider: DatabaseConfigProvider)
                   (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  def getAllCompany: Action[AnyContent] = Action.async { implicit request =>
    ws.url("https://www.qichacha.com/search_index?key=%25E5%25B0%258F%25E7%25B1%25B3&ajaxflag=1&p=1&")
      .addHttpHeaders(
        "User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36",
        "Cookie" -> "UM_distinctid=163ec752d68e3-0f8cde2302ebb9-183e6952-1fa400-163ec752d6c845; zg_did=%7B%22did%22%3A%20%22163ec752da229f-02374e40db1186-183e6952-1fa400-163ec752da3e1e%22%7D; acw_tc=AQAAAGh71hSx/QcAY0FM2vCTImTFRzQ0; _uab_collina=152868049866614828164468; PHPSESSID=kh4h6c56fioj6pt1gpu8pbnmh6; CNZZDATA1254842228=1467089437-1528678839-%7C1533697292; Hm_lvt_3456bee468c83cc63fb5147f119f1075=1532961130,1532961146,1532999527,1533700988; hasShow=1; _umdata=A502B1276E6D5FEFF6695553F653401E3013E055714536A6FD927B8A542DBF54F370B3E0A38A2F9BCD43AD3E795C914CFE8A3E5A81FA2865894685088D5A0843; Hm_lpvt_3456bee468c83cc63fb5147f119f1075=1533701019; zg_de1d1a35bfa24ce29bbf2c7eb17e6c4f=%7B%22sid%22%3A%201533700987676%2C%22updated%22%3A%201533701053381%2C%22info%22%3A%201533700987680%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%7D%22%2C%22referrerDomain%22%3A%20%22www.baidu.com%22%2C%22cuid%22%3A%20%2298553b777c7239746cd4812bc09dd4a6%22%7D")
      .get().map { x =>
      Jsoup.parse(x.body).select(".m_srchList tbody tr").forEach { company =>
        println(company.child(0).child(0).attr("src")) // img src

        val title = company.child(1).child(0)
        println(title.text() + "  " + title.attr("href")) // name + url

        val second = company.child(1).child(1)

        println(company.child(2).child(0).text()) // status
      }
      Ok(x.body)
    }
  }
}
