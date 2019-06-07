import Dependencies._

lazy val akkaVersion = "2.5.22"

lazy val bankifytest = (project in file("."))
  .enablePlugins(DockerComposePlugin, RevolverPlugin, JavaAppPackaging)
  .settings(
    name := "bankifi",
    dockerImageCreationTask := (publishLocal in Docker).value,
    dockerBaseImage := "openjdk:8-jre-stretch",
    dockerUpdateLatest := true,
    dockerExposedPorts := Seq(8080)
  )
  .settings(Dependencies.bankifitest)
