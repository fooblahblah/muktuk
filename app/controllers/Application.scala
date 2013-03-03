package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import daos.UrisDAO
import org.apache.commons.validator.routines.UrlValidator
import models.MiniUri
import utils.UriUtils

object Application extends Controller {
  val validator = new UrlValidator(Array("http", "https", "ftp"))

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }


  def shorten = Action { implicit request =>
    shortenerForm.bindFromRequest.fold(
      formWithErrors =>
        BadRequest("Invalid form submission. Must provide a uri."),
      uri => {
        if(validator.isValid(uri)) {
          UrisDAO.store(MiniUri(None, uri)) map { mini =>
            val shortUri = UriUtils.toUri(mini)
            Logger.debug(shortUri)
            Redirect(routes.Application.index)
          } getOrElse(BadRequest("Unable to store uri"))
        } else {
          BadRequest("Invalid uri (we only support http, https and ftp protocols)")
        }
      }
    )
  }


  def show(id: Long) = Action {
    UrisDAO.findById(id) match {
      case Some(mini) => Redirect(mini.uri)
      case _          => NotFound
    }
  }


  val shortenerForm = Form("uri" -> nonEmptyText)
}