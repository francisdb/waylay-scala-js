package eu.somatik.waylay

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobalScope

@main
def main(): Unit =
  ExamplePlugin.run(Globals.options).map { case (err, result) =>
    Globals.send(err, result)
  }

@js.native
@JSGlobalScope
object Globals extends js.Object:
  val options: Options = js.native
  def send(err: js.Error, result: Result): Unit = js.native
