enablePlugins(ScalaJSPlugin, WaylayPlugin)

organization := "eu.somatik.waylay-scala-js"
name := "waylay-scala-js"
scalaVersion := "2.13.5" // or any other Scala version >= 2.11.12

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies += "org.scalameta" %%% "munit" % "0.7.27" % Test

// The use of "Test / " allows the rest of your project to use a different module kind
Test / scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }

waylayApi := "https://plugs-staging.waylay.io"
