package domain

import domain.DomainError.InvalidDayOfWeek

import scala.annotation.targetName
import scala.util.Try

opaque type DayOfWeek = Int

object DayOfWeek:

  def minDayOfWeek : Int = 1
  def maxDayOfWeek : Int = 7

  def from(input: String): Result[DayOfWeek] =
    Try(input.toInt).fold[Result[DayOfWeek]](_ => Left(InvalidDayOfWeek(input)), inputAsInt => from(inputAsInt))

  def from(input: Int): Result[DayOfWeek] =
    input >= minDayOfWeek && input <= maxDayOfWeek match
      case true => Right(input)
      case false => Left(InvalidDayOfWeek(input.toString))
      
  def all: Result[Seq[DayOfWeek]] =
    for
        monday <- DayOfWeek.from(1)
        tuesday <- DayOfWeek.from(2)
        wednesday <- DayOfWeek.from(3)
        thursday <- DayOfWeek.from(4)
        friday <- DayOfWeek.from(5)
        saturday <- DayOfWeek.from(6)
        sunday <- DayOfWeek.from(7)
    yield Seq[DayOfWeek](monday, tuesday, wednesday, thursday, friday, saturday, sunday)
    
  extension (input: DayOfWeek)
    def value: Int = input
    def toString: String = input.value.toString