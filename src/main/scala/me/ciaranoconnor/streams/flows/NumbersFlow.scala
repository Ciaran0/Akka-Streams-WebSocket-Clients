package me.ciaranoconnor.streams.flows

import akka.actor.ActorRef
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl.{Keep, Sink, Flow, Source}

import scala.concurrent.ExecutionContext

object NumbersFlow {

  implicit val executionContext = ExecutionContext.global

  val numbersSource: Source[MyData, ActorRef] = Source.actorPublisher[MyData](NumbersPublisher.props)

  def toMessage(myData: MyData) : Message = TextMessage(myData.toString)

  type MessageFilter = MyData => Boolean

  val myFlow = Flow.fromSinkAndSource(Sink.ignore, numbersSource map {d => TextMessage.Strict(d.data)})


}
