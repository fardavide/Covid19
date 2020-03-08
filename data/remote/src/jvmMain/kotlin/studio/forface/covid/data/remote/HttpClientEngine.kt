package studio.forface.covid.data.remote

import io.ktor.client.engine.apache.Apache

actual val HttpClientEngine = Apache.create()
