package domain

import domain.DomainError.EmptyString

import scala.annotation.targetName

opaque type NonEmptyString = String
object NonEmptyString:
  def from(input: String): Result[NonEmptyString] =
    !input.isEmpty match
      case true => Right(input)
      case false => Left(EmptyString)
  extension(input: NonEmptyString)
    def value: String = input