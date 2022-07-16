package domain.info

import domain.DomainError.UnknownRequirementRole
import domain.PositiveNumber
import domain.Result
import xml.XML.fromAttribute

import scala.xml.Node

final case class NurseRequirement(roleType: NurseRole, number: PositiveNumber)

object NurseRequirement:
  def from(nurseRoles: Seq[NurseRole])(xml: Node): Result[NurseRequirement] =
    for
      roleAsString <- fromAttribute(xml, "role")
      roleB <- NurseRole.from(roleAsString)
      role <- nurseRoles.find(role => role == roleB).fold(Left(UnknownRequirementRole(roleAsString)))(roleResult => Right(roleResult))
      numberString <- fromAttribute(xml, "number")
      number <- PositiveNumber.from(numberString)
    yield NurseRequirement(role, number)


