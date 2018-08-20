package models

import play.api.mvc.{Request, WrappedRequest}

object Entities {

  case class Children(name: String, value: Option[String], children: Option[Seq[Children]])

  case class Property(keyNo: String, name: String)

  case class TempNode(id: String, labels: Array[String], properties: Property)

  case class NodeResult(id: String, keyNo: String, name: String, category: Int)

  case class Property2(role: Option[String])

  case class TempLink(startNode: String, endNode: String, `type`: String, properties: Property2)

  case class LinkResult(source: Int, target: Int, relation: String)

  case class Token(subject: String, expiresIn: Int)

  case class User(id: Int, username: String, password: String, nickname: Option[String])

  case class UserBean(username: String, password: String)

  case class RegisterInput(username: String, mail: String, password: String)

  case class LoginRes(status: Int, id_token: Option[String], expiresIn: Option[Int], nickname: Option[String])

  case class CommonRes(status: Int, reason: String)

  class UserRequest[A](val username: String, request: Request[A]) extends WrappedRequest[A](request)

}