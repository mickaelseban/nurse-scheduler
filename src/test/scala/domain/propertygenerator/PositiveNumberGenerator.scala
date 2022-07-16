package domain.propertygenerator

import domain.PositiveNumber
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object PositiveNumberGenerator extends Properties("PositiveNumber"):

  def gen(maxNumber: Int): Gen[PositiveNumber] =
    for
      number <- Gen.chooseNum(1, maxNumber)
      positiveNumber <- PositiveNumber.from(number).fold(_ => Gen.fail, input => Gen.const(input))
    yield positiveNumber

  property("PositiveNumberGenerator must generate a positive number between 1 and 10") = forAll(gen(Int.MaxValue)) (n => {
      n.value >= 1
      n.value <= Int.MaxValue
  })