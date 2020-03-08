package studio.forface.covid.data.remote

import io.ktor.client.engine.js.Js

actual val HttpClientEngine = Js.create()
