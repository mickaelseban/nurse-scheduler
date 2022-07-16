package domain.propertygenerator

import domain.info.ScheduleInfo
import domain.propertygenerator.TimeGenerator.property
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}
import domain.schedule.ShiftScheduleFactory
import domain.DayOfWeek

import scala.math.{max, min}

object ScheduleInfoGenerator extends Properties("ScheduleInfoGenerator"):

  def gen: Gen[ScheduleInfo] =
    for
      constraints <- ConstraintsGenerator.gen
      nurses <- NurseGenerator.genMany
      schedulingPeriods <- SchedulingPeriodGenerator.genMany(nurses)
      preferences <- PreferencesGenerator.gen(nurses.map(n => n.name), schedulingPeriods.map(schedulingPeriod => schedulingPeriod.schedulingPeriodId).distinct)
    yield ScheduleInfo(schedulingPeriods, nurses, constraints, preferences)

  property("Nurse have at least a role") = forAll(gen)(x =>  x.nurses.filter(nurse => nurse.nurseRoles.isEmpty).isEmpty)
  
  property("Nurse have distinct roles") = forAll(gen)(x => x.nurses.forall(nurse => nurse.nurseRoles.size == nurse.nurseRoles.distinct.size))
 
  property("Scheduling Period list should not be empty") = forAll(gen)(x =>  !x.schedulingPeriods.isEmpty)
  
  property("Nurse Requirement list of a Scheduling Period should not be empty") = forAll(gen) (x => 
    x.schedulingPeriods.filter(schedulingPeriod => schedulingPeriod.nurseRequirements.isEmpty).isEmpty)
  
  property("Scheduling Periods list of Nurse Requirement are distinct") = forAll(gen) (x => 
    x.schedulingPeriods.forall(schedulingPeriod => schedulingPeriod.nurseRequirements.size == schedulingPeriod.nurseRequirements.distinct.size)
  )
  
  property("Nurse Requirement have a valid nurse roles") = forAll(gen) (x =>
    x.schedulingPeriods.forall(schedulingPeriod => schedulingPeriod.nurseRequirements
      .forall(nurseRequirement => x.nurses.map(nurse => nurse.nurseRoles).flatten
        .contains(nurseRequirement.roleType))))
  
  property("Preference Days have valid Nurses") = forAll(gen) (x => x.preferences.days.forall(day => x.nurses.map(x => x.name).contains(day.nurseName)))

  property("Preference Periods have valid Nurses") = forAll(gen) (x => x.preferences.periods.forall(period => x.nurses.map(x => x.name).contains(period.nurseName)))

  property("Preference Periods have valid Periods") = forAll(gen) (x => x.preferences.periods
    .forall(periodPreference => x.schedulingPeriods.map(schedulingPeriod => schedulingPeriod.schedulingPeriodId)
      .contains(periodPreference.period)))

  property("Nurse are not assigned to a shift more than one time") = forAll(gen) (scheduleInfo =>
    ShiftScheduleFactory.from(scheduleInfo).fold(_ => true, shiftSchedule => shiftSchedule.days.forall(day =>
      day.shifts.forall(shift => shift.nursesShift.map(nurseShift => nurseShift.nurse).size == shift.nursesShift.map(sn => sn.nurse).distinct.size))))

  property("Shift Schedule have the max days of week") = forAll(gen) (scheduleInfo =>
    ShiftScheduleFactory.from(scheduleInfo).fold(_ => true, shiftSchedule => shiftSchedule.days.size == DayOfWeek.maxDayOfWeek))

  property("Scheduling Periods have the nurse roles existent in nurse list") = forAll(gen) (scheduleInfo =>
    ShiftScheduleFactory.from(scheduleInfo).fold(_ => true, shiftSchedule => {
      scheduleInfo.nurses.map(p => p.nurseRoles).contains(scheduleInfo.schedulingPeriods.head.nurseRequirements.map(y => y.roleType).distinct)
    }))
