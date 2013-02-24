package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone
      }
    }

    "return 400 when requesting nodes" in {
      running(FakeApplication()) {
        val nodes = route(FakeRequest(GET, "/nodes")).get

        status(nodes) must equalTo(OK)
        contentType(nodes) must beSome.which(_ == "application/json")
        //contentAsString(home) must contain ("Your new application is ready.")
      }
    }


    "return 400 when adding a new node with correct JSON" in {
      running(FakeApplication()) {
        val json = "{\"name\":\"pornserver\",\"ipaddress\":\"10.0.0.1\"}"
        val response = route(FakeRequest(POST, "/nodes").withJsonBody(Json.parse(json))).get

        status(response) must equalTo(OK)

      }
    }

    "return bad request when name is missing in the JSON request" in {
      running(FakeApplication()) {
        val json = "{\"ipaddress\":\"10.0.0.1\"}"
        val response = route(FakeRequest(POST, "/nodes").withJsonBody(Json.parse(json))).get

        status(response) must equalTo(BAD_REQUEST)

      }
    }


  }
}