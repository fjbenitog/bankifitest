package com.javi.bankifi.server

import akka.actor.{Actor, Props}
import com.javi.bankifi.server.google.GoogleSearchService
import cats.effect._
import cats.implicits._

object GoogleSearch {
  case class Search(query: String)

  sealed trait SearchResponse
  case class ValueFound(title: String, url: String, text: String) extends SearchResponse
  case class GoogleFailure(message: String) extends SearchResponse

  def props: Props = Props(new GoogleSearch)
}
class GoogleSearch extends Actor {

  val googleSearchService = new GoogleSearchService

  import GoogleSearch._
  override def receive: Receive = {
    case Search(query) =>
      val searchResponse = googleSearchService
        .search(query)
        .map(response => ValueFound(response.title, response.url, response.text))
        .onError {
          case ex =>
            IO(
              context.system.log.error(
                ex,
                s"Error Searching in Google for query: $query"
              )
            )
        }
        .handleErrorWith(ex => IO.pure(GoogleFailure(ex.getMessage)))
          .unsafeRunSync()
      context.sender() ! searchResponse
  }
}
