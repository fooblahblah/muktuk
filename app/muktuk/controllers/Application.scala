package muktuk.controllers

import muktuk.daos.MiniUriDAO
import muktuk.models.MiniUri
import muktuk.utils.Implicits._
import org.apache.commons.validator.routines.UrlValidator
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {
  val validator = new UrlValidator(Array("http", "https", "ftp"))

  def index(uri: Option[String] = None, error: Option[String] = None) = Action {
    Ok(views.html.index(uri, error))
  }


  def shorten = Action { implicit request =>
    shortenerForm.bindFromRequest.fold(
      formWithErrors =>
        BadRequest(views.html.index(None, Some("Invalid form submission. Must provide a uri."))),

      uri => {
        if(validator.isValid(uri)) {
          MiniUriDAO.store(MiniUri(None, uri)) map { mini =>
            Redirect(routes.Application.index(Some(mini.toUri)))
          } getOrElse(BadRequest(views.html.index(None, Some("Error storing shortened uri."))))
        } else {
          BadRequest(views.html.index(None, Some("Invalid uri (we only support http, https and ftp protocols)")))
        }
      }
    )
  }


  def show(id: Long) = Action {
    MiniUriDAO.findById(id) match {
      case Some(mini) => Redirect(mini.uri)
      case _          => NotFound
    }
  }


  val shortenerForm = Form("uri" -> nonEmptyText)
}