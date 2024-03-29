package com.javi.bankifi.bootstrap

import cats.effect._
import cats.implicits._
import com.javi.bankifi.monitoring.Monitor
import com.javi.bankifi.server.WebServer
import org.slf4j.LoggerFactory

object Main extends IOApp with Monitor {

  val logger = LoggerFactory.getLogger(Main.getClass)

  def run(args: List[String]): IO[ExitCode] = {

    val server = for {
      _      <- IO(logger.debug("Starting Monitoring ..."))
      _      <- startMonitoring()
      _      <- IO(logger.debug("Started Monitoring"))
      _      <- IO(logger.debug("Starting the Web server ..."))
      server <- WebServer.start
      _      <- IO(logger.debug("Started the Web server ..."))
    } yield server

    server.as(ExitCode.Success)
  }
}
