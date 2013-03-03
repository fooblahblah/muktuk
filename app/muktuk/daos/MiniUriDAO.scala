package muktuk.daos

import java.sql.SQLException
import muktuk.models._
import play.api.Play.current
import play.api.Logger
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB

object MiniUriDAO {

  def store(s: MiniUri): Option[MiniUri] = {
    DB.withSession { implicit session =>
      session.withTransaction {
        try {
          findByUri(s.uri) orElse {
            Some(s.copy(id = Some(Uris.autoInc.insert(s))))
          }
        } catch {
          case e: SQLException =>
            Logger.error("Failed to store MiniUri", e)
            session.rollback
            None
        }
      }
    }
  }


  def findById(id: Long): Option[MiniUri] = {
    DB.withSession { implicit session =>
      Query(Uris) filter (_.id === id) firstOption
    }
  }


  def list(): List[MiniUri] = DB.withSession { implicit session =>
    Query(Uris) list
  }


  private[daos] def create(s: MiniUri)(implicit ss: Session): Option[MiniUri] = {
    Some(s.copy(id = Some(Uris.autoInc.insert(s))))
  }


  private[daos] def findByUri(uri: String)(implicit ss: Session): Option[MiniUri] = {
    Query(Uris) filter (_.uri === uri) firstOption
  }
}