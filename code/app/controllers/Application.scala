package controllers

import play.api._
import libs.json.Json
import play.api.mvc._
import model.Node

object Application extends Controller {

  def index = Action {
    Redirect(routes.Application.nodes())
  }

  def nodes = Action {
    Ok(Json.toJson(Node.all()))
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

  def deleteNode(id: Long) = TODO

  def updateNode(id: Long) = TODO


}
