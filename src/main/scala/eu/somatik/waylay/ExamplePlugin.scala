package eu.somatik.waylay

import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.JSON

object ExamplePlugin extends Plugin {

  override def run(options: js.Object): Future[(js.Any, js.Object)] = {
    println("Hello from scala.js!")
    println(s"Got options: ${JSON.stringify(options, space = 2)}")
    val value = js.Dynamic.literal(
      "observedState" -> "success",
      "rawData" -> js.Dynamic.literal(
        "message" -> "test",
        "foo" -> 1
      )
    )
    Future.successful((null, value))
  }
}
