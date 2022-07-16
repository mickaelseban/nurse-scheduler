package domain.propertygenerator

import domain.info.Preferences
import domain.info.NurseName
import domain.NonEmptyString
import org.scalacheck.{Gen, Properties}

object PreferencesGenerator extends Properties("Preferences"):

  def gen(nurseNames: Seq[NurseName], periods: Seq[NonEmptyString]): Gen[Preferences] =
    for
      periods <- PeriodPreferenceGenerator.genPeriodPreferences(nurseNames, periods)
      days <- DayPreferenceGenerator.genMany(nurseNames)
    yield Preferences(periods, days)