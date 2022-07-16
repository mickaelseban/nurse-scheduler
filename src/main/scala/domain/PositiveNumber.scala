package domain

import domain.DomainError.InvalidPositiveNumber

import scala.annotation.targetName
import scala.util.Try

opaque type PositiveNumber = Int
object PositiveNumber:
  def from(input: String): Result[PositiveNumber] =
    Try(input.toInt).fold[Result[PositiveNumber]](_ => Left(InvalidPositiveNumber(input)), inputAsInt => from(inputAsInt))
  def from(input: Int): Result[PositiveNumber] =
    input > 0 match
      case true => Right(input)
      case false => Left(InvalidPositiveNumber(input.toString))
  extension (input: PositiveNumber)
    def value: Int = input