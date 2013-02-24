package controllers

import play.api._
import libs.json.Json
import play.api.mvc._
import model.Node
import org.bson.types.ObjectId

object Application extends Controller {

  def index = Action {
    Redirect(routes.Application.nodes())
  }

  def nodes = Action {
    Ok(Json.toJson(Node.all()))
  }


  def getNode(id: ObjectId) = Action {
    Ok(Json.toJson(Node.findOne(id)))
  }

  def newNode = Action {
    request =>
      request.body.asJson.map {
        json =>
          (json \ "label").asOpt[String].map {
            label =>
              Node.create(label)
              Ok("Created node with label " + label)
          }.getOrElse {
            BadRequest("Missing parameter label")
          }
    }.getOrElse {
      BadRequest("Expecting JSON data")
    }
  }

  def deleteNode(id: ObjectId) = Action {
    Node.delete(id)
    Ok("Deleted node, " + id)
  }

  def updateNode(id: Long) = TODO


}
