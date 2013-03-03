package utils

import models.MiniUri
import play.api.mvc._
import controllers.routes

object UriUtils {

  def toUri(mini: MiniUri)(implicit request: Request[Any]): String = {
    println(request.headers("HOST"))
    routes.Application.show(mini.id.get).url
  }

}