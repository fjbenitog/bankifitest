package com.javi.bankify.server

import com.javi.bankify.model._

import scala.concurrent.Future

trait BankifyTestAPI {

  def generatePrimes(request: PrimesRequest): Future[PrimesResponse]
}
