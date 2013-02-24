package model

import com.mongodb.casbah.commons.Imports._
import com.mongodb.casbah.MongoConnection
import com.novus.salat._
import play.api.Play
import play.api.Play.current

/**
 * Adds the Play classloader to the salat context.
 */
object `package` {

  implicit val ctx = {
    val c = new Context {
      val name = "play-context"
    }

    c.registerClassLoader(Play.classloader)

    c
  }

  object DB {
    val connection = MongoConnection()("salat_play_db")
  }

}
