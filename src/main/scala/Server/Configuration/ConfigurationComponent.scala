package Server.Configuration

import com.typesafe.config.{Config, ConfigFactory}

trait ConfigurationComponent {
  def config: Config
}

trait ConfigurationComponentImpl extends ConfigurationComponent {
  override def config: Config = ConfigFactory.load()
}
