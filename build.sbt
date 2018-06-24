
name := "server"

version := "0.0.0"

scalaVersion := "2.12.6"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += guice

libraryDependencies += ws

libraryDependencies += jdbc

libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.6"

libraryDependencies += "com.typesafe.play" %% "anorm" % "2.5.3"

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test

libraryDependencies += "com.jason-goodwin" %% "authentikat-jwt" % "0.4.5"

