import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import play.api.libs.json.JsObject
import play.api.libs.json.Json.obj
import sbt.{AutoPlugin, Compile, File, IO, settingKey, taskKey}
import sbt.Keys.streams

import java.nio.charset.StandardCharsets
import java.time.Duration

object WaylayPlugin extends AutoPlugin {

  object autoImport {
    val waylayApi = settingKey[String]("Waylay api base (without trailing slash)")
    val waylayApiKey = settingKey[String]("Waylay api key")
    val waylayApiSecret = settingKey[String]("Waylay api secret")
    val publishWaylayPlugin = taskKey[Unit]("Publishes the plugin to your waylay environment.")
  }

  import autoImport._

  import play.api.libs.json.Json

  override lazy val globalSettings = Seq(
    waylayApiKey := Option(System.getenv("API_KEY")).getOrElse(""),
    waylayApiSecret := Option(System.getenv("API_SECRET")).getOrElse("")
  )

  override lazy val projectSettings = Seq(
    publishWaylayPlugin := {
      val log = streams.value.log
      // TODO find a cleaner way to get this
      val fileName = (Compile / fullLinkJS).value.data.publicModules.head.jsFileName
      val scriptFile = (Compile / fullLinkJS / scalaJSLinkerOutputDirectory).value.toPath.resolve(fileName)

      val metaFileContents = IO.read(new File("metadata.json"), StandardCharsets.UTF_8)
      val metaJson = Json.parse(metaFileContents).as[JsObject]
      val scriptFileContents = IO.read(scriptFile.toFile, StandardCharsets.UTF_8)
      val pluginJson = metaJson ++ obj(
        "script" -> scriptFileContents
      )

      log.info(
        s"Publishing ${(pluginJson \ "type").as[String]} ${(pluginJson \ "name").as[String]}:${(pluginJson \ "version").as[String]}"
      )
      log.info(s"Script from $scriptFile")

      // TODO we probably want to use the v1 api?
      val url = (metaJson \ "type").as[String] match {
        case "sensor"   => waylayApi.value + "/v0/sensors"
        case "actuator" => waylayApi.value + "/v0/actions"
        case other      => throw new RuntimeException(s"Don't know whow to handle plugin type $other")
      }

      if (waylayApiKey.value.isEmpty) {
        throw new RuntimeException("Missing API_KEY env var or waylayApiKey configuration")
      }
      if (waylayApiSecret.value.isEmpty) {
        throw new RuntimeException("Missing API_SECRET env var or waylayApiSecret configuration")
      }

      log.info(s"POST $url")
      val r = requests.post(
        url,
        (waylayApiKey.value, waylayApiSecret.value),
        headers = Map("Content-Type" -> "application/json"),
        data = Json.stringify(pluginJson),
        readTimeout = Duration.ofMinutes(2).toMillis.toInt
      )
      log.info(s"status: ${r.statusCode}")
      log.info(s"response: ${r.text}")
    }
  )
}
