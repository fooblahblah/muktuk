package muktuk.controllers

import org.specs2.execute.AsResult
import org.specs2.mutable.Specification
import org.specs2.specification.AroundExample
import play.api.test._
import play.api.test.Helpers._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import muktuk.models.MiniUri
import play.api.mvc.Results
import muktuk.daos.MiniUriDAO

@RunWith(classOf[JUnitRunner])
class ApplicationControllerSpec extends Specification with AroundExample {

  def application = FakeApplication(additionalConfiguration =
    inMemoryDatabase() + ("logger.application" -> "OFF"))

  def around[R : AsResult](r: => R) = {
    running(application) {
      AsResult(r)
    }
  }

  "shortener" should {
    "require uri field as non-empty, valid uri" in {
      status(route(FakeRequest(POST, "/shorten")).get) === 400
      status(route(FakeRequest(POST, "/shorten").withFormUrlEncodedBody("uri" -> "www.foo.org")).get) === 400
    }

    "store shortened uri" in {
      val result = route(FakeRequest(POST, "/shorten").withFormUrlEncodedBody("uri" -> "http://www.google.com")).get
      status(result) === 303
    }

    "return 404 for non-existent records" in {
      val result = route(FakeRequest(GET, "/1")).get
      status(result) === 404
    }

    "redirect to shortened uri" in {
      MiniUriDAO.store(MiniUri(None, "http://www.fooblahblah.org"))
      val result = route(FakeRequest(GET, "/1")).get
      status(result) === 303
    }
  }
}