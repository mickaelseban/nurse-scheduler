package domain

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import scala.annotation.targetName
import scala.util.Try
import domain.DomainError.InvalidTime
import domain.Result

opaque type Time = LocalTime

object Time:

  def from(input: String): Result[Time] =
    Try(LocalTime.parse(input, DateTimeFormatter.ofPattern("HH:mm:ss"))).toOption
      .fold(Left(InvalidTime(input)))(resultTime => Right(resultTime))