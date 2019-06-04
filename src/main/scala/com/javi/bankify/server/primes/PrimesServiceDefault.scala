package com.javi.bankify.server.primes

import scala.concurrent.{ExecutionContext, Future}

private class PrimesServiceDefault extends PrimesService {

  def generatePrimes(
      maxNumber: Long
  )(implicit executionContext: ExecutionContext): Future[List[Long]] = Future {
    var c    = 1
    var p    = 2
    var d    = 2
    val list = scala.collection.mutable.SortedSet[Long]()
    while (p <= maxNumber) {
      if (p % d == 0) {
        if (p == d) {
          list.add(p)
          c += 1
        }
        d = 2
        p += 1
      } else d += 1
    }
    list.toList
  }

  override def algorithmName: String = "Default"
}
