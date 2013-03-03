package muktuk

import play.api.Play
import Play.current
import play.api.GlobalSettings

object Global extends GlobalSettings {
  lazy val config = Play.configuration

  lazy val externalUri = config.getString("external_uri").getOrElse(sys.error("external_uri should be defined in configuration or -Dexternal_uri="))
}