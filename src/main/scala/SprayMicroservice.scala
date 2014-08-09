import akka.actor.ActorSystem
import akka.event.Logging._
import akka.event.LoggingAdapter
import spray.routing.SimpleRoutingApp
import akka.event.Logging

object SprayMicroservice extends App with SimpleRoutingApp {
  implicit val system = ActorSystem()

   val  log :LoggingAdapter = Logging.getLogger(system.eventStream, "my.string")

  startServer(interface = "0.0.0.0", port = 8002) {
    logRequestResponse("spray-microservice", InfoLevel) {
      path("ping") {
        get {
          complete {
             log.debug("teest")
            "pong"
          }
        }
      }
    }
  }
}
