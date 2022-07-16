package domain

import domain.PositiveNumber
import domain.*
import domain.DomainError.InvalidPositiveNumber
import io.FileIO.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.Inside

class PositiveNumberTests extends AnyFunSuite with Inside with Matchers:

  test("PositiveNumber from a positive integer should be a PositiveNumber") {
    val positiveNumber = 2
    val actual = PositiveNumber.from(positiveNumber)
    assert(actual.isRight)
    inside(actual) { case Right(actual) => actual should equal (positiveNumber) }
  }

  test("PositiveNumber from negative integer should be a InvalidPositiveNumber domain error") {
    val negativeNumber = -2
    val expected = InvalidPositiveNumber(negativeNumber.toString)
    val actual = PositiveNumber.from(negativeNumber)
    assert(actual.isLeft)
    inside(actual) { case Left(actual) => actual should equal (expected) }
  }

  test("PositiveNumber from positive integer as string should be a PositiveNumber") {
    val positiveNumber = 2
    val positiveNumberAsString = positiveNumber.toString
    val actual = PositiveNumber.from(positiveNumberAsString)
    assert(actual.isRight)
    inside(actual) { case Right(actual) => actual should equal (positiveNumber) }
  }

  test("PositiveNumber from negative integer as string should be a InvalidPositiveNumber domain error") {
    val negativeNumber = -2
    val negativeNumberAsString = negativeNumber.toString
    val expected = InvalidPositiveNumber(negativeNumberAsString)
    val actual = PositiveNumber.from(negativeNumberAsString)
    assert(actual.isLeft)
    inside(actual) { case Left(actual) => actual should equal (expected) }
  }