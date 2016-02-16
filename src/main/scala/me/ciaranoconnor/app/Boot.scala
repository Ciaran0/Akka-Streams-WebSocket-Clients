package me.ciaranoconnor.app

import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import me.ciaranoconnor.api.ApiService
import me.ciaranoconnor.streams.flows.MyData
import scala.annotation.tailrec
import scala.util.{ Success, Failure }

object Boot extends App with Config with BootedCore with ApiService {
  this: ApiService with Core =>

  import system.dispatcher

  implicit val materializer = ActorMaterializer()

  def ran = scala.util.Random
  @tailrec
  def sendInt(): Unit ={
    Thread sleep 4000
    var r = ran.nextInt()
    log.info(s"sending $r")
    system.eventStream.publish(MyData("help "+r))
    sendInt()
  }

  val binding = Http().bindAndHandle(routes, httpInterface, httpPort)
  //binding failure
  binding.onComplete {
    case Success(binding) ⇒
      val localAddress = binding.localAddress
      log.info(s"Server is listening on ${localAddress.getHostName}:${localAddress.getPort}")
    case Failure(e) ⇒
      log.error(s"Binding failed with ${e.getMessage}")
      system.terminate()
  }

  sendInt()


}
