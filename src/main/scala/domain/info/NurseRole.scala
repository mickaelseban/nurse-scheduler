package domain.info

import domain.Result
import domain.DomainError.UnknownRequirementRole

import scala.util.Try

opaque type NurseRole = String
object NurseRole:
  def from(input: String): Result[NurseRole] =
    val rx = "[A-Z]{1}".r
    rx.matches(input) match
      case true => Right(input)
      case false => Left(UnknownRequirementRole(input))
  extension (input: NurseRole)
    def value: String = input