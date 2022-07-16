package domain.schedule

import scala.xml.Elem
import domain.Result

trait Schedule:
  def create(xml: Elem): Result[Elem]
