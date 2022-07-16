package domain.propertygenerator

import domain.info.NurseRole
import domain.propertygenerator.NurseGenerator.{gen, property}
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object NurseRoleGenerator extends Properties("NurseRole"):

  def gen: Gen[NurseRole] =
    for
      randomNumber <- Gen.chooseNum(2, 5)
      alphaUpperChars <- Gen.pick(randomNumber, Seq('A', 'B', 'C', 'D', 'E'))
      alphaUpperChar = alphaUpperChars.head
      nurseRole <- NurseRole.from(alphaUpperChar.toString).fold(_ => Gen.fail, input => Gen.const(input))
    yield nurseRole

  def genMany: Gen[Seq[NurseRole]] =
    for
      randomNumber <- Gen.chooseNum(2, 5)
      nurseRoles <- Gen.containerOfN[Seq, NurseRole](randomNumber, gen)
    yield nurseRoles.distinct

  property("NurseRoleGenerator genMany should generates a list with items between 1 and 5") = forAll(genMany) (n => {
    n.length >= 1 && n.length <= 5
  })

  property("NurseRoleGenerator should generates a nurse role with a Alpha Upper Char") = forAll(gen) (n => {
    n.value.size == 1 && n.value.exists(_.isUpper)
  })