import domain.*
import domain.info.NurseRole
import domain.propertygenerator.{ConstraintsGenerator, DayOfWeekGenerator, NurseRoleGenerator, TimeGenerator}
import org.scalacheck.Gen

import java.time.LocalTime
import java.time.format.DateTimeFormatter

val s = domain.propertygenerator.ScheduleInfoGenerator.gen.sample.get
val ssd = domain.propertygenerator.NurseNameGenerator.gen.sample.get