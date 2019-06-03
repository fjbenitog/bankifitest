import Dependencies.Akka

lazy val akkaVersion = "2.5.22"

lazy val bankifytest = (project in file("."))
  .enablePlugins(DockerComposePlugin, RevolverPlugin, JavaAppPackaging)
  .settings(
    name := "bankifytest",
    libraryDependencies ++= Akka.Common
  )
