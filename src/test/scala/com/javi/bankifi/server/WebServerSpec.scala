package com.javi.bankifi.server

import org.scalatest._
import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.javi.bankifi.model._

class WebServerSpec(_system: ActorSystem)
  extends TestKit(_system)
  with Matchers
  with AsyncWordSpecLike
  with BeforeAndAfterAll {

    def this() = this(ActorSystem("WebServerSpec"))

    val webServer = new WebServer()

    override def afterAll: Unit = {
      shutdown(system)
    }

    "A WebServer" should {
        "generate primes numbers with Default Algorithm if this is not specified" in {
          val maxNumber = 4
          val expectedPrimeList = List(2,3)

          webServer.generatePrimes(PrimesRequest(maxNumber))
            .map(response  => {
              response.primes should be (expectedPrimeList)
              response.algorithmName should be ("default")
            })
        }

      "generate primes numbers with the specified Algorithm" in {
        val maxNumber = 4
        val expectedPrimeList = List(2, 3)
        val expectedAlgorithm = "second-algorithm"

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

        webServer.generatePrimes(PrimesRequest(maxNumber, Some(expectedAlgorithm)))
          .map(response => {
            response.primes should be(expectedPrimeList)
            response.algorithmName should be("default")
          })
      }


      "return the second search result from Google for query Q, excluding ads " in {

        webServer.searchInGoogle("bankifi")
          .map {
            case GoogleQueryResult(title,url,text) => {
              title should not be empty
              url should not be empty
              text should not be empty
            }
            case error:GoogleQueryFailure => fail(error.message)
          }
      }

    }
}
