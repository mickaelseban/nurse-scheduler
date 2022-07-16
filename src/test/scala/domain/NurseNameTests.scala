package domain

import domain.info.NurseName
import domain.DomainError.{EmptyName, EmptyString}
import io.FileIO.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.Inside

class NurseNameTests extends AnyFunSuite with Inside with Matchers :

  test("NurseName from string with n1 should be a NurseName") {
    val validNurseId = "n1"
    val actual = NurseName.from(validNurseId)
    assert(actual.isRight)
    inside(actual) { case Right(actual) => actual should equal (validNurseId) }
  }

  test("NurseName from empty name should be a EmptyString domain error") {
    val emptyName = ""
    val expected = EmptyName
    val actual = NurseName.from(emptyName)
    assert(actual.isLeft)
    inside(actual) { case Left(actual) => actual should equal (expected) }
  }

  test("NurseName from white space name should be a EmptyString domain error") {
    val whiteSpaceName = " "
    val expected = EmptyName
    val actual = NurseName.from(whiteSpaceName)
    assert(actual.isLeft)
    inside(actual) { case Left(actual) => actual should equal (expected) }
  }