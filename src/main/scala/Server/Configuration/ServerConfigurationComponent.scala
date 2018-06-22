package Server.Configuration

trait ServerConfigurationComponent {
  def port: Int
}

trait ServerConfigurationComponentImpl extends ServerConfigurationComponent {
  this: ConfigurationComponent =>
  override def port: Int = config.getInt("server.port")
}
