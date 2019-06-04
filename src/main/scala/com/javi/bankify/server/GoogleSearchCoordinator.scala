package com.javi.bankify.server

import akka.actor.{Actor, ActorRef, Props}
import com.javi.bankify.server.GoogleSearchReceptionist.{Query, Result}

object GoogleSearchCoordinator {

  def props(reply: ActorRef): Props = Props(new GoogleSearchCoordinator(reply))
}

class GoogleSearchCoordinator(reply: ActorRef) extends Actor {
  override def receive: Receive = {
    case Query(query) =>
      context.system.log.debug(s"Google Search Query:$query")
      reply ! Result("Pepe", "http://pp.es", "asdfasdfasd")
      context.stop(self)
  }
}
