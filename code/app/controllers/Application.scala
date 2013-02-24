package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import model._

object Application extends Controller {
  
  val nodeForm = Form (
    "name" -> nonEmptyText
  )

  def index = Action {
    Redirect(routes.Application.listNodes)
  }

  def listNodes = Action {
    Ok(views.html.index(Node.all(), nodeForm))
  }

  def addNode = Action { implicit request =>
    nodeForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Node.all(), errors)),
      name => {
        Node.create(name)
        Redirect(routes.Application.listNodes)
      }
    )
  }

  def deleteNode(id: String) = Action {
    Node.delete(id)
    Redirect(routes.Application.listNodes)
  }

  def updateNode(id : Long) = TODO


}
