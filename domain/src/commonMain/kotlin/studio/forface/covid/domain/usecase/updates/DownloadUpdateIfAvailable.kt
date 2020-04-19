package studio.forface.covid.domain.usecase.updates

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import studio.forface.covid.domain.entity.Version
import studio.forface.covid.domain.entity.compareTo
import studio.forface.covid.domain.gateway.UpdatesApi
import studio.forface.covid.domain.gateway.UpdatesRepository

/**
 * Download and save locally the last update, if newed version is available
 *
 * This will check the last available version to download, compare with the already downloaded one and,
 * if downloadable version is greater than the downloaded one, download it
 *
 *
 * @author Davide Farella
 */
class DownloadUpdateIfAvailable(
    private val api: UpdatesApi,
    private val repository: UpdatesRepository,
    private val getInstallableUpdate: GetInstallableUpdate,
    private val buildDownloadableUpdateFileName: BuildDownloadableUpdateFileName
) {

    suspend operator fun invoke() = coroutineScope {
        val versionToDownload = async { api.getLastUpdateVersion() }
        val downloadedVersion = async { getInstallableUpdate()?.updateVersion ?: Version.Empty }

        if (versionToDownload.await() > downloadedVersion.await()) {
            val name = buildDownloadableUpdateFileName(versionToDownload.getCompleted())
            repository.storeUpdate(api.getUpdateFile(name), name)
        }
    }
}