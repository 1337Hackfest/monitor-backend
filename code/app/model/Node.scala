package model

import com.novus.salat._
import com.novus.salat.dao._

import com.mongodb.casbah.commons.Imports._
import com.mongodb.casbah.MongoConnection

import com.novus.salat.Context

import mongoContext._

case class Node(_id: ObjectId = new ObjectId, val name: String) {
   var nodeDetails: NodeDetails = _
   var nodeAccessInfo: NodeAccessInfo = _
}

object NodeDAO extends SalatDAO[Node, ObjectId](
    collection = MongoConnection()("monitor")("nodes"))

object Node {
    def all(): List[Node] = NodeDAO.find(MongoDBObject.empty).toList

    def create(name: String) {
        NodeDAO.insert(Node(name = name))
    }

    def delete(id: String) {
        NodeDAO.remove(MongoDBObject("_id" -> new ObjectId(id)))
    }
}
