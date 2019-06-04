package com.javi.bankify.server

import scala.concurrent.Future

trait BankifyTestAPI {

  def generatePrimes(numbers: Long, algorithmName: Option[String]): Future[List[Long]]
}
