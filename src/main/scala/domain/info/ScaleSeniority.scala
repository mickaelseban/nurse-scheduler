package domain.info

import domain.Result
import domain.DomainError.SeniorityValueOutOfRange

import scala.annotation.targetName
import scala.util.Try

opaque type ScaleSeniority = Int
object ScaleSeniority:
  def from(input: String): Result[ScaleSeniority] =
    Try(input.toInt).fold[Result[ScaleSeniority]](_ => Left(SeniorityValueOutOfRange(0)), inputAsInt => from(inputAsInt))
  def from(inputAsInt: Int): Result[ScaleSeniority] = inputAsInt >= 1 && inputAsInt <= 5 match
    case true => Right(inputAsInt)
    case false => Left(SeniorityValueOutOfRange(inputAsInt))
  extension (input: ScaleSeniority)
    def value: Int = input