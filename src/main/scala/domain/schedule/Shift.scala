package domain.schedule

import domain.NonEmptyString

import scala.xml.Elem

final case class Shift(id: NonEmptyString, nursesShift: Seq[NurseShift])

object Shift:
  def toXML(shift: Shift) : Elem =
   <shift id={shift.id.value}>
      {shift.nursesShift.map(nurseShift => NurseShift.toXML(nurseShift))}
   </shift>