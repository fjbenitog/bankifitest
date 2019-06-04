package com.javi.bankify.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import cats.effect.IO
import com.javi.bankify.model.{PrimesRequest, PrimesResponse}
import com.javi.bankify.server.primes.{PrimesServiceDefault, PrimesServiceProvider}

import scala.concurrent.Future

object WebServer {

  def start: IO[Http.ServerBinding] = {

    val interface                         = "0.0.0.0"
    val port                              = 8080
    implicit val actorSystem: ActorSystem = ActorSystem("bankifytest-system")

    val server = new WebServer() with HttpRouter

    import server.httpMateriaizer

    val bindingFuture = Http().bindAndHandle(server.main, interface, port)

    IO.fromFuture(IO(bindingFuture))
  }
}

class WebServer(implicit val actorSystem: ActorSystem) extends BankifyTestAPI {

  import actorSystem.dispatcher
  val primesServiceProvider = PrimesServiceProvider()

  override def generatePrimes(request: PrimesRequest): Future[PrimesResponse] = {
    val primesService = primesServiceProvider.getPrimesService(request.algorithmName)
    primesService
      .generatePrimes(request.maxNumber)
      .map(primes => PrimesResponse(primesService.algorithmName, primes))
  }
}
