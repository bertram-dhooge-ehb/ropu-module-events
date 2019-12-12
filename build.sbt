name := """events-module"""
organization := "org.ropu"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)

javaOptions in Universal ++= Seq(
  // JVM memory tuning
  "-J-Xmx1024m",
  "-J-Xms512m",
  // Since play uses separate pidfile we have to provide it with a proper path
  // name of the pid file must be play.pid
  s"-Dpidfile.path=/var/run/${packageName.value}/play.pid",
  // Use separate configuration file for production environment
  s"-Dconfig.file=/usr/share/${packageName.value}/conf/production.conf",
  // Use separate logger configuration file for production environment
  s"-Dlogger.file=/usr/share/${packageName.value}/conf/logback.xml"
)

// exposing the play ports
dockerExposedPorts in Docker := Seq(9000, 9443)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test
//libraryDependencies += "net.liftweb" %% "lift-json" % "XXX"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "org.ropu.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "org.ropu.binders._"
