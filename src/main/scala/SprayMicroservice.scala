import akka.actor.ActorSystem
import akka.event.Logging._
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.{BSONDateTime, BSONDocument, Macros, BSONObjectID}
import scala.concurrent.ExecutionContext.Implicits.global
import spray.httpx.SprayJsonSupport._
import spray.json._
import spray.routing.SimpleRoutingApp

case class Ping(_id: Option[BSONObjectID] = Some(BSONObjectID.generate), someString: String, someInt: Int, date: BSONDateTime)

case class Pings(pings: List[Ping], now: Long)

case class PingPostRequest(someString: String, someInt: Int)

object Converters extends DefaultJsonProtocol {
  // json converters...
  implicit val bsonObjectIDJsonFormat = new JsonFormat[BSONObjectID] {
    override def write(obj: BSONObjectID): JsValue = JsString(obj.stringify)
    override def read(json: JsValue): BSONObjectID = BSONObjectID(json.toString())
  }
  implicit val bsonDateTimeJsonFormat = new JsonFormat[BSONDateTime] {
    override def write(obj: BSONDateTime): JsValue = JsNumber(obj.value)
    override def read(json: JsValue): BSONDateTime = BSONDateTime(json.toString().toLong)
  }
  implicit val pingPostJsonFormat = jsonFormat4(Ping)
  implicit val pingPostRequestJsonFormat = jsonFormat2(PingPostRequest)
  implicit val pingsJsonFormat = jsonFormat2(Pings)

  // mongo converters...
  implicit val pingMongoHandler = Macros.handler[Ping]
}

object SprayMicroservice extends App with SimpleRoutingApp {
  import Converters._

  implicit val _ = ActorSystem()
  val driver = new MongoDriver
  val connection = driver.connection(List("localhost"))
  val db = connection("sprayMicroservice")
  val pings: BSONCollection = db("pings")

  startServer(interface = "0.0.0.0", port = 8002) {
    logRequestResponse("spray-microservice", InfoLevel) {
      path("pings") {
        get {
          parameters('limit ? 10, 'offset ? 0) { (limit, offset) =>
            complete {
              pings.find(BSONDocument()).cursor[Ping].collect[List]().map { pings =>
                val pingsTrimmed = pings.drop(offset).take(limit)
                val now = System.currentTimeMillis()
                Pings(pingsTrimmed, now)
              }
            }
          }
        } ~
        post {
          entity(as[PingPostRequest]) { pingPostRequest =>
            complete {
              val now = System.currentTimeMillis()
              val pingToInsert = Ping(_id = Some(BSONObjectID.generate), pingPostRequest.someString, pingPostRequest.someInt, BSONDateTime(now))
              pings.insert(pingToInsert).map { _ =>
                pingToInsert
              }
            }
          }
        }
      }
    }
  }
}
