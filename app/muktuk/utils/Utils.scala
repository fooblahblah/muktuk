package muktuk.utils

import controllers.routes
import muktuk.Global
import muktuk.models.MiniUri
import org.apache.commons.validator.routines.UrlValidator
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation._
import play.api.mvc._

/**
 * Custom uri form validator
 */
object Forms {
  val validator            = new UrlValidator(Array("http", "https", "ftp"))
  val uri: Mapping[String] = text verifying (Constraints.nonEmpty, validUri)

  def validUri: Constraint[String] = Constraint[String]("constraint.uri") { o =>
    if (!validator.isValid(o)) Invalid(ValidationError("error.required")) else Valid
  }
}


/**
 * Enrichment of the MiniUri class
 */
object Implicits {

  implicit class EnrichedMiniUri(mini: MiniUri) {
    def toUri: String = {
      Global.externalUri + muktuk.controllers.routes.Application.show(mini.id.get).url
    }
  }

}