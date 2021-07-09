package eu.somatik.waylay

import scala.concurrent.Future
import scala.scalajs.js

trait Plugin {
  def run(options: js.Object): Future[(js.Any, js.Object)]
}
