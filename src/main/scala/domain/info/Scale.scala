package domain.info

import domain.Result
import domain.DomainError.PreferenceValueOutOfRange

import scala.util.Try

opaque type Scale = Int
object Scale:
  def from(input: String): Result[Scale] =
    Try(input.toInt).fold[Result[Scale]](_ => Left(PreferenceValueOutOfRange(0)), inputAsInt => from(inputAsInt))
  def from(inputAsInt: Int): Result[Scale] = inputAsInt >= -2 && inputAsInt <= 2 match
    case true => Right(inputAsInt)
    case false => Left(PreferenceValueOutOfRange(inputAsInt))
  extension (input: Scale)
    def value: Int = input