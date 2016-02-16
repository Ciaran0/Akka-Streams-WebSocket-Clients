package me.ciaranoconnor.app

import akka.actor.ActorSystem

trait Core {
  implicit def system: ActorSystem
}

trait BootedCore extends Core {

  /**
    * Construct the ActorSystem we will use in our application
    */
  implicit lazy val system = ActorSystem("akka-http")


  /**
    * Ensure that the constructed ActorSystem is shut down when the JVM shuts down
    */
  sys.addShutdownHook(system.terminate())

}
