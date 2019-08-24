import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "oyster"
ThisBuild / organizationName := "rcastro"

lazy val root = (project in file("."))
  .settings(
    name := "oystercard",
    libraryDependencies += scalaTest % Test
  )
