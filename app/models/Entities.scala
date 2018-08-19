package models

case class Children(name: String, value: Option[String], children: Option[Seq[Children]])

case class Property(keyNo: String, name: String)

case class TempNode(id: String, labels: Array[String], properties: Property)

case class NodeResult(id: String, keyNo: String, name: String, category: Int)

case class Property2(role: Option[String])

case class TempLink(startNode: String, endNode: String, `type`: String, properties: Property2)

case class LinkResult(source: Int, target: Int, relation: String)