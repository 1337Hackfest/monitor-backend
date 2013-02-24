package controllers

import play.api._
import libs.json.{JsResult, Json}
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

  def newNode = Action { request =>
    request.body.asJson.map { json =>
      val jsResult: JsResult[Node] = Json.fromJson[Node](json)

      jsResult.asOpt.map { node =>
        Created(Node.create(node.name, node.ipAddress).toString)
      }.getOrElse {
        BadRequest("Invalid node: " + jsResult)
      }
    }.getOrElse {
      BadRequest("Expecting JSON data")
    }
  }

  def deleteNode(id: ObjectId) = Action {
    Node.delete(id)
    Ok("Deleted node, " + id)
  }

  def updateNode(id: ObjectId) = Action { request =>
    request.body.asJson.map { json =>
      val jsResult: JsResult[Node] = Json.fromJson[Node](json)

      jsResult.asOpt.map { node =>
        Ok(Node.update(Node(id, node.name, node.ipAddress)).toString + " updating to: " + node.name + " " + node.ipAddress)
      }.getOrElse {
        BadRequest("Invalid node: " + jsResult)
      }
    }.getOrElse {
      BadRequest("Expecting JSON data")
    }
  }


}
