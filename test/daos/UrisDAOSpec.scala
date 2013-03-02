package daos

import org.specs2.execute.AsResult
import org.specs2.mutable.Specification
import org.specs2.specification.AroundExample
import play.api.test._
import play.api.test.Helpers._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import models.MiniUri

@RunWith(classOf[JUnitRunner])
class UrisDAOSpec extends Specification with AroundExample {

  def application = FakeApplication(additionalConfiguration = inMemoryDatabase())

  def around[R : AsResult](r: => R) = {
    running(application) {
      AsResult(r)
    }
  }

  "shortener" should {
    "insert MiniUri records" in {
      val uri = MiniUri(None, "http://www.google.com")
      UrisDAO.insert(uri).map(_.id) must beSome
    }

    "find MiniUri record by id" in {
      val uri = MiniUri(None, "http://www.google.com")
      val id  = UrisDAO.insert(uri).flatMap(_.id).getOrElse(sys.error("failed to insert uri"))
      UrisDAO.findById(id) must beSome(uri.copy(id = Some(id)))
    }

    "list MiniUri records" in {
      UrisDAO.insert(MiniUri(None, "http://www.google.com"))
      UrisDAO.insert(MiniUri(None, "http://www.github.com"))
      UrisDAO.list.length === 2
    }
  }
}