package domain.propertygenerator

import domain.info.{DayPreference, NurseName}
import org.scalacheck.Gen
import domain.DayOfWeek
import domain.propertygenerator.DayPreferenceGenerator.genDayPrefsPerDay

import scala.math.{max, min}

object DayPreferenceGenerator:

  def genMany(nurses: Seq[NurseName]): Gen[Seq[DayPreference]] =
    for
      allDaysOfWeek <- DayOfWeekGenerator.genAll
      dayPreferencesGen = allDaysOfWeek.map(dayOfWeek => genDayPrefsPerDay(dayOfWeek, nurses))
      dayPreferences <- Gen.sequence[Seq[Seq[DayPreference]], Seq[DayPreference]](dayPreferencesGen)
    yield dayPreferences.flatten

  def genDayPrefsPerDay(dayOfWeek: DayOfWeek, nurses: Seq[NurseName]): Gen[Seq[DayPreference]] =
    for
      number <- Gen.chooseNum(DayOfWeek.minDayOfWeek, DayOfWeek.maxDayOfWeek)
      pickedNurses <- Gen.pick(min(number, nurses.size), nurses)
      dayPreferencesGen = pickedNurses.map(nurse => gen(nurse, dayOfWeek))
      dayPreferences <- Gen.sequence[Seq[DayPreference], DayPreference](dayPreferencesGen)
    yield dayPreferences

  def gen(nurseName: NurseName, dayOfWeek: DayOfWeek): Gen[DayPreference] =
    for
      scale <- ScaleGenerator.gen
    yield DayPreference(nurseName, dayOfWeek, scale)