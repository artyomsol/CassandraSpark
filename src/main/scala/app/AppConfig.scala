package app

import com.typesafe.config.{Config, ConfigFactory}

import scala.util.Try

class AppConfig(val config: Config) {
  val appConfig = config.getConfig("app")
  val appName = Try(appConfig.getString("name")).getOrElse("MyApp")
}

object AppConfig {
  def apply() = new AppConfig(ConfigFactory.load())

  def apply(cnf: Config) = new AppConfig(cnf)

  def apply(resourceBaseName: String): AppConfig = {
    val cfg = ConfigFactory.load(resourceBaseName).withFallback(ConfigFactory.load())
    new AppConfig(cfg)
  }

}
