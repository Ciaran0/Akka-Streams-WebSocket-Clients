package me.ciaranoconnor.api

import javax.ws.rs.Path
import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.model.ws.{TextMessage}
import akka.stream.scaladsl.{Sink, Source}
import io.swagger.annotations._
import me.ciaranoconnor.models.User
import me.ciaranoconnor.streams.flows.MyData
import org.slf4j.LoggerFactory
import scala.util.Random


@Api(value = "/User", produces = "application/json", description = "Operations on users")
@Path("/User")
trait UserHttpService extends Directives with SprayJsonSupport {
  import User._

  val userRoutes = getUsers ~ getUser ~ addUser
  val log = LoggerFactory.getLogger(classOf[UserHttpService])
  implicit def system: ActorSystem


  @ApiOperation(value = "Get all users", notes = "Returns all users", nickname = "getUsers", httpMethod = "GET", response = classOf[Seq[User]], responseContainer = "List")
  @ApiImplicitParams(Array())
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "User retrieved"),
    new ApiResponse(code = 204, message = "No user found")
  ))
  def getUsers = {
    path("User") {
        get {
          pathEndOrSingleSlash {
            complete {
              //In a real application retrievedUsers would be retrieved from a database
              val retrievedUsers  = List(User(1, "Tom"),User(2,"bob"))
              system.eventStream.publish(MyData("test"))
              log.debug("Getting users")
              retrievedUsers match {
                case head::tail => retrievedUsers
                case Nil => StatusCodes.NoContent
              }
            }
          }
        }
    }
  }

  @Path("/{id}")
  @ApiOperation(value = "Find a user by ID", notes = "Returns a user based on ID", httpMethod = "GET", response = classOf[User])
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "userId", value = "ID of user that needs to be fetched", required = true, dataType = "integer", paramType = "path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "Pet not found"),
    new ApiResponse(code = 400, message = "Invalid ID supplied")
  ))
  def getUser = {
    path("User"/ IntNumber) { id => {
        get {
          complete(User(id,"Tom"))
        }
    }
    }
  }

  @Path("/{user}")
  @ApiOperation(value = "Add a new user", nickname = "addUser", httpMethod = "POST", consumes = "application/json, application/vnd.custom.user")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "body", value = "User object", dataType = "User", required = true, paramType = "body")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 405, message = "Invalid input"),
    new ApiResponse(code = 200, message = "User added")

  ))
  def addUser = post {
    path("User" / Segment) { id => complete(id)}
  }
}
