package muktuk.controllers

import muktuk.daos.MiniUriDAO
import muktuk.models.MiniUri
import muktuk.utils.Forms._
import muktuk.utils.Implicits._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  def index(uri: Option[String] = None, error: Option[String] = None) = Action {
    Ok(views.html.index(uri, error))
  }


  def shorten = Action { implicit request =>
    shortenerForm.bindFromRequest.fold(
      formWithErrors =>
        BadRequest(views.html.index(None, Some("Please provide a well-formed uri."))),

      uri => {
        MiniUriDAO.store(MiniUri(None, uri)) map { mini =>
          Redirect(routes.Application.index(Some(mini.toUri)))
        } getOrElse(BadRequest(views.html.index(None, Some("Error storing shortened uri."))))
      }
    )
  }


  def show(id: Long) = Action {
    MiniUriDAO.findById(id) match {
      case Some(mini) => Redirect(mini.uri)
      case _          => NotFound
    }
  }


  val shortenerForm = Form("uri" -> uri)
}
