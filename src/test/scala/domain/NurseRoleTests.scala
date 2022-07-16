package domain

import domain.*
import domain.DomainError.*
import domain.info.NurseRole
import io.FileIO.*
import org.scalatest.Inside
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class NurseRoleTests extends AnyFunSuite with Inside with Matchers :

  test("NurseRole one upper alpha char should be a NurseRole") {
    val oneUpperAlphaChar = "A"
    val actual = NurseRole.from(oneUpperAlphaChar)
    assert(actual.isRight)
    inside(actual) { case Right(actual) => actual should equal (oneUpperAlphaChar) }
  }

  test("NurseRole from two upper alpha char should be a UnknownRequirementRole domain error") {
    val twoUpperAlphaChar = "AA"
    val expected = UnknownRequirementRole(twoUpperAlphaChar)
    val actual = NurseRole.from(twoUpperAlphaChar)
    assert(actual.isLeft)
    inside(actual) { case Left(actual) => actual should equal (expected) }
  }

  test("NurseRole from one numeric char should be a UnknownRequirementRole domain error") {
    val oneNumericChar = "1"
    val expected = UnknownRequirementRole(oneNumericChar)
    val actual = NurseRole.from(oneNumericChar)
    assert(actual.isLeft)
    inside(actual) { case Left(actual) => actual should equal (expected) }
  }

  test("NurseRole from one alpha lower char should be a UnknownRequirementRole domain error") {
    val oneLowerAlphaChar = "a"
    val expected = UnknownRequirementRole(oneLowerAlphaChar)
    val actual = NurseRole.from(oneLowerAlphaChar)
    assert(actual.isLeft)
    inside(actual) { case Left(actual) => actual should equal (expected) }
  }
