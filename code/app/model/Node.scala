package model

import com.mongodb.casbah.commons.MongoDBObject
import play.api.libs.json._
import play.api.libs.functional.syntax._

import play.api.Play.current
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import com.mongodb.casbah.commons.conversions.scala.RegisterConversionHelpers

case class Node(
                 _id: ObjectId = new ObjectId,
                 name: String,
                 ipAddress: String
                 )

object Node extends ModelCompanion[Node, ObjectId] {

  RegisterConversionHelpers()

  val dao = new SalatDAO[Node, ObjectId](collection = mongoCollection("nodes")) {}

  def all(): List[Node] = dao.find(MongoDBObject.empty).toList

  def findOne(id: ObjectId) = dao.findOneById(id)

  def create(name: String, ipAddress: String) {
    val node = new Node(new ObjectId(), name, ipAddress)
    dao.insert(node)
  }

  def delete(id: ObjectId) = dao.removeById(id)

  implicit val objectIdFormat = new Format[ObjectId] {
    def reads(jv: JsValue): JsResult[ObjectId] = {
      JsSuccess(new ObjectId(jv.as[String]))
    }

    def writes(o: ObjectId): JsValue = JsString(o.toString)
  }

  implicit val nodeFormat = (
    (__ \ "_id").format[ObjectId] and
      (__ \ "name").format[String] and
        (__ \ "ipaddress").format[String]
    )(Node.apply, unlift(Node.unapply))

}
