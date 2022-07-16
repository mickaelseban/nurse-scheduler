package domain.schedule


import scala.xml.Elem

final case class ShiftSchedule(days: Seq[Day])

object ShiftSchedule:
  def toXML(shiftSchedule: ShiftSchedule) : Elem =
    <shiftSchedule xmlns="http://www.dei.isep.ipp.pt/tap-2022" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.dei.isep.ipp.pt/tap-2022 ../../schedule.xsd ">
      {shiftSchedule.days.map(day => Day.toXML(day))}
    </shiftSchedule>
