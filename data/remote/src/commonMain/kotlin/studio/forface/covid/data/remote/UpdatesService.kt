package studio.forface.covid.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import studio.forface.covid.data.remote.model.UpdateVersionApiModel
import studio.forface.covid.domain.util.checkNotBlank

internal class UpdatesService(
    private val client: HttpClient,
    private val regularHost: String,
    private val rawHost: String,
    private val configName: String
) {

    suspend fun getUpdateVersion(versionName: String) = getReleases()
        .split("\n")
        .first { it.startsWith(versionName) }
        .parseToUpdateVersion()

    suspend fun getLastUpdateVersion() = getReleases()
        .substringBefore("\n")
        .parseToUpdateVersion()

    // https://github.com/4face-studi0/Covid19/blob/master/releases/android-classic/release/Covid-android-classic_0.2-release-unsigned.apk
    suspend fun geUpdateFile(fileName: String) = client.get<ByteArray>(
        scheme = "https",
        host = regularHost,
        path = "$configName/$fileName"
    )

    // https://raw.githubusercontent.com/4face-studi0/Covid19/master/releases/android-classic/releases.txt
    private suspend fun getReleases() = client.get<String>(
        scheme = "https",
        host = rawHost,
        path = "$configName/releases.txt"
    ).checkNotBlank { "Cannot read file content" }

    private fun String.parseToUpdateVersion(): UpdateVersionApiModel {
        val (name, code, timestamp) = split("-")
        return UpdateVersionApiModel(name, code, timestamp)
    }
}
