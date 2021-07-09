package eu.somatik.waylay

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobalScope

object Main {
  def main(args: Array[String]): Unit = {
    ExamplePlugin.run(Globals.options).map { case (err, result) =>
      Globals.send(err, result)
    }
  }
}

@js.native
@JSGlobalScope
object Globals extends js.Object {
  val options: js.Object = js.native
  def send(err: js.Any, result: js.Object): Unit = js.native
}
