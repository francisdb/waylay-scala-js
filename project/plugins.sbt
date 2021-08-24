addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.6.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.3")

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "requests" % "0.6.9",
  "com.typesafe.play" %% "play-json" % "2.9.2"
)
