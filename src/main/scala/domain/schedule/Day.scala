package domain.schedule

import domain.Result
import domain.DayOfWeek

import scala.xml.Elem

final case class Day(dayOfWeek: DayOfWeek, shifts: Seq[Shift])

object Day:

  def toXML(day: Day) : Elem =
    <day id={day.dayOfWeek.toString}>
      {day.shifts.map(shift => Shift.toXML(shift))}
    </day>