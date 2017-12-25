name := "FillwordsSolver"

version := "0.1"

scalaVersion := "2.12.2"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "io.monix" %% "monix" % "2.3.0"

resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies +=
  "com.storm-enroute" %% "scalameter-core" % "0.8.2"