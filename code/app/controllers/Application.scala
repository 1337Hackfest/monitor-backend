package controllers

import play.api._
import play.api.mvc._
import model.Node

object Application extends Controller {

  def listNodes = Action {
    val myNode = new Node("server1")
    Ok(myNode.name)
  }

  def addNode (id : Long) = Action {
    Ok("got node " + id)
  }

  def deleteNode(id : Long) = TODO

  def updateNode(id : Long) = TODO


}
