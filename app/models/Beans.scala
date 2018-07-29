package models

import java.util.Date

import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{Request, WrappedRequest}

object Format {
  implicit lazy val UserBeanFormat: OFormat[UserBean] = Json.format[UserBean]
  implicit lazy val TokenFormat: OFormat[Token] = Json.format[Token]
  implicit lazy val JWTTokenFormat: OFormat[LoginRes] = Json.format[LoginRes]
  implicit lazy val RegisterInputFormat: OFormat[RegisterInput] = Json.format[RegisterInput]
  implicit lazy val CommonResInputFormat: OFormat[CommonRes] = Json.format[CommonRes]
  implicit lazy val CompanyFormat: OFormat[Company] = Json.format[Company]
  implicit lazy val BasicInfoFormat: OFormat[BasicInfo] = Json.format[BasicInfo]
  implicit lazy val ShareholderInformationFormat: OFormat[ShareholderInformation] = Json.format[ShareholderInformation]
  implicit lazy val OutboundInvestmentFormat: OFormat[OutboundInvestment] = Json.format[OutboundInvestment]
  implicit lazy val BranchFormat: OFormat[Branch] = Json.format[Branch]
  implicit lazy val ChangeRecordFormat: OFormat[ChangeRecord] = Json.format[ChangeRecord]
  implicit lazy val MainPersonnelFormat: OFormat[MainPersonnel] = Json.format[MainPersonnel]
  implicit lazy val CompanyProfileFormat: OFormat[CompanyProfile] = Json.format[CompanyProfile]
  implicit lazy val InterestedPeopleFormat: OFormat[InterestedPeople] = Json.format[InterestedPeople]
  implicit lazy val TreeFormat: OFormat[Tree] = Json.format[Tree]
  implicit lazy val ColorTreeFormat: OFormat[ColorTree] = Json.format[ColorTree]
  implicit lazy val LinksFormat: OFormat[Links] = Json.format[Links]
  implicit lazy val NodesFormat: OFormat[Nodes] = Json.format[Nodes]
  implicit lazy val BossFormat: OFormat[Boss] = Json.format[Boss]

  // 调试用
  implicit lazy val PropertyFormat: OFormat[Property] = Json.format[Property]
  implicit lazy val TempNodeFormat: OFormat[TempNode] = Json.format[TempNode]
  implicit lazy val NodeResultFormat: OFormat[NodeResult] = Json.format[NodeResult]

  implicit lazy val Property2Format: OFormat[Property2] = Json.format[Property2]
  implicit lazy val TempLinkFormat: OFormat[TempLink] = Json.format[TempLink]
  implicit lazy val LinkResultFormat: OFormat[LinkResult] = Json.format[LinkResult]
}

class UserRequest[A](val username: String, request: Request[A]) extends WrappedRequest[A](request)

case class Token(subject: String, expiresIn: Int)

case class User(id: Int, username: String, password: String, nickname: Option[String])

case class UserBean(username: String, password: String)

case class RegisterInput(username: String, mail: String, password: String)

case class LoginRes(status: Int, id_token: Option[String], expiresIn: Option[Int], nickname: Option[String])

case class CommonRes(status: Int, reason: String)

case class Company(id: Int, name: String, status: String, represent: String, capital: Double, found_time: Date, mail: String, phone: String, addr: String, website: Option[String], introduction: Option[String], img: String)

case class BasicInfo(cid: Int, open_status: String, paid_capital: Int, social_credit_code: String, taxpayer_identification_number: String, registration_number: String, organization_code: String, type_of_company: String, industry: String, date_of_approval: Date, registration_authority: String, region: String, english_name: String, name_used_before: String, operation_mode: String, personnel_scale: String, time_limit_for_business: String, enterprise_address: String, scope_of_operation: String)

case class ShareholderInformation(id: Int, name: String, associate_count: Int, shareholding_ratio: Double, subscribed_capital_contribution: Double, date_of_subscription: Option[String], type_of_shareholder: String)

case class OutboundInvestment(id: Int, invested_enterprise: String, investment_representative: String, registered_capital: Option[String], contribution: Option[Double], date_of_establishment: Date, status: String)

case class Branch(id: Int, name: String)

case class ChangeRecord(id: Int, change_date: Date, change_project: String, before_change: String, after_change: String)

case class MainPersonnel(id: Int, name: String, associate_count: Int, desc: String)

case class CompanyProfile(id: Int, profile: String)

case class Boss(name: String, associate_count: Int, avator: String)

case class InterestedPeople(name: String, img: String, desc: String)

case class Tree(name: String, children: Option[List[Tree]], value: Option[Double])

case class ColorTree(name: String, children: Option[List[ColorTree]], value: Option[Double], symbolSize: List[Int], symbol: String = "rectangle")

case class EnterpriseGraph(one_name: String, two_name: String, three_name: String)

case class InvestmentGraph(q_name: Option[String], value_a: Option[Double], w_name: Option[String], value_b: Option[Double], e_name: Option[String])

case class InvestmentBoss(id: Int, name: String, value: Option[Double])

case class Association(id: Int, bid: Int, value: String, name_a: String, name_b: String, kind_a: Int, kind_b: Int)

case class Links(source: Int, target: Int, value: String)

case class Nodes(id: Int, name: String, category: Int)


// 调试用
case class Property(keyNo: String, name: String)

case class TempNode(id: String, labels: Array[String], properties: Property)

case class NodeResult(id: String, keyNo: String, name: String, category: Int)

case class Property2(role: Option[String])

case class TempLink(startNode: String, endNode: String, `type`: String, properties: Property2)

case class LinkResult(source: Int, target: Int, relation: String)
