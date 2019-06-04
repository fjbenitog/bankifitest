package com.javi.bankify.server.primes

import org.scalatest._


class PrimesServiceDefaultTest extends AsyncWordSpecLike with Matchers  {

  "PrimesServiceDefault" should {

    "return a empty list when size numbers is 0" in {
      val maxNumber = 0
      val expectedPrimeList = List.empty
      val actualPrimeList = new PrimesServiceDefault().generatePrimes(maxNumber)

      actualPrimeList.map(primes  => primes should be (expectedPrimeList))
    }

    "return a empty list when size numbers is 1" in {
      val maxNumber = 1
      val expectedPrimeList = List.empty
      val actualPrimeList = new PrimesServiceDefault().generatePrimes(maxNumber)

      actualPrimeList.map(primes  => primes should be (expectedPrimeList))
    }

    "return a list of primes number with the specify size" in {
      val maxNumber = 3
      val expectedPrimeList = List(2,3)
      val actualPrimeList = new PrimesServiceDefault().generatePrimes(maxNumber)

      actualPrimeList.map(primes  => primes should be (expectedPrimeList))
    }
  }
}
