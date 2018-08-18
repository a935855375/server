package models

case class Children(name: String, value: Option[String], children: Option[Seq[Children]])
