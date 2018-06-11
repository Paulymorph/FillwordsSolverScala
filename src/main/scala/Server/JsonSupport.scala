package Server

import Server.Models.Table
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

object JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val tableFormat = jsonFormat1(Table)
}
