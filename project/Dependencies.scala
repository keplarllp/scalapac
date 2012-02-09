import sbt._

object Dependencies {
  val resolutionRepos = Seq(
    ScalaToolsSnapshots
  )

  object V {
    val commons     = "1.6"
  }

  object Libraries {
    // Used for request signing
    val codec       = "commons-codec" % "commons-codec"  % V.commons
  }
}