package com.javi.bankifi.server

import java.util.UUID

import akka.actor.{Actor, Props}
object GoogleSearchReceptionist {

  case class Query(value: String)

  trait QueryResponse

  case class Result(title: String, url: String, text: String) extends QueryResponse

  case class SearchFailure(message: String) extends QueryResponse

  def props(): Props = Props(new GoogleSearchReceptionist)
}
class GoogleSearchReceptionist extends Actor {

  import GoogleSearchReceptionist._

  val googleSearch = context.actorOf(GoogleSearch.props,"GoogleSearch")

  override def receive: Receive = {
    case query @ Query(_) =>
      val coordinator =
        context.actorOf(
          GoogleSearchCoordinator.props(context.sender(), googleSearch),
          s"googleSearchCoordinator-${UUID.randomUUID}"
        )
      coordinator ! query

  }
}
