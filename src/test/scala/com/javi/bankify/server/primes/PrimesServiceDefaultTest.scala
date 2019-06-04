package com.javi.bankify.server.primes

import org.scalatest._


class PrimesServiceDefaultTest extends AsyncWordSpecLike with Matchers  {

  "PrimesServiceDefault" should {

    "return a empty list when size numbers is 0" in {
      val primeNumberSize = 0
      val expectedPrimeList = List.empty
      val actualPrimeList = PrimesServiceDefault().generatePrimes(primeNumberSize)

      actualPrimeList.map(primes  => primes should be (expectedPrimeList))
    }

    "return a list of primes number with the specify size" in {
      val primeNumberSize = 3
      val expectedPrimeList = List(2,3,5)
      val actualPrimeList = PrimesServiceDefault().generatePrimes(primeNumberSize)

      actualPrimeList.map(primes  => primes should be (expectedPrimeList))
    }
  }
}
