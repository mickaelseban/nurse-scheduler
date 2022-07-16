package domain.propertygenerator

import domain.NonEmptyString
import domain.info.ScaleSeniority
import domain.info.NurseRole
import domain.info.Nurse
import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object NurseGenerator extends Properties("Nurse"):

  def gen: Gen[Nurse] =
    for
      name <- NurseNameGenerator.gen
      seniority <- ScaleSeniorityGenerator.gen
      nurseRoles <- NurseRoleGenerator.genMany
    yield Nurse(name, seniority, nurseRoles)

  def genMany: Gen[Seq[Nurse]] =
    for
      randomNumberOfNurses <- Gen.chooseNum( 1, 1)
      nurses <- Gen.containerOfN[Seq, Nurse](randomNumberOfNurses, gen)
    yield nurses

  property("NurseGenerator should generates") = forAll(gen) (n => {
    !n.nurseRoles.isEmpty
  })