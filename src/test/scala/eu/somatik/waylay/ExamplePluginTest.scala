package eu.somatik.waylay
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSON
import scalajs.js

class ExamplePluginTest extends munit.FunSuite {
  test("run") {
    val options = js.Object()
    ExamplePlugin.run(options).map { case (err, result) =>
      val resultAsJson = JSON.stringify(result)
      assertEquals(err, null)
      assertEquals(
        resultAsJson,
        """{"observedState":"success","rawData":{"message":"test","foo":1}}"""
      )
    }

  }
}
