package domain.info

import domain.Result
import xml.XML.{fromAttribute, traverse}
import scala.xml.Node

final case class Nurse(name: NurseName, seniority: ScaleSeniority, nurseRoles: Seq[NurseRole])

object Nurse:
  def from(xml: Node) : Result[Nurse] =
    for
      stringName <- fromAttribute(xml, "name")
      name <- NurseName.from(stringName)
      stringSeniority <- fromAttribute(xml, "seniority")
      seniority <- ScaleSeniority.from(stringSeniority)
      nurseRoles <- traverse((xml \\ "nurseRole"), createNurseRoleType)
    yield Nurse(name, seniority , nurseRoles)

  private def createNurseRoleType(xml: Node) : Result[NurseRole] =
      for
        roleAsString <- fromAttribute(xml, "role")
        role <- NurseRole.from(roleAsString)
      yield role
