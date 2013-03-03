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
    "store uri records" in {
      val uri = MiniUri(None, "http://www.google.com")
      UrisDAO.store(uri).map(_.id) must beSome
    }

    "store only unique uri records" in {
      val uri    = MiniUri(None, "http://www.google.com")
      val optUri = UrisDAO.store(uri)
      UrisDAO.store(uri) === optUri
    }

    "find MiniUri record by id" in {
      val uri = MiniUri(None, "http://www.google.com")
      val id  = UrisDAO.store(uri).flatMap(_.id).getOrElse(sys.error("failed to store uri"))
      UrisDAO.findById(id) === Some(uri.copy(id = Some(id)))
    }

    "list MiniUri records" in {
      UrisDAO.store(MiniUri(None, "http://www.google.com"))
      UrisDAO.store(MiniUri(None, "http://www.github.com"))
      UrisDAO.list.length === 2
    }
  }
}