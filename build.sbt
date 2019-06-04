import Dependencies._

lazy val akkaVersion = "2.5.22"

lazy val bankifytest = (project in file("."))
  .enablePlugins(DockerComposePlugin, RevolverPlugin, JavaAppPackaging)
  .settings(
    name := "bankifytest",
    dockerImageCreationTask := (publishLocal in Docker).value,
    libraryDependencies ++= Akka.Common,
    libraryDependencies ++= Cats.All,
    libraryDependencies ++= Testing.scalaTest.Default,
    libraryDependencies ++= Logging.All,
    libraryDependencies ++= Circe.All,
    libraryDependencies += Logging.akka,
    dockerBaseImage := "openjdk:8-jre-stretch",
    dockerUpdateLatest := true,
    dockerExposedPorts := Seq(8080)
  )
