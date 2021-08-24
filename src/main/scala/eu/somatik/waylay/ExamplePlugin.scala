package eu.somatik.waylay

import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.annotation.*
import scala.scalajs.js.JSON

trait RequiredProperties extends js.Object {
  val param1: js.UndefOr[String]
}
trait Options extends js.Object {
  val requiredProperties: RequiredProperties
}
object ExamplePlugin extends Plugin[Options]:

  override def run(options: Options): Future[(js.Error, Result)] =
    println("Hello from scala.js!")
    //println(s"Got options: ${JSON.stringify(options, space = 2)}")
    options.requiredProperties.param1.toOption match
      case Some(param1) =>
        val result = Result(
          Some("success"),
          Map(
            "foo" -> 1,
            "param1" -> param1
          ),
          Some("testMessage")
        )
        Future.successful((null, result))
      case None =>
        Future.successful((new js.Error("Missing param1"), null))
