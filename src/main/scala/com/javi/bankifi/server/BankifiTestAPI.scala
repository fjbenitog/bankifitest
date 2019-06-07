package com.javi.bankifi.server

import com.javi.bankifi.model._

import scala.concurrent.Future

trait BankifiTestAPI {

  def generatePrimes(request: PrimesRequest): Future[PrimesResponse]

  def searchInGoogle(query: String): Future[GoogleResponse]
}
