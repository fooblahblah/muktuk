package muktuk.daos

import org.specs2.execute.AsResult
import org.specs2.mutable.Specification
import org.specs2.specification.AroundExample
import play.api.test._
import play.api.test.Helpers._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import muktuk.models.MiniUri

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
      MiniUriDAO.store(uri).map(_.id) must beSome
    }

    "store only unique uri records" in {
      val uri    = MiniUri(None, "http://www.google.com")
      val optUri = MiniUriDAO.store(uri)
      MiniUriDAO.store(uri) === optUri
    }

    "find MiniUri record by id" in {
      val uri = MiniUri(None, "http://www.google.com")
      val id  = MiniUriDAO.store(uri).flatMap(_.id).getOrElse(sys.error("failed to store uri"))
      MiniUriDAO.findById(id) === Some(uri.copy(id = Some(id)))
    }

    "list MiniUri records" in {
      MiniUriDAO.store(MiniUri(None, "http://www.google.com"))
      MiniUriDAO.store(MiniUri(None, "http://www.github.com"))
      MiniUriDAO.list.length === 2
    }
  }
}