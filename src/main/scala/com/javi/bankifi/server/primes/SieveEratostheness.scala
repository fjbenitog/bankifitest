package com.javi.bankifi.server.primes
import scala.concurrent.{ExecutionContext, Future}

class SieveEratostheness extends PrimesService {
  override def algorithmName: String = "sieve_eratostheness"

  override def generatePrimes(
      maxNumber: Long
  )(implicit executionContext: ExecutionContext): Future[List[Long]] = Future {
    val top    = math.floor(math.sqrt(maxNumber)).toInt
    val primes = new Array[Boolean](maxNumber.toInt + 1)

    (2 to top)
      .filterNot(primes)
      .foreach(i => {
        (i to (maxNumber.toInt / i)).foreach(j => {
          primes(j * i) = true
        })
      })

    (2 to maxNumber.toInt).filterNot(primes).map(_.toLong).toList

  }
}
