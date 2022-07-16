package domain.schedule

import domain.DomainError.NotEnoughNurses
import domain.info.{Nurse, NurseRequirement, ScheduleInfo, SchedulingPeriod, NurseRole}
import domain.*

import scala.util.Try

object ShiftScheduleFactory:

  def from(scheduleInfo: ScheduleInfo): Result[ShiftSchedule] =
    for
      daysOfWeek <- DayOfWeek.all
      days <- createDays(scheduleInfo, daysOfWeek)
    yield ShiftSchedule(days)

  private def createDays(scheduleInfo: ScheduleInfo, daysOfWeek: Seq[DayOfWeek]): Result[Seq[Day]] =
    daysOfWeek.foldLeft[Result[Seq[Day]]](Right(Seq()))(
      { case (daysResult, dayNumber) =>
        val currentDaysResult =
          for {
            days <- daysResult
            maxDaysOfWeek <- calculateMaxDaysOfWeek(daysOfWeek, scheduleInfo.constraints.minRestDaysPerWeek)
            //filteredNurses <- filterMinRestDaysPerWeek(maxDaysOfWeek, days, scheduleInfo.nurses)
            shifts <- createShifts(scheduleInfo.schedulingPeriods, scheduleInfo.nurses, scheduleInfo.constraints.maxShiftsPerDay)
          } yield days ++ Seq(Day(dayNumber, shifts))
        currentDaysResult
      }
    )

  private def createShifts(schedulingPeriods: Seq[SchedulingPeriod], nurses: Seq[Nurse], maxShiftsPerDay: PositiveNumber): Result[Seq[Shift]] =
    schedulingPeriods.foldLeft[Result[Seq[Shift]]](Right(Seq()))(
      { case (shiftsResult, schedulingPeriod) =>
        val shiftResult =
          for {
            shifts <- shiftsResult
            filteredNurses <- filterMaxShiftsPerDay(shifts, nurses, maxShiftsPerDay)
            nurseShiftList <- handleNursesPerShift(schedulingPeriod.nurseRequirements, filteredNurses)
          } yield Shift(schedulingPeriod.schedulingPeriodId, nurseShiftList.sortBy(nurseShift => nurseShift.nurse.name.value))
        val shiftsWithLastShiftResult =
          for {
            shifts <- shiftsResult
            shift <- shiftResult
          } yield shifts ++ Seq(shift)
        shiftsWithLastShiftResult
      }
    )

  private def handleNursesPerShift(nurseRequirements: Seq[NurseRequirement], nursesList: Seq[Nurse]): Result[Seq[NurseShift]] =
    flatRequirements(nurseRequirements).foldLeft[Result[(Seq[NurseShift], Seq[Nurse])]](Right(Seq(), nursesList))(
      { case (result, nurseRequirement) =>
        for {
          (nurseShifts, availableNurses) <- result
          (nurseShift, availableNursesUpdated) <- assignNurseToShift(nurseRequirement, availableNurses)
          nursesShiftUpdated = nurseShifts ++ Seq(nurseShift)
        } yield (nursesShiftUpdated, availableNursesUpdated)
      }
    ).map((nursesShift, _) => nursesShift)

  private def assignNurseToShift(nurseRequirement: NurseRequirement, nursesList: Seq[Nurse]): Result[(NurseShift, Seq[Nurse])] =
    for {
      nurse <- findNurseByType(nurseRequirement.roleType, nursesList)
      currentAvailableNurses <- removeNurseFromAvailableNurses(nurse, nursesList)
    } yield (NurseShift(nurse, nurseRequirement.roleType), currentAvailableNurses)

  private def flatRequirements(nurseRequirements: Seq[NurseRequirement]): Seq[NurseRequirement] =
    nurseRequirements.flatMap(nurseRequirement => (1 to nurseRequirement.number.value)
      .map(_ => nurseRequirement))

  private def removeNurseFromAvailableNurses(nurse: Nurse, nurses: Seq[Nurse]): Result[Seq[Nurse]] =
    Try(nurses.filter(nurseFromList => !nurseFromList.equals(nurse)))
      .fold[Result[Seq[Nurse]]](_ => Left(NotEnoughNurses), nurseRight => Right(nurseRight))

  private def findNurseByType(nurseRoleType: NurseRole, nurses: Seq[Nurse]): Result[Nurse] =
    Try(nurses.find(nurse => nurse.nurseRoles
      .contains(nurseRoleType)).get)
      .fold[Result[Nurse]](_ => Left(NotEnoughNurses), nursesRight => Right(nursesRight))

  private def filterMaxShiftsPerDay(dayShifts: Seq[Shift], nurses: Seq[Nurse], maxShiftsPerDay: PositiveNumber): Result[Seq[Nurse]] =
    Try(nurses.filter(nurse => dayShifts
      .flatMap(shift => shift.nursesShift)
      .filter(nurseShift => nurseShift.nurse == nurse).size < maxShiftsPerDay.value))
      .fold[Result[Seq[Nurse]]](_ => Left(NotEnoughNurses), nursesRight => Right(nursesRight))

  private def calculateMaxDaysOfWeek(daysOfWeek: Seq[DayOfWeek], minRestDaysPerWeek: PositiveNumber): Result[PositiveNumber] =
    PositiveNumber.from(daysOfWeek.size - minRestDaysPerWeek.value)

  private def filterMinRestDaysPerWeek(maxDaysOfWeek: PositiveNumber, days: Seq[Day], nurse: Nurse): Result[Nurse] =
    days.filter(day => day.shifts
      .flatMap(shift => shift.nursesShift)
      .exists(nurseShift => nurseShift.nurse == nurse)).size <= maxDaysOfWeek.value match
      case true => Right(nurse)
      case false => Left(NotEnoughNurses)

  private def filterMinRestDaysPerWeek(maxDaysOfWeek: PositiveNumber, days: Seq[Day], nurses: Seq[Nurse]): Result[Seq[Nurse]] =
    nurses.foldLeft[Result[Seq[Nurse]]](Right(List()))(
      { case (nursesResult, nurse) =>
        for
          nurses <- nursesResult
          filteredNurse <- filterMinRestDaysPerWeek(maxDaysOfWeek, days, nurse)
        yield nurses ++ Seq(filteredNurse)
      }
    )
