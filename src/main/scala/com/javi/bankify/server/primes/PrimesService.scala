package com.javi.bankify.server.primes

import scala.concurrent.Future

trait PrimesService {

  def generatePrimes(maxNumber:Long):Future[List[Long]]
}
