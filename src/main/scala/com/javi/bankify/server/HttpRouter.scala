package com.javi.bankify.server

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream._
import com.javi.bankify.model.{PrimesRequest, PrimesResponse}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.Future

trait HttpRouter extends FailFastCirceSupport { api: BankifyTestAPI =>

  protected implicit def actorSystem: ActorSystem
  implicit lazy val httpMateriaizer: Materializer = ActorMaterializer(
    namePrefix = Some("bankifytest-http")
  )

  def main: Route = primesRoute

  private def primesRoute: Route =
    path("primes") {
      post {
        entity(as[PrimesRequest]) { _ =>
          complete(
            Future.successful(PrimesResponse(Set(1,2,3)))
          )
        }
      }
    }
}
