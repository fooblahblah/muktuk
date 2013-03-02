package models

import play.api.db.slick.Config.driver.simple._

case class MiniUri(id: Option[Long], uri: String)

object Uris extends Table[MiniUri]("uris") {
  def id       = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def uri      = column[String]("uri")
  def autoInc  = id.? ~ uri <> (MiniUri, MiniUri.unapply _) returning id
  def *        = id.? ~ uri <> (MiniUri, MiniUri.unapply _)
}

