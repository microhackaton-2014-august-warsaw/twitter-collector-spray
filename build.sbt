import AssemblyKeys._

assemblySettings

name := "spray-microservice-stub"

version := "1.0"

scalaVersion := "2.10.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= {
  val akkaV = "2.3.0"
  val sprayV = "1.3.1"
  val sprayJsonV = "1.2.6"
  val reactivemongoV = "0.11.0-SNAPSHOT"
  Seq(
    "io.spray" % "spray-can" % sprayV,
    "io.spray" % "spray-routing" % sprayV,
    "io.spray" %% "spray-json" % sprayJsonV,
    "io.spray" % "spray-caching" % sprayV,
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "org.reactivemongo" %% "reactivemongo" % reactivemongoV
  )
}

Revolver.settings
