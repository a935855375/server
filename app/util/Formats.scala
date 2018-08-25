package util

import julienrf.json.derived
import models.Entities._
import models.Tables
import play.api.libs.json.{Json, OFormat}

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

  implicit lazy val ChildrenFormat: OFormat[Children] = Json.format[Children]
  implicit lazy val UserBeanFormat: OFormat[UserBean] = Json.format[UserBean]
  implicit lazy val RegisterInputFormat: OFormat[RegisterInput] = Json.format[RegisterInput]
  implicit lazy val CommonResFormat: OFormat[CommonRes] = Json.format[CommonRes]
  implicit lazy val LoginResFormat: OFormat[LoginRes] = Json.format[LoginRes]
  implicit lazy val TokenFormat: OFormat[Token] = Json.format[Token]
  implicit lazy val InterestedPeopleFormat: OFormat[Tables.InterestedPeopleRow] = derived.oformat()

  implicit lazy val PropertyFormat: OFormat[Property] = Json.format[Property]
  implicit lazy val TempNodeFormat: OFormat[TempNode] = Json.format[TempNode]
  implicit lazy val NodeResultFormat: OFormat[NodeResult] = Json.format[NodeResult]

  implicit lazy val Property2Format: OFormat[Property2] = Json.format[Property2]
  implicit lazy val TempLinkFormat: OFormat[TempLink] = Json.format[TempLink]
  implicit lazy val LinkResultFormat: OFormat[LinkResult] = Json.format[LinkResult]

  implicit lazy val RefereeFormat: OFormat[Tables.RefereeRow] = derived.oformat()
  implicit lazy val CourtNoticeFormat: OFormat[Tables.CourtNoticeRow] = derived.oformat()
  implicit lazy val OpeningNoticeFormat: OFormat[Tables.OpeningNoticeRow] = derived.oformat()

  implicit lazy val AdministrativeLicenseIcRowFormat: OFormat[Tables.AdministrativeLicenseIcRow] = derived.oformat()
  implicit lazy val AdministrativeLicenseChRowFormat: OFormat[Tables.AdministrativeLicenseChRow] = derived.oformat()
  implicit lazy val TaxCreditRowFormat: OFormat[Tables.TaxCreditRow] = derived.oformat()
  implicit lazy val ProductInformationRowFormat: OFormat[Tables.ProductInformationRow] = derived.oformat()
  implicit lazy val FinancingInformationRowFormat: OFormat[Tables.FinancingInformationRow] = derived.oformat()
  implicit lazy val BiddingInformationRowFormat: OFormat[Tables.BiddingInformationRow] = derived.oformat()
  implicit lazy val RecruitmentRowFormat: OFormat[Tables.RecruitmentRow] = derived.oformat()
  implicit lazy val PublicNumberRowFormat: OFormat[Tables.PublicNumberRow] = derived.oformat()
  implicit lazy val NewsLyricsRowFormat: OFormat[Tables.NewsLyricsRow] = derived.oformat()

  implicit lazy val NewsRowFormat: OFormat[Tables.NewsRow] = derived.oformat()
  implicit lazy val NewsBodyFormat: OFormat[Tables.NewsBodyRow] = derived.oformat()

  implicit lazy val BrandRowFormat: OFormat[Tables.BrandRow] = derived.oformat()
  implicit lazy val BrandBodyRowFormat: OFormat[Tables.BrandBodyRow] = derived.oformat()

  implicit lazy val LoseCreditRowFormat: OFormat[Tables.LoseCreditRow] = derived.oformat()
  implicit lazy val LoseCreditBodyRowFormat: OFormat[Tables.LoseCreditBodyRow] = derived.oformat()
}
