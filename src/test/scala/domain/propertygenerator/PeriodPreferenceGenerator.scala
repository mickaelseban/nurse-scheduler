package domain.propertygenerator

import domain.{NonEmptyString, DayOfWeek}
import domain.info.{Nurse, NurseName, PeriodPreference}
import org.scalacheck.Gen
import scala.math.{max, min}

object PeriodPreferenceGenerator:

  private def genPeriodsList(periods: Seq[NonEmptyString]): Gen[Seq[NonEmptyString]] =
    for
      num <- Gen.chooseNum(0, periods.size);
      genPeriod <- Gen.pick(num, periods)
    yield genPeriod.toList

  private def genPeriodPreferencePerDay(period: NonEmptyString, nurseName: NurseName): Gen[PeriodPreference] =
    for
      scale <- ScaleGenerator.gen
    yield PeriodPreference(nurseName, period, scale)

  private def genPeriodPrefsPerDay(period: NonEmptyString, nurses: Seq[NurseName]): Gen[Seq[PeriodPreference]] =
    for
      number <- Gen.chooseNum(DayOfWeek.minDayOfWeek, DayOfWeek.maxDayOfWeek)
      pickedNurses <- Gen.pick(min(number, nurses.size), nurses)
      periodPreferencesGen = pickedNurses.map(nurse => genPeriodPreferencePerDay(period, nurse))
      periodPreferences <- Gen.sequence[Seq[PeriodPreference], PeriodPreference](periodPreferencesGen)
    yield periodPreferences

  def genPeriodPreferences(nurseNames: Seq[NurseName], periods: Seq[NonEmptyString]): Gen[Seq[PeriodPreference]] =
    for
      periodGen <- genPeriodsList(periods)
      periodPreferencesGen = periodGen.map(period => genPeriodPrefsPerDay(period, nurseNames))
      periodPreferences <- Gen.sequence[Seq[Seq[PeriodPreference]], Seq[PeriodPreference]](periodPreferencesGen)
    yield periodPreferences.flatten