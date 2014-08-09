import akka.actor.ActorSystem
import akka.event.Logging._
import spray.routing.SimpleRoutingApp

object SprayMicroservice extends App with SimpleRoutingApp {
  implicit val _ = ActorSystem()

  startServer(interface = "0.0.0.0", port = 8002) {
    logRequestResponse("spray-microservice", InfoLevel) {
      path("ping") {
        get {
          complete {
            "pong"
          }
        }
      }
    }
  }
}
