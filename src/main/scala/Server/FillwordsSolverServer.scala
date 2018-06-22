package Server

import Server.Services.{FillwordsSolverServiceComponent, SwaggerHttpServiceComponent}
import akka.http.scaladsl.server.{HttpApp, Route}

class FillwordsSolverServer extends HttpApp {
  this: FillwordsSolverServiceComponent
    with SwaggerHttpServiceComponent =>

  override def routes: Route =
    pathPrefix("api") {
      fillwordsSolverService.router
    } ~ swaggerHttpService.routes
}
