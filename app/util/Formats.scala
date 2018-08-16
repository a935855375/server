package util

import julienrf.json.derived
import models.Tables
import play.api.libs.json.OFormat

object Formats {
  implicit lazy val CompanyFormat: OFormat[Tables.CompanyRow] = derived.oformat()
}
