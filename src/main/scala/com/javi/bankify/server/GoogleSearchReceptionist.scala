package com.javi.bankify.server

import akka.actor.{Actor, Props}
object GoogleSearchReceptionist {

  case class Query(value: String)

  case class Result(title: String, url: String, text: String)

  def props(): Props = Props(new GoogleSearchReceptionist)
}
class GoogleSearchReceptionist extends Actor {

  import GoogleSearchReceptionist._

  override def receive: Receive = {
    case query @ Query(value) =>
      val coordinator = context.actorOf(GoogleSearchCoordinator.props(context.sender()))
      coordinator ! query

  }
}
