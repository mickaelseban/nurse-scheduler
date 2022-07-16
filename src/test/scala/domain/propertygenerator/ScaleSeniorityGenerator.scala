package domain.propertygenerator

import domain.info.ScaleSeniority
import domain.propertygenerator.ScaleGenerator.{gen, property}
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object ScaleSeniorityGenerator extends Properties("ScaleSeniority"):

  def gen: Gen[ScaleSeniority] =
    for 
      number <- Gen.chooseNum(1, 5)
      scaleSeniority <- ScaleSeniority.from(number).fold(_ => Gen.fail, x => Gen.const(x))
    yield scaleSeniority

  property("ScaleSeniorityGenerator should generate a number between 1 and 5") = forAll(gen) (n => {
    n.value >= 1 && n.value <= 5
  })