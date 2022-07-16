package domain.propertygenerator

import domain.DayOfWeek
import org.scalacheck.{Gen, Properties}

object DayOfWeekGenerator extends Properties("DayOfWeek"):

  def genAll: Gen[Seq[DayOfWeek]]  =
    for
      daysOfWeek <- DayOfWeek.all.fold(_ => Gen.fail, x => Gen.const(x))
    yield daysOfWeek
