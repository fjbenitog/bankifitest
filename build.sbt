import Dependencies._
import com.typesafe.sbt.packager.docker.DockerChmodType
import kamon.aspectj.sbt.SbtAspectJRunner.Keys.aspectjVersion

lazy val akkaVersion = "2.5.22"

lazy val bankifytest = (project in file("."))
  .enablePlugins(DockerComposePlugin,RevolverPlugin, JavaAppPackaging, JavaAgent, SbtAspectJRunner)
  .settings(
    name := "bankifitest",
    dockerImageCreationTask := (publishLocal in Docker).value,
    dockerBaseImage := "openjdk:8-jre-stretch",
    dockerUpdateLatest := true,
    dockerExposedPorts := Seq(8080,9095),
    javaOptions in Universal ++= Seq(
      "-Dorg.aspectj.tracing.factory=default",
      s"-Dkamon.sigar.folder=${(defaultLinuxInstallLocation in Docker).value}/sigar-loader",
    ),
    dockerAdditionalPermissions += (DockerChmodType.UserGroupWriteExecute, s"${(defaultLinuxInstallLocation in Docker).value}/sigar-loader"),
    javaAgents ++= Seq(
      "org.aspectj" % "aspectjweaver" % aspectjVersion.value,
      "io.kamon"    % "sigar-loader"  % Versions.kamon.sigarLoader
    )
  )
  .settings(Dependencies.bankifitest)
