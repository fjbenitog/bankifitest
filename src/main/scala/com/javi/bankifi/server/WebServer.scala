package com.javi.bankifi.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.pattern._
import akka.util.Timeout
import cats.effect.IO
import com.javi.bankifi.model._
import com.javi.bankifi.server.GoogleSearchReceptionist._
import com.javi.bankifi.server.primes._
import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

object WebServer {

  def start: IO[Http.ServerBinding] = {

    val interface                         = "0.0.0.0"
    val port                              = 8080
    implicit val actorSystem: ActorSystem = ActorSystem("bankifitest-system")

    val server = new WebServer() with HttpRouter

    import server.httpMateriaizer

    val bindingFuture = Http().bindAndHandle(server.main, interface, port)

    IO.fromFuture(IO(bindingFuture))
  }
}

class WebServer(implicit val actorSystem: ActorSystem) extends BankifiTestAPI {

  import actorSystem.dispatcher
  val primesServiceProvider = PrimesServiceProvider()

  val logger = LoggerFactory.getLogger(WebServer.getClass)

  private implicit val timeout: Timeout = Timeout(10 seconds)

  val receptionist =
    actorSystem.actorOf(GoogleSearchReceptionist.props(), "GoogleSearchReceptionist")

  override def generatePrimes(request: PrimesRequest): Future[PrimesResponse] = {
    val primesService = primesServiceProvider.getPrimesService(request.algorithmName)
    for {
      _      <- Future(logger.debug(s"Generating primes numbers for ${request.maxNumber} ..."))
      primes <- primesService.generatePrimes(request.maxNumber)
      _      <- Future(logger.debug(s"Generated primes numbers:${primes} for ${request.maxNumber}"))
    } yield PrimesResponse(primesService.algorithmName, primes)

  }

  override def searchInGoogle(query: String): Future[GoogleResponse] =
    (receptionist ? Query(query))
      .mapTo[QueryResponse]
      .map {
        case Result(title, url, text) => GoogleQueryResult(title, url, text)
        case SearchFailure(message)   => GoogleQueryFailure(message)
      }
}
