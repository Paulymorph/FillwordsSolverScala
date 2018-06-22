package Server.Services

import akka.http.scaladsl.server.Route

trait Service {
  def router: Route
}

