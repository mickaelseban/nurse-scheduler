package domain.schedule

import domain.info.Nurse
import domain.info.NurseRole

import scala.xml.Elem

final case class NurseShift(nurse: Nurse, role: NurseRole)

object NurseShift:
  def toXML(nurseShift: NurseShift) : Elem =
    <nurse name={nurseShift.nurse.name.value} role={nurseShift.role.value}/>