package com.javi.bankify.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import cats.effect.IO
import com.javi.bankify.server.primes.PrimesServiceDefault

import scala.concurrent.Future

object WebServer {

  def start: IO[Http.ServerBinding] = {

    val interface = "0.0.0.0"
    val port      = 8080
    val server    = new WebServer() with HttpRouter

    implicit val actorSystem: ActorSystem = server.actorSystem
    import server.httpMateriaizer

    val bindingFuture = Http().bindAndHandle(server.main, interface, port)

    IO.fromFuture(IO(bindingFuture))
  }
}

class WebServer extends BankifyTestAPI {
  implicit val actorSystem = ActorSystem("bankifytest-system")

  import actorSystem.dispatcher
  val primesService = PrimesServiceDefault()

  override def generatePrimes(numbers: Long): Future[List[Long]] = primesService.generatePrimes(numbers)
}
