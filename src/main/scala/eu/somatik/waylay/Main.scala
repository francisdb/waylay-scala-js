package eu.somatik.waylay

import eu.somatik.waylay.ExamplePlugin.run

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSGlobalScope

object Main {
  private val options =
    if (js.typeOf(Globals.options) == "undefined")
      js.Dynamic.literal(
        "foo" -> 42,
        "tic" -> "toc"
      )
    else Globals.options

  // debug replacement for send()
  private val send: (js.Any, js.Object) => Unit =
    if (js.typeOf(js.Dynamic.global.send) == "undefined") testSend
    else Globals.send

  def main(args: Array[String]): Unit = {
    run(options).map { case (err, result) => send(err, result) }
  }

  private def testSend(err: js.Any, result: js.Object): Unit =
    println(s"Debug send($err,${JSON.stringify(result, space = 2)})")

}

@js.native
@JSGlobalScope
object Globals extends js.Object {
  val options: js.Object = js.native
  def send(err: js.Any, result: js.Object): Unit = js.native
}
