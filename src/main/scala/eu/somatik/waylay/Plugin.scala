package eu.somatik.waylay

import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportAll

object Result:
  def apply(observedState: Option[String], rawData: Map[String, js.Any], message: Option[String] = None) = new Result(
    observedState.getOrElse(js.undefined),
    js.Dictionary(rawData.toSeq*),
    message.getOrElse(js.undefined)
  )

class Result(
    val observedState: js.UndefOr[String],
    val rawData: js.Dictionary[js.Any],
    val message: js.UndefOr[String]
) extends js.Object

trait Plugin[O <: js.Object]:
  def run(options: O): Future[(js.Error, Result)]
