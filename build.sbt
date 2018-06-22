name := "FillwordsSolver"

version := "0.1"

scalaVersion := "2.12.2"

resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.storm-enroute" %% "scalameter-core" % "0.8.2",
  "com.typesafe.akka" %% "akka-http" % "10.1.2",
  "com.typesafe.akka" %% "akka-stream" % "2.5.13",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.2",
  "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.14.0",
  "com.typesafe" % "config" % "1.3.2"
)
