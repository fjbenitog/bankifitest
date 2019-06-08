package com.javi.bankifi.server

import akka.actor.{Actor, Props}
import cats.Show
import com.javi.bankifi.server.google.GoogleSearchService
import cats.effect._
import cats.implicits._
import com.javi.bankifi.config.DefaultBlockingDispatcher

import scala.util.{Failure, Success}

object GoogleSearch {
  case class Search(query: String)

  sealed trait SearchResponse
  case class ValueFound(title: String, url: String, text: String) extends SearchResponse
  case class GoogleFailure(message: String)                       extends SearchResponse

  implicit val showValueFound: Show[ValueFound] =
    Show.show(value => s"""ValueFound("${value.title}","${value.url}","${value.text}")""")

  def props: Props =
    Props(new GoogleSearch)
      .withDispatcher(DefaultBlockingDispatcher)
}
class GoogleSearch extends Actor {

  val googleSearchService = new GoogleSearchService

  import GoogleSearch._
  import context.dispatcher
  override def receive: Receive = {
    case Search(query) =>
      val replayTo = context.sender()
      googleSearchService
        .search(query)
        .map(response => ValueFound(response.title, response.url, response.text))
        .onError {
          case ex =>
            IO(
              context.system.log.error(
                ex,
                s"Error Searching in Google for query: '$query'"
              )
            )
        }
        .unsafeToFuture()
        .onComplete {
          case Success(searchResponse) => replayTo ! searchResponse
          case Failure(ex)             => replayTo ! GoogleFailure(ex.getMessage)
        }

  }
}
