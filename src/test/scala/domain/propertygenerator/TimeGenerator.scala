package domain.propertygenerator

import domain.Time
import org.scalacheck.{Gen, Properties}
import java.time.LocalTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object TimeGenerator extends Properties("Time"):

  def gen: Gen[Time] =
    for
      random <- Gen.choose(LocalTime.MIN, LocalTime.MAX)
      formattedLocalTimeAsString = random.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
      time <- Time.from(formattedLocalTimeAsString).fold(_ => Gen.fail, x => Gen.const(x))
    yield time
