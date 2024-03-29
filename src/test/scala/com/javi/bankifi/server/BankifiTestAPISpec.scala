package com.javi.bankifi.server


import com.javi.bankifi.model._
import akka.actor.ActorSystem
import com.javi.bankifi.model.PrimesResponse._
import com.javi.bankifi.model.PrimesRequest._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

import scala.concurrent.Future


class BankifiTestAPISpec extends WordSpec with ScalatestRouteTest with Matchers {



  val httpRouter: HttpRouter = new BankifiTestAPI with HttpRouter {
    override implicit val actorSystem: ActorSystem = system

    override def generatePrimes(request: PrimesRequest): Future[PrimesResponse] = request.algorithmName match {
      case Some(name) => Future.successful(PrimesResponse(name,List(2,3,5)))
      case _ => Future.successful(PrimesResponse("Default",List(2,3)))
    }

    override def searchInGoogle(query: String): Future[GoogleResponse] = ???
  }

  "BankifiTest API" must {

    "return a list of primes numbers for POST requests to /primes with default algorithm" in {
      val maxNumber = 10
      Post("/primes",PrimesRequest(maxNumber)) ~> httpRouter.main ~> check {

        responseAs[PrimesResponse].primes shouldEqual List(2,3)
      }
    }

    "return a list of primes numbers for POST requests to /primes with the specified algorithm" in {
      val maxNumber = 10
      val algorithmName = "Algorithm_Method"

      Post("/primes",PrimesRequest(maxNumber,Some(algorithmName))) ~> httpRouter.main ~> check {

        responseAs[PrimesResponse].primes shouldEqual List(2,3,5)
      }
    }

  }

}
