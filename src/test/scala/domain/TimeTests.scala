package domain

import domain.*
import domain.DomainError.InvalidTime
import io.FileIO.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.Inside

import scala.xml.Elem

class TimeTests extends AnyFunSuite with Inside with Matchers :

  test("Time from validDate should be a Time") {
    val validTime = "08:00:20"
    val actual = Time.from(validTime)
    assert(actual.isRight)
    inside(actual) { case Right(actual) => actual.toString should equal (validTime) }
  }

  test("Time from inValidDate should be a InvalidTime domain error") {
    val inValidTime = "08:00:00:00:00"
    val expected = InvalidTime(inValidTime)
    val actual = Time.from(inValidTime)
    assert(actual.isLeft)
    inside(actual) { case Left(actual) => actual should equal (expected) }
  }
