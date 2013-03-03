import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "muktuk"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "commons-validator" % "commons-validator"     % "1.4.0",
    "mysql"             %  "mysql-connector-java" % "5.1.21",
    jdbc,
    anorm
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
  ).dependsOn(RootProject( uri("git://github.com/freekh/play-slick.git") ))

}
