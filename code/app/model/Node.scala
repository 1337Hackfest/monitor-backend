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
                 id: ObjectId = new ObjectId,
                 label: String
                 )

object Node extends ModelCompanion[Node, ObjectId] {

  RegisterConversionHelpers()

  val dao = new SalatDAO[Node, ObjectId](collection = mongoCollection("nodes")) {}

  def all(): List[Node] = dao.find(MongoDBObject.empty).toList

  def create(label: String) {
    val node = new Node(new ObjectId(), label)
    dao.insert(node)
  }

  def delete(id: Long) {}

  implicit val objectIdFormat = new Format[ObjectId] {
    def reads(jv: JsValue): JsResult[ObjectId] = {
      JsSuccess(new ObjectId(jv.as[String]))
    }

    def writes(o: ObjectId): JsValue = JsString(o.toString)
  }

  implicit val nodeFormat = (
    (__ \ "id").format[ObjectId] and
      (__ \ "label").format[String]
    )(Node.apply, unlift(Node.unapply))


}
