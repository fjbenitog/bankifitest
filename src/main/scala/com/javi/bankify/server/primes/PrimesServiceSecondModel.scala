package com.javi.bankify.server.primes

import scala.concurrent.{ExecutionContext, Future}

private class PrimesServiceSecondModel extends PrimesService {

  override def generatePrimes(
      maxNumber: Long
  )(implicit executionContext: ExecutionContext): Future[List[Long]] = Future {

    def isPrime(number: Long): Boolean = {
      var counter = 2
      var prime   = true
      while ({
        prime && (counter != number)
      }) {
        if (number % counter == 0) prime = false
        counter += 1
      }
      prime
    }

    val numbers = for (number <- 2l to maxNumber) yield number

    numbers.filter(n => isPrime(n)).toList
  }

  override def algorithmName: String = "SecondAlgorithm"
}
