package com.javi.bankify.server.primes

import scala.concurrent.Future

trait PrimesService {

  def generatePrimes(numbers:Long):Future[List[Long]]
}
