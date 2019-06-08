object Versions {

  // logging
  val slf4j = "1.7.26"
  val log4j = "2.11.2"

  val scalaTest = "3.0.7"

  object cats {
    val main   = "1.6.0"
    val effect = "1.3.0"
    val logger = "0.3.0"
  }

  object akka {
    val main = "2.5.23"
    val http = "10.1.8"
    val json = "1.22.0"
  }

  // monitoring
  object kamon {
    val core        = "1.1.6"
    val akka        = "1.1.3"
    val http        = "1.1.1"
    val remote      = "1.1.0"
    val prometheus  = "1.1.1"
    val scala       = "1.0.0"
    val sysmetrics  = "1.0.1"
    val sigarLoader = "1.6.6"
    val executors   = "1.0.2"
  }

  val circe   = "0.11.1"
  val aspectj = "1.9.4"
  val scraper = "2.1.0"
}
