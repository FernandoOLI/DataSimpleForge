import sbt.Keys.testFrameworks

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.15"
ThisBuild / version := sys.props.getOrElse("version.override", "0.1.0-SNAPSHOT")
testFrameworks += new TestFramework("org.scalatest.tools.Framework")
lazy val root = (project in file("."))
  .settings(
    name := "simple-forge",
    resolvers ++= Seq(
      "Maven Central" at "https://repo1.maven.org/maven2/",
      "Delta Lake IO" at "https://repo.delta.io/",
      "Confluent" at "https://packages.confluent.io/maven/"
    ),
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % "3.5.0",
      "org.apache.spark" %% "spark-sql" % "3.5.0",
      "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.5.0",
      "io.delta" %% "delta-spark" % "3.2.0",
      "org.apache.httpcomponents.client5" % "httpclient5" % "5.1.3",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.13.3",
      "com.github.scopt" %% "scopt" % "4.1.0",
      "org.scalatest" %% "scalatest" % "3.2.15" % Test,
      "org.scalatestplus" %% "mockito-4-6" % "3.2.15.0" % Test,
      "org.apache.spark" %% "spark-sql" % "3.4.0" % Test classifier "tests",
      "org.apache.spark" %% "spark-catalyst" % "3.4.0" % Test classifier "tests",
      "org.apache.spark" %% "spark-core" % "3.4.0" % Test classifier "tests",
      "org.mockito" %% "mockito-scala-scalatest" % "1.17.12" % Test
    )
  )

import sbtassembly.AssemblyPlugin.autoImport._

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.discard
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}