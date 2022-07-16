package domain.info

import domain.Result
import domain.DomainError.EmptyName

import scala.annotation.targetName

opaque type NurseName = String
object NurseName:
  def from(input: String): Result[NurseName] =
    !input.trim.isEmpty match
      case true => Right(input)
      case false => Left(EmptyName)
  extension (input: NurseName)
    def value: String = input