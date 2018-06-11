package Server

import Server.Models.Table
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

trait RouterComponent {
  def router: Route
}

class RouterComponentImpl extends RouterComponent {
  import Server.JsonSupport._

  val router: Route = get {
    path("solve") {
      getSolution
    }
  }

  def getSolution: Route = parameters('table.as[Table]) { table =>
    complete(
      """
        |:(
        |Not implemented yet.
        |One day I will send the solution!
      """.stripMargin)
  }
}