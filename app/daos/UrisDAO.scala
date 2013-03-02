package daos

import models.MiniUri
import models.MiniUri
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import models._

object UrisDAO {

  def insert(s: MiniUri): Option[MiniUri] = {
    DB.withSession { implicit session =>
      Some(s.copy(id = Some(Uris.autoInc.insert(s))))
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

}