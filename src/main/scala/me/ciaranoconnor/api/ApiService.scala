package me.ciaranoconnor.api

import akka.http.scaladsl.server.RouteConcatenation
import me.ciaranoconnor.app.BootedCore

trait ApiService extends RouteConcatenation with UserHttpService with WebSockets with ResourceService with BootedCore {

  private implicit val _ = system.dispatcher

  val routes = userRoutes ~
    resourceRoutes ~
    websocket ~
    new SwaggerDocService(system).routes
}
