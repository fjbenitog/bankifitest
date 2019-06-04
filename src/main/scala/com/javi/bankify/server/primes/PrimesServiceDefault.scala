package com.javi.bankify.server.primes

import scala.concurrent.{ExecutionContext, Future}

class PrimesServiceDefault private(implicit computingExecutionContext: ExecutionContext) extends PrimesService {

  //Copied from internet implementation
  def generatePrimes(maxNumber:Long):Future[List[Long]] = Future{
      var c = 1
      var p = 2
      var d = 2
      val list = scala.collection.mutable.SortedSet[Long]()
      while (p <= maxNumber) {
        if (p % d == 0) {
          if (p == d) {
            list.add(p)
            c += 1
          }
          d = 2
          p += 1
        }
        else d += 1
      }
      list.toList
  }
}

object PrimesServiceDefault {
  def apply()(implicit computingExecutionContext: ExecutionContext) = new PrimesServiceDefault
}
