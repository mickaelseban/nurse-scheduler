package domain.info

import domain.Result
import xml.XML.{fromAttribute, traverse}

import scala.xml.Node

final case class Preferences(periods: Seq[PeriodPreference], days: Seq[DayPreference])

object Preferences:
  def from(xml: Node) : Result[Preferences] =
    for
      periodPreference <- traverse((xml \\ "periodPreference"), PeriodPreference.from)
      dayPreference <- traverse(xml \\ "dayPreference", DayPreference.from)
    yield Preferences(periodPreference, dayPreference)