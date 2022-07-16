package domain.propertygenerator

import domain.info.{Nurse, NurseRequirement, NurseRole, SchedulingPeriod}
import domain.{NonEmptyString, PositiveNumber, Time}
import org.scalacheck.{Gen, Properties}

import scala.annotation.tailrec
import scala.math.{max, min}
import scala.util.Random

object SchedulingPeriodGenerator extends Properties("SchedulingPeriod"):

  def genMany(nurses: Seq[Nurse]) : Gen[Seq[SchedulingPeriod]] =
    for
      periods <- genAvailablePeriodsWithTime
      allNurseRequirements <- NurseRequirementGenerator.genMany(nurses)
      nurseRequirementsPerPeriod = splitNurseRequirements(allNurseRequirements, periods.size).filter(nr => !nr.isEmpty)
      schedulingPeriods = nurseRequirementsPerPeriod.zip(periods).map(x => SchedulingPeriod(x._2._1, x._2._2, x._2._3, x._1))
    yield schedulingPeriods

  private def genAvailablePeriodsWithTime: Gen[Seq[(NonEmptyString, Time, Time)]] =
    for
      randomNumber <- Gen.chooseNum(1, 3)
      availablePeriodsWithTime <- Gen.containerOfN[Seq, (NonEmptyString, Time, Time)](randomNumber, genPeriodAndTime)
    yield availablePeriodsWithTime

  private def genPeriodAndTime: Gen[(NonEmptyString, Time, Time)] =
    for
      schedulingPeriodId <- NonEmptyStringGenerator.gen
      start <- TimeGenerator.gen
      end <- TimeGenerator.gen
    yield (schedulingPeriodId, start, end)

  private def periodsWithTime(periodsWithTime: (NonEmptyString, Time, Time), nurseRequirements: Seq[NurseRequirement]): Gen[SchedulingPeriod] =
    for
      nurseRequirementsGen <- Gen.pick(min(1, nurseRequirements.size), nurseRequirements)
    yield SchedulingPeriod(periodsWithTime._1, periodsWithTime._2, periodsWithTime._3, nurseRequirementsGen.toList)

  private def splitNurseRequirements[NurseRequirement](nurseRequirements: Seq[NurseRequirement], periodsSize: Int): Seq[Seq[NurseRequirement]] =
    if (periodsSize == 0) Nil
    else if (periodsSize == 1) Seq(nurseRequirements)
    else {
      val avg = nurseRequirements.size / periodsSize
      val rand = (1.0 + Random.nextGaussian / 3) * avg
      val index = (rand.toInt max 1) min (nurseRequirements.size - periodsSize)
      val (h, t) = nurseRequirements splitAt index
      h +: splitNurseRequirements(t, periodsSize - 1)
    }