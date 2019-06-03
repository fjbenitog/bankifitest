import Dependencies._

lazy val akkaVersion = "2.5.22"

lazy val bankifytest = (project in file("."))
  .enablePlugins(DockerComposePlugin, RevolverPlugin, JavaAppPackaging)
  .settings(
    name := "bankifytest",
    libraryDependencies ++= Akka.Common,
    libraryDependencies ++= Cats.All,
    libraryDependencies ++= Testing.scalaTest.Default,
    libraryDependencies ++= Logging.All,
    libraryDependencies ++= Circe.All,
    libraryDependencies += Logging.akka
  )
