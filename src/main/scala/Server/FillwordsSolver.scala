package Server

import Server.Configuration.{ConfigurationComponentImpl, ServerConfigurationComponentImpl}
import Server.Services._
import com.github.swagger.akka.SwaggerHttpService

object FillwordsSolver {

  def main(args: Array[String]) {
    val serverConfigComponent = new ServerConfigurationComponentImpl with ConfigurationComponentImpl

    trait SwaggerHttpServiceComponentImpl extends SwaggerHttpServiceComponent {
      override def swaggerHttpService = new SwaggerDocService
        with ServerConfigurationComponentImpl
        with ConfigurationComponentImpl
    }

    val server = new FillwordsSolverServer
      with FillwordsSolverServiceComponentImpl
      with SwaggerHttpServiceComponentImpl

    server.startServer("localhost", serverConfigComponent.port)
  }
}