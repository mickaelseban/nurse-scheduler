package xml

import scala.xml.Node
import domain.Result
import domain.DomainError.XMLError

object XML:

  /*** Get a subnode with name {@code n} from a xml node, if it exists ***/
  def fromNode(xml: Node, n: String): Result[Node] =
    val node = (xml \ n).headOption
    node.fold[Result[Node]](Left(XMLError(s"Node $n is empty/undefined in ${xml.label}")))(nd => Right(nd))

  /*** Create a String from a valid xml attribute, if it exists in a node ***/
  def fromAttribute(xml: Node, a: String): Result[String] =
    val attr = (xml \@ a)
    if (attr.isEmpty) Left(XMLError(s"Attribute $a is empty/undefined in ${xml.label}"))
    else Right(attr)

  /*** Traverse seq s, folding each element using function f, resulting in the new seq ***/
  def traverse[A, B](s: Seq[A], f: A => Result[B]): Result[List[B]] =
    s.foldLeft[Result[List[B]]](Right(Nil)) { case (t, a) =>
      for
        lb <- t
        b <- f(a)
      yield lb :+ b
    }
