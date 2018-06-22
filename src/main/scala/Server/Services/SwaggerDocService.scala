package Server.Services

import Server.Configuration.ServerConfigurationComponent
import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info

class SwaggerDocService extends SwaggerHttpService {
  this: ServerConfigurationComponent =>
  override val apiClasses = Set(classOf[FillwordsSolverService])
  override val host = s"localhost:$port" //the url of your api, not swagger's json endpoint
  override val basePath = "/api"    //the basePath for the API you are exposing
  override val apiDocsPath = "swagger" //where you want the swagger-json endpoint exposed
  override val info = Info() //provides license and other description details
}

trait SwaggerHttpServiceComponent {
  def swaggerHttpService: SwaggerHttpService
}
