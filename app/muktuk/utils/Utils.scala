package muktuk.utils

import muktuk.Global
import muktuk.models.MiniUri
import play.api.mvc._
import controllers.routes

object Implicits {

  implicit class EnrichedMiniUri(mini: MiniUri) {
    def toUri: String = {
      Global.externalUri + muktuk.controllers.routes.Application.show(mini.id.get).url
    }
  }

}