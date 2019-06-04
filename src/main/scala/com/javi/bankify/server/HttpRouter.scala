package com.javi.bankify.server

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream._
import com.javi.bankify.model._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

trait HttpRouter extends FailFastCirceSupport { api: BankifyTestAPI =>

  protected implicit def actorSystem: ActorSystem
  implicit lazy val httpMateriaizer: Materializer = ActorMaterializer(
    namePrefix = Some("bankifytest-http")
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
        complete(
          api.searchInGoogle(query)
        )
      }
    }
}
