package me.ciaranoconnor.api

import akka.NotUsed
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives
import akka.stream.scaladsl._
import me.ciaranoconnor.streams.flows.{MyData, NumbersFlow}
import scala.concurrent.forkjoin.ThreadLocalRandom

trait WebSockets extends Directives {



  def websocket = {
    path("randomNums") {
      //send the the websocket random numbers

      val src =
      Source.fromIterator(() => Iterator.continually(ThreadLocalRandom.current.nextInt()))
      .filter(i => i > 0 && i % 2 == 0).map(i => TextMessage(i.toString))


      //send the user the numbers 1 to 5
      //val src = Source(1 to 5).map(i => TextMessage(i.toString))
        handleWebSocketMessages(NumbersFlow.myFlow)

    }
  }

}
