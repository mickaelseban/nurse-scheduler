package domain.propertygenerator

import domain.NonEmptyString
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object NonEmptyStringGenerator extends Properties("NonEmptyString"):

  def gen: Gen[NonEmptyString] =
    for
      number <- Gen.chooseNum(5, 15)
      listOfAlphaChars <- Gen.listOfN(number, Gen.alphaChar)
      nonEmptyString <- NonEmptyString.from(listOfAlphaChars.mkString).fold(_ => Gen.fail, x => Gen.const(x))
    yield nonEmptyString

  property("NonEmptyString must generate string not empty") = forAll(gen) (n => {
    !n.value.isEmpty
  })