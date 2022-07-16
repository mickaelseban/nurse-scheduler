package domain.propertygenerator

import domain.info.NurseName
import domain.propertygenerator.NurseRoleGenerator.{gen, genMany, property}
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object NurseNameGenerator extends Properties("NurseName"):

  def gen: Gen[NurseName] =
    for
      alphaChar <- Gen.alphaLowerChar
      nurseName <- NurseName.from("nurseName_" + alphaChar.toString).fold(_ => Gen.fail, x => Gen.const(x))
    yield nurseName

  property("NurseNameGenerator starts with prefix nurseName_ and ends with a lower alpha char") = forAll(gen) (n => {
    n.value.startsWith("nurseName_")
    !n.value.last.isDigit
    !n.value.last.isUpper
  })