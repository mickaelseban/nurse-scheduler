package domain.info

import domain.Result
import xml.XML.{fromAttribute, traverse}

import domain.DayOfWeek
import scala.xml.Node

final case class DayPreference(nurseName: NurseName, day: DayOfWeek, value: Scale)

object DayPreference:
  def from(xml: Node) : Result[DayPreference] =
    for {
      nurseNameString <- fromAttribute(xml, "nurse")
      nurseName <- NurseName.from(nurseNameString)
      dayString <- fromAttribute(xml, "day")
      day <- DayOfWeek.from(dayString)
      valueAsString <- fromAttribute(xml, "value")
      value <- Scale.from(valueAsString)
    } yield DayPreference(nurseName, day, value)

