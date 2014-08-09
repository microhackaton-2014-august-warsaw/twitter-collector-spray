package spraytwittercollector

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp
import akka.event.Logging

object SprayMicroservice extends App with SimpleRoutingApp {
  implicit val system = ActorSystem()

  val log = Logging(system, SprayMicroservice.getClass)

  startServer(interface = "0.0.0.0", port = 8002) {
   path("ping") {
     get {
       complete {
         log.info("Returning pong.")
         "pong"
       }
     }
   }
  }
}
