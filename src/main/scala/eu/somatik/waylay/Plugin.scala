package eu.somatik.waylay

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSGlobalScope

object Plugin {

  val options =
    if (js.typeOf(Globals.options) == "undefined")
      js.Dynamic.literal(
        "foo" -> 42,
        "tic" -> "toc"
      )
    else Globals.options

  // debug replacement for send()
  val send: (js.Any, js.Object) => Unit =
    if (js.typeOf(js.Dynamic.global.send) == "undefined") testSend
    else Globals.send

  def main(args: Array[String]): Unit = {
    println("Hello from scala.js!")
    println(s"Got options: ${JSON.stringify(options, space = 2)}")
    val value = js.Dynamic.literal(
      "observedState" -> "success",
      "rawData" -> js.Dynamic.literal(
        "message" -> "test",
        "foo" -> 1
      )
    )
    send(null, value)
  }

  def testSend(err: js.Any, result: js.Object): Unit =
    println(s"Debug send($err,${JSON.stringify(result, space = 2)})")

}

@js.native
@JSGlobalScope
object Globals extends js.Object {
  val options: js.Object = js.native
  def send(err: js.Any, result: js.Object): Unit = js.native
}
