package domain.info

import domain.Result
import domain.PositiveNumber
import xml.XML.fromAttribute
import scala.xml.Node

final case class Constraints(minRestDaysPerWeek : PositiveNumber, maxShiftsPerDay: PositiveNumber)

object Constraints:
  def from(xml: Node) : Result[Constraints] =
    for
      stringMinRestDaysPerWeek  <- fromAttribute(xml, "minRestDaysPerWeek")
      minRestDaysPerWeek <- PositiveNumber.from(stringMinRestDaysPerWeek)
      stringMaxShiftsPerDay  <- fromAttribute(xml, "maxShiftsPerDay")
      maxShiftsPerDay <- PositiveNumber.from(stringMaxShiftsPerDay)
    yield Constraints(minRestDaysPerWeek, maxShiftsPerDay )