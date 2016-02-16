package me.ciaranoconnor.streams.flows

import akka.actor.{ActorLogging, Props}
import akka.http.scaladsl.model.ws.TextMessage
import akka.stream.actor.ActorPublisher
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContext

class NumbersPublisher extends ActorPublisher[MyData] with ActorLogging {

  override def preStart = {
    context.system.eventStream.subscribe(self, classOf[MyData])
  }

  override def receive: Receive = {

    case msg: MyData =>
      log.info("Got some data yo")
      if (isActive && totalDemand > 0) {
        // Pushes the message onto the stream
        onNext(msg)
      }
  }

}

object NumbersPublisher {
  def props(implicit  ctx: ExecutionContext): Props = Props(new NumbersPublisher())
}

case class MyData(data:String)

object MyData extends DefaultJsonProtocol {
  implicit val myDataFormat = jsonFormat1(MyData.apply)
}
