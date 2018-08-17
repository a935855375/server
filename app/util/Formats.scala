package util

import julienrf.json.derived
import models.Tables
import play.api.libs.json.OFormat

object Formats {
  implicit lazy val CompanyFormat: OFormat[Tables.CompanyRow] = derived.oformat()
  implicit lazy val MainPersonnelFormat: OFormat[Tables.MainPersonnelRow] = derived.oformat()
  implicit lazy val OutboundInvestmentFormat: OFormat[Tables.OutboundInvestmentRow] = derived.oformat()
  implicit lazy val ChangeRecordFormat: OFormat[Tables.ChangeRecordRow] = derived.oformat()
  implicit lazy val BranchFormat: OFormat[Tables.BranchRow] = derived.oformat()
  implicit lazy val ShareholderInformationFormat: OFormat[Tables.ShareholderInformationRow] = derived.oformat()
  implicit lazy val BasicInfoFormat: OFormat[Tables.BasicInfoRow] = derived.oformat()
  implicit lazy val BossInfoFormat: OFormat[Tables.PersonRow] = derived.oformat()
}
