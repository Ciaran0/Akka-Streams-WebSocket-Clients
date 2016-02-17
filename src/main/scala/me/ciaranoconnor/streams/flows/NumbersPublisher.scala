package me.ciaranoconnor.streams.flows

import akka.actor.{ActorLogging, Props}
import akka.stream.actor.ActorPublisher
import me.ciaranoconnor.models.MyData

import scala.concurrent.ExecutionContext

class NumbersPublisher extends ActorPublisher[MyData] with ActorLogging {

  override def preStart = {
    context.system.eventStream.subscribe(self, classOf[MyData])
  }

  override def receive: Receive = {

    case msg: MyData =>
      if (isActive && totalDemand > 0) {
        // Pushes the message onto the stream
        onNext(msg)
      }
  }

}

object NumbersPublisher {
  def props(implicit  ctx: ExecutionContext): Props = Props(new NumbersPublisher())
}


