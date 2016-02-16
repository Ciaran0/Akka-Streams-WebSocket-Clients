package me.ciaranoconnor.api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.github.swagger.akka.model.{Contact, Info}
import com.github.swagger.akka.{HasActorSystem, SwaggerHttpService}

import scala.reflect.runtime.universe._

class SwaggerDocService(system: ActorSystem) extends SwaggerHttpService with HasActorSystem {
  override implicit val actorSystem: ActorSystem = system
  override implicit val materializer: ActorMaterializer = ActorMaterializer()
  override val apiTypes = Seq(typeOf[UserHttpService])
  override val host = "localhost:8080" //the url of your api, not swagger's json endpoint
  override val basePath = "/"    //the basePath for the API you are exposing
  override val apiDocsPath = "/api-docs" //where you want the swagger-json endpoint exposed
  override val info = Info("API docs for Akka-Http Example project","0.1","Akka-Http Example","",Some(Contact("Ciaran O'Connor","www.ciaranoconnor.me","ciaranoconnor23@gmail.com")),None,Map()) //provides license and other description details
}
