package com.javi.bankify.server


import com.javi.bankify.model._
import akka.actor.ActorSystem
import com.javi.bankify.model.PrimesResponse._
import com.javi.bankify.model.PrimesRequest._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

import scala.concurrent.Future


class BankifyTestAPISpec extends WordSpec with ScalatestRouteTest with Matchers {

  val httpRouter: HttpRouter = new BankifyTestAPI with HttpRouter {
    override implicit val actorSystem: ActorSystem = system

    override def generatePrimes(numbers: Long): Future[List[Long]] = Future.successful(List(2,3))
  }

  "BankifyTest API" must {

    "return a list of prime number for POST requests to /primes" in {
      Post("/primes",PrimesRequest(10)) ~> httpRouter.main ~> check {

        responseAs[PrimesResponse].primes shouldEqual List(2,3)
      }
    }


  }

}
