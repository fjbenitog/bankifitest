package com.javi.bankifi.server.primes

import org.scalatest._
import cats.implicits._

import scala.concurrent.Future


class PrimesServiceTest extends AsyncWordSpecLike with Matchers  {

  val primerService = PrimesServiceProvider()
  val algorithms = List("default","second-algorithm","sieve_eratostheness","unknown")


  "PrimesService" should {

    "return a empty list when size numbers is 0" in {
      val maxNumber = 0
      val expectedPrimeList = List.empty

      val results = algorithms.traverse(generatePrimes(_, maxNumber))

      results.map(primes  => primes.flatten should be (expectedPrimeList))
    }

    "return a empty list when size numbers is 1" in {
      val maxNumber = 1
      val expectedPrimeList = List.empty
      val results = algorithms.traverse(generatePrimes(_, maxNumber))

      results.map(primes  => primes.flatten should be (expectedPrimeList))
    }

    "return a list of primes number with the specify size" in {
      val maxNumber = 8
      val expectedPrimeList = List(2,3,5,7)

      val results = algorithms.traverse(generatePrimes(_, maxNumber))

      results.map(primes  => primes should be (List.fill(algorithms.size)(expectedPrimeList)))
    }
  }

  private def generatePrimes(algorithm: String, maxNumber: Int): Future[List[Long]] = {
    primerService.getPrimesService(Some(algorithm)).generatePrimes(maxNumber)
  }
}
