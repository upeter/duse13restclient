

name := "duse13"

scalaVersion := "2.9.0-1"

//libraryDependencies += "org.specs2" %% "specs2" % "1.5" % "test"
libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % "1.6.1",
        "ch.qos.logback" % "logback-classic" % "0.9.28",
	"net.databinder" %% "dispatch-http" % "0.8.5",
	"net.liftweb" % "lift-json_2.9.0-1" % "2.4-M3",
	"net.liftweb" % "lift-json-ext_2.9.0-1" % "2.4-M3",
	"joda-time" % "joda-time" % "1.6.2",
	"org.scala-tools.testing" % "specs_2.9.0-1" % "1.6.8",
	"junit" % "junit" % "4.7",
	"org.scalatest" % "scalatest_2.9.0-1" % "1.6.1"
)

	//"org.specs2" %% "specs2" % "1.4" % "test",
//val dependency = "net.databinder" % "dispatch-http_2.8.1" %  "0.8.0.Beta2"
