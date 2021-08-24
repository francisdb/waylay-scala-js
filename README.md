# waylay-scala-js
POC of writing a waylay.io plugin using scala.js on Scala 3

Run below commands in the `sbt` console

## Test run script

```sbt
test
```

## Create script

```sbt
fullLinkJS
```

Will create `./target/scala-2.13/waylay-scala-js-opt/main.js` which for now you need to copy-paste into the Waylay console.

## Deploy

Makes ure you have `API_KEY` and `API_SECRET` env vars set

Set the correct endpoint:
```sbt
waylayApi := "https://plugs-staging.waylay.io"
```

Update `metadata.json` (TODO find a better way of versioning)

Publish the current version

```sbt
publishWaylayPlugin
```

## Test remotely

This will run the plugin script on the waylay side servers, make sure you have your credentails and api set up as described above.
