package com.javi.bankify.server.primes

import scala.concurrent.{ExecutionContext, Future}

trait PrimesService {
  def algorithmName: String

  def generatePrimes(maxNumber: Long)(
      implicit executionContext: ExecutionContext
  ): Future[List[Long]]
}

class PrimesServiceProvider {
  private val defaultAlgorithm = new PrimesServiceDefault
  private val secondAlgorithm  = new PrimesServiceSecondModel
  private val algorithms =
    Map[String, PrimesService](secondAlgorithm.algorithmName -> secondAlgorithm)

  def getPrimesService(algorithmName: Option[String] = Option.empty): PrimesService =
    algorithmName match {
      case Some(name) => algorithms.getOrElse(name, defaultAlgorithm)
      case _          => defaultAlgorithm
    }

}

object PrimesServiceProvider {
  def apply() = new PrimesServiceProvider
}
