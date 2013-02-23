package controllers

import play.api._
import play.api.mvc._
import model.Node

object Application extends Controller {

  def nodes = Action {
    val myNode = new Node
    myNode.name = "server1"

    Ok(myNode.name)
  }
  
}