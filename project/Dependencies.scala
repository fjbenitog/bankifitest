import sbt._
import Keys._

object Dependencies {

  object Akka {
    val actor  = "com.typesafe.akka" %% "akka-actor"  % Versions.akka.main
    val stream = "com.typesafe.akka" %% "akka-stream" % Versions.akka.main
    val http   = "com.typesafe.akka" %% "akka-http"   % Versions.akka.http

    object testkit {
      val common = "com.typesafe.akka" %% "akka-testkit"      % Versions.akka.main
      val http   = "com.typesafe.akka" %% "akka-http-testkit" % Versions.akka.http

      lazy val All = Seq(common, stream, http).map(_ % Test)
    }

    lazy val Common = Seq(actor, stream, http) ++ testkit.All
  }
}
