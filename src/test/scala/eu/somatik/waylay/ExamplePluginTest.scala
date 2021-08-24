package eu.somatik.waylay
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.{JSON, UndefOr}
import scalajs.js

class ExamplePluginTest extends munit.FunSuite:

  test("succeed if param1 is provided") {
    object TestOptions extends Options {
      override val requiredProperties: RequiredProperties = new RequiredProperties {
        override val param1: UndefOr[String] = "testValue"
      }
    }
    ExamplePlugin.run(TestOptions).map { case (err, result) =>
      assertEquals(err, null)
      assertEquals(result.observedState, "success")
      assertEquals(result.rawData.toMap, Map[String, js.Any]("foo" -> 1, "param1" -> "testValue"))
      assertEquals(result.message, "testMessage")
    }
  }

  test("fail if param1 is missing") {
    val options = new Options {
      override val requiredProperties: RequiredProperties = new RequiredProperties {
        override val param1: UndefOr[String] = js.undefined
      }
    }
    ExamplePlugin.run(options).map { case (err, result) =>
      assert(err != null)
      assertEquals(err.message, "Missing param1")
    }
  }
