package domain

import domain.DomainError.EmptyName

type Result[A] = Either[DomainError, A]

enum DomainError:
  case IOFileProblem(error: String)
  case XMLError(error: String)
  case EmptyString
  case EmptyName
  case SeniorityValueOutOfRange(error: Int)
  case UnknownRequirementRole(error: String)
  case InvalidTime(error: String)
  case InvalidPositiveNumber(error: String)
  case InvalidSchedulingId(error: String)
  case NotEnoughNurses
  case PreferenceValueOutOfRange(error: Int)
  case InvalidDayOfWeek(error: String)