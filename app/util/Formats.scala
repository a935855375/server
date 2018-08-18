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

  implicit lazy val boss_history_investmentFormat: OFormat[Tables.BossHistoryInvestmentRow] = derived.oformat()
  implicit lazy val boss_history_positionFormat: OFormat[Tables.BossHistoryPositionRow] = derived.oformat()
  implicit lazy val boss_history_representFormat: OFormat[Tables.BossHistoryRepresentRow] = derived.oformat()
  implicit lazy val boss_holding_companyFormat: OFormat[Tables.BossHoldingCompanyRow] = derived.oformat()
  implicit lazy val boss_investmentFormat: OFormat[Tables.BossInvestmentRow] = derived.oformat()
  implicit lazy val boss_positionFormat: OFormat[Tables.BossPositionRow] = derived.oformat()
  implicit lazy val boss_representFormat: OFormat[Tables.BossRepresentRow] = derived.oformat()
}
