package com.javi.bankify.server

import akka.actor.{Actor, Props}

object GoogleSearch {

  case class Search(query: String)
  case class Found(title: String, url: String, text: String)
  case class Failure(message: String)

  def props: Props = Props(new GoogleSearch)
}
class GoogleSearch extends Actor {

  import GoogleSearch._
  override def receive: Receive = {
    case Search(query) =>
      context.system.log.debug(s"Searching for query:$query")
      context.sender() ! Found("Pepe", "http://pp.es", "asdfasdfasd")
//      context.sender() ! Failure("Connection Failure")
  }
}
