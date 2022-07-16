package domain.info

import domain.DomainError.*
import domain.Result
import xml.XML.*

import scala.xml.{Elem, Node}

final case class ScheduleInfo(schedulingPeriods: Seq[SchedulingPeriod], nurses: Seq[Nurse], constraints: Constraints, preferences: Preferences)
object ScheduleInfo:
  def from(xml: Elem): Result[ScheduleInfo] =
    for
      nurses <- traverse((xml \\ "nurse"), Nurse.from)
      schedulingPeriods <- traverse((xml \\ "schedulingPeriod"), SchedulingPeriod.from(nurses.flatMap(nurse => nurse.nurseRoles)))
      constraintsNode <- fromNode(xml, "constraints")
      constraints <- Constraints.from(constraintsNode)
      preferencesNode <- fromNode(xml, "preferences")
      preferences <- Preferences.from(preferencesNode)
    yield ScheduleInfo(schedulingPeriods, nurses, constraints, preferences)