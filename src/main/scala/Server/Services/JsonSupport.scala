package Server.Services

import Server.Models.TableRequest
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val tableFormat = jsonFormat1(TableRequest)
}
