package domain.info

import domain.Result
import domain.NonEmptyString
import xml.XML.{fromAttribute, traverse}

import scala.xml.Node

final case class PeriodPreference(nurseName: NurseName, period: NonEmptyString, value: Scale)

object PeriodPreference:
  def from(xml: Node) : Result[PeriodPreference] =
    for {
      nurseNameString <- fromAttribute(xml, "nurse")
      nurseName <- NurseName.from(nurseNameString)
      periodAsString <- fromAttribute(xml, "period")
      period <- NonEmptyString.from(periodAsString)
      valueAsString <- fromAttribute(xml, "value")
      value <- Scale.from(valueAsString)
    } yield PeriodPreference(nurseName, period, value)
