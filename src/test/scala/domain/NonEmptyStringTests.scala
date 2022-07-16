package domain

import domain.*
import domain.DomainError.EmptyString
import io.FileIO.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.Inside

class NonEmptyStringTests extends AnyFunSuite with Inside with Matchers :

  test("NonEmptyString from nonEmptyString should be a NonEmptyString") {
    val nonEmptyString = "nonEmptyString"
    val actual = NonEmptyString.from(nonEmptyString)
    assert(actual.isRight)
    inside(actual) { case Right(actual) => actual should equal (nonEmptyString) }
  }

  test("NonEmptyString from emptyString should be a EmptyString domain error") {
    val emptyString = ""
    val expected = EmptyString
    val actual = NonEmptyString.from(emptyString)
    assert(actual.isLeft)
    inside(actual) { case Left(actual) => actual should equal (expected) }
  }
