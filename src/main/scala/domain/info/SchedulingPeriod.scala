package domain.info

import domain.Result
import domain.NonEmptyString
import domain.Time
import xml.XML.*

import scala.xml.Node

final case class SchedulingPeriod(schedulingPeriodId: NonEmptyString, start: Time, end: Time,
                                  nurseRequirements: Seq[NurseRequirement])

object SchedulingPeriod:
  def from(nurseRoles: Seq[NurseRole])(xml: Node): Result[SchedulingPeriod] =
    for
      idString <- fromAttribute(xml, "id")
      id <- NonEmptyString.from(idString)
      startString <- fromAttribute(xml, "start")
      start <- Time.from(startString)
      endString <- fromAttribute(xml, "end")
      end <- Time.from(endString)
      nurseRequirements <- traverse(xml \\ "nurseRequirement", NurseRequirement.from(nurseRoles))
    yield SchedulingPeriod(id, start, end, nurseRequirements)
