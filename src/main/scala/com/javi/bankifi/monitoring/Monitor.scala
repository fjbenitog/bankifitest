package com.javi.bankifi.monitoring

import cats.effect._
import cats.implicits._
import kamon.prometheus.PrometheusReporter
import kamon.system.SystemMetrics
import kamon._
import kamon.util.Registration

trait Monitor {

  def startMonitoring(): IO[Unit] = {

    def startModules(): IO[Unit] = IO(SystemMetrics.startCollecting())

    def cancelRegistration(reg: Registration): IO[Unit] = IO(reg.cancel()).void

    def registerReporter(reporter: MetricReporter): IO[IO[Unit]] = {

      def deferUnregister(registration: Registration): IO[Unit] = IO.suspend {
        IO(SystemMetrics.stopCollecting()) *> cancelRegistration(registration)
      }

      IO(deferUnregister(Kamon.addReporter(reporter)))
    }

    registerReporter(new PrometheusReporter) *> startModules()

  }
}
