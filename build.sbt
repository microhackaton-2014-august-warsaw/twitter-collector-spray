import AssemblyKeys._

assemblySettings

name := "spray-microservice-stub"

version := "1.0"

scalaVersion := "2.10.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"

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
     "com.typesafe.akka" %% "akka-slf4j" % akkaV,
    "org.reactivemongo" %% "reactivemongo" % reactivemongoV,
     "net.logstash.logback" % "logstash-logback-encoder" % "3.0",
     "ch.qos.logback" % "logback-classic" % "1.1.2",
     "ch.qos.logback" % "logback-core" % "1.1.2",
     "org.slf4j" % "slf4j-api" % "1.6.6"
  )
}

Revolver.settings
