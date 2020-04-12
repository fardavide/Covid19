package studio.forface.covid.data.remote

import io.ktor.client.engine.android.Android

actual val HttpClientEngine = Android.create()
