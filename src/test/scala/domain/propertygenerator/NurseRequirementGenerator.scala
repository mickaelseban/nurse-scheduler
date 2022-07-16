package domain.propertygenerator

import domain.PositiveNumber
import domain.info.{NurseRequirement, NurseRole, Nurse}
import org.scalacheck.{Gen, Properties}

object NurseRequirementGenerator extends Properties("NurseRequirement"):

  def genMany(nurses: Seq[Nurse]) : Gen[Seq[NurseRequirement]] =
    val nurseRoles = nurses.flatMap(nurse => nurse.nurseRoles).distinct
    val gensNurseRequirements = nurseRoles.map(nurseRole => gen(nurseRole, nurses))
    for
      nurseRequirements <- Gen.sequence[Seq[NurseRequirement], NurseRequirement](gensNurseRequirements)
    yield nurseRequirements

  private def gen(nurseRole: NurseRole, nurses: Seq[Nurse]): Gen[NurseRequirement] =
    for
      number <- PositiveNumberGenerator.gen(nurses.count(n => n.nurseRoles.contains(nurseRole)))
    yield NurseRequirement(nurseRole, number)

