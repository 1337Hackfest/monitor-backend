import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "monitor-backend"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "com.novus" % "salat_2.10" % "1.9.2-SNAPSHOT",
    "com.github.tmingos" % "casbah_2.10" % "2.5.0-SNAPSHOT",
    "se.radley" % "play-plugins-salat_2.10" % "1.2"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    routesImport += "se.radley.plugin.salat.Binders._",
    templatesImport += "org.bson.types.ObjectId"
  )

}
