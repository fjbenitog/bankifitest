package com.javi.bankify.server

import org.scalatest._
import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.javi.bankify.model.PrimesRequest

class WebServerSpec(_system: ActorSystem)
  extends TestKit(_system)
  with Matchers
  with AsyncWordSpecLike
  with BeforeAndAfterAll {

    def this() = this(ActorSystem("WebServerSpec"))

    override def afterAll: Unit = {
      shutdown(system)
    }

    "A WebServer" should {
        "generate primes numbers with Default Algorithm if this is not specified" in {
          val maxNumber = 4
          val expectedPrimeList = List(2,3)
          val webServer = new WebServer()

          webServer.generatePrimes(PrimesRequest(maxNumber))
            .map(response  => {
              response.primes should be (expectedPrimeList)
              response.algorithmName should be ("Default")
            })
        }

      "generate primes numbers with the specified Algorithm" in {
        val maxNumber = 4
        val expectedPrimeList = List(2, 3)
        val expectedAlgorithm = "SecondAlgorithm"
        val webServer = new WebServer()

        webServer.generatePrimes(PrimesRequest(maxNumber, Some(expectedAlgorithm)))
          .map(response => {
            response.primes should be(expectedPrimeList)
            response.algorithmName should be(expectedAlgorithm)
          })
      }

      "generate primes numbers with Default Algorithm if the specified doesn't exist" in {
        val maxNumber = 4
        val expectedPrimeList = List(2, 3)
        val expectedAlgorithm = "InvalidAlgorithm"
        val webServer = new WebServer()

        webServer.generatePrimes(PrimesRequest(maxNumber, Some(expectedAlgorithm)))
          .map(response => {
            response.primes should be(expectedPrimeList)
            response.algorithmName should be("Default")
          })
      }

    }
}
