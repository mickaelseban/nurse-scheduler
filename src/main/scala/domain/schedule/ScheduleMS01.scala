package domain.schedule

import scala.xml.Elem
import domain.info.ScheduleInfo
import domain.Result

object ScheduleMS01 extends Schedule:
  def create(xml: Elem): Result[Elem] =
    for
      scheduleInfo <- ScheduleInfo.from(xml)
      shiftSchedule <- ShiftScheduleFactory.from(scheduleInfo)
    yield ShiftSchedule.toXML(shiftSchedule)

