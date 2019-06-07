package com.javi.bankifi.server

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream._
import com.javi.bankifi.model._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import cats.implicits._

trait HttpRouter extends FailFastCirceSupport { api: BankifiTestAPI =>

  protected implicit def actorSystem: ActorSystem
  implicit lazy val httpMateriaizer: Materializer = ActorMaterializer(
    namePrefix = Some("bankifitest-http")
  )

  def main: Route = primesRoute ~ googleRoute

  private def primesRoute: Route =
    path("primes") {
      post {
        entity(as[PrimesRequest]) { request =>
          complete(
            api.generatePrimes(request)
          )
        }
      }
    }

  private def googleRoute: Route =
    path("google") {
      parameters('query) { query =>
        extractExecutionContext { implicit ec =>
          complete(api.searchInGoogle(query).map(parseResponse))
        }
      }
    }

  def parseResponse(response: GoogleResponse): (StatusCode, GoogleResponse) = response match {
    case error: GoogleQueryFailure => StatusCodes.InternalServerError -> error
    case result                    => StatusCodes.OK                  -> result
  }

}
