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

    override def generatePrimes(numbers: Long, algorithmName: Option[String]): Future[List[Long]] = algorithmName match {
      case Some(_) => Future.successful(List(2,3,5))
      case _ => Future.successful(List(2,3))
    }

  }

  "BankifyTest API" must {

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

        responseAs[PrimesResponse].primes shouldEqual List(2,3)
      }
    }

  }

}
