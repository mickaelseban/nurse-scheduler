name := "nurse-scheduler"

version := "0.1"

scalaVersion := "3.1.1"

scalacOptions ++= Seq("-source:future", "-indent", "-rewrite")

// XMl
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0"

// Scalatest
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"

// Scalacheck
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.16.0" % "test"
