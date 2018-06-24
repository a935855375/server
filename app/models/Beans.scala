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
}

class UserRequest[A](val username: String, request: Request[A]) extends WrappedRequest[A](request)

case class Token(subject: String, expiresIn: Int)

case class User(id: Int, username: String, password: String, nickname: Option[String])

case class UserBean(username: String, password: String)

case class RegisterInput(username: String, mail: String, password: String)

case class LoginRes(status: Int, id_token: Option[String], expiresIn: Option[Int], nickname: Option[String])

case class CommonRes(status: Int, reason: String)

case class Company(id: Int, name: String, represent: String, capital: Int, found_time: Date, mail: String, phone: String, addr: String, website: String, introduction: String)

