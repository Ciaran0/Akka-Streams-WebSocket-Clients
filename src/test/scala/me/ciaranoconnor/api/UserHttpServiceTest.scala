package me.ciaranoconnor.api

import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import me.ciaranoconnor.models.User
import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.server._
import Directives._


class UserHttpServiceTest extends WordSpec with Matchers with UserHttpService with ScalatestRouteTest {

  "The service" should  {
    "return a JSON list showing all users" in  {
        Get("/User") ~> getUsers ~> check {
          assert(contentType.mediaType.isApplication)
          //Check content type
          contentType.toString should include("application/json")
          //check response is of the right type
          val response = responseAs[List[User]]
          //check the response size and content
          response.size should equal(2)
          response.head.name should equal("Tom")
          //Check http status
          status should equal(StatusCodes.OK)
        }
    }
  }
}
