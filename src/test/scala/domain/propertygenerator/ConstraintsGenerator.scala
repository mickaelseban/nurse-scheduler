package domain.propertygenerator

import domain.info.Constraints
import domain.DayOfWeek
import org.scalacheck.{Gen, Properties}

object ConstraintsGenerator extends Properties("Constraints"):

  def gen: Gen[Constraints] =
    for
      minRestDaysWeek <- PositiveNumberGenerator.gen(DayOfWeek.minDayOfWeek)
      maxShiftsPerDay <- PositiveNumberGenerator.gen(1)
    yield Constraints(minRestDaysWeek, maxShiftsPerDay)