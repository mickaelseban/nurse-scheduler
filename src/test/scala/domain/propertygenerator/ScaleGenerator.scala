package domain.propertygenerator

import domain.info.Scale
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object ScaleGenerator extends Properties("Scale"):

  def gen: Gen[Scale] =
    for
      number <- Gen.chooseNum(-2, 2)
      scale <- Scale.from(number).fold(_ => Gen.fail, x => Gen.const(x))
    yield scale

  property("ScaleGenerator should generate a number between -2 and 2") = forAll(gen) (n => {
    n.value >= -2 && n.value <= 2
  })