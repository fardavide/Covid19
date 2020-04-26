@file:Suppress("EXPERIMENTAL_API_USAGE")

package studio.forface.covid.domain.usecase.updates

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.callbackFlow
import studio.forface.covid.domain.entity.Version
import studio.forface.covid.domain.entity.compareTo
import studio.forface.covid.domain.gateway.UpdatesApi
import studio.forface.covid.domain.gateway.UpdatesRepository
import studio.forface.covid.domain.usecase.updates.DownloadUpdateIfAvailable.State

/**
 * Download and save locally the last update, if newed version is available
 *
 * This will check the last available version to download, compare with the installed on and the
 * already downloaded one and, if downloadable version is greater than the local ones, it will be
 * downloaded
 *
 * * Output:
 *  * Flow of [State]
 *
 * [State] can be:
 * * [State.Checking]
 * * [State.UpToDate]
 * * [State.AlreadyDownloaded]
 * * [State.Downloading] with [State.Downloading.progress] in Float
 * * [State.Completed]
 *
 *
 * @author Davide Farella
 */
class DownloadUpdateIfAvailable(
    private val api: UpdatesApi,
    private val repository: UpdatesRepository,
    private val getAppVersion: GetAppVersion,
    private val getInstallableUpdate: GetInstallableUpdate,
    private val buildDownloadableUpdateFileName: BuildDownloadableUpdateFileName
) {

    // TODO: convert to Channel
    suspend operator fun invoke() = callbackFlow {
        send(State.Checking)
        val versionToDownloadAsync = async { api.getLastUpdateVersion() }
        val downloadedVersionAsync = async { getInstallableUpdate()?.updateVersion ?: Version.Empty }

        // This is required to be completed before proceed
        val versionToDownload = versionToDownloadAsync.await()

        @Suppress("CascadeIf") // More readable than a when statement
        if (versionToDownload <= getAppVersion()) {
            send(State.UpToDate)

        } else if (versionToDownload <= downloadedVersionAsync.await()) {
            send(State.AlreadyDownloaded(versionToDownload))

        } else {
            send(State.Downloading(0f))
            // TODO: updated progress
            val name = buildDownloadableUpdateFileName(versionToDownload)
            repository.storeUpdate(api.getUpdateFile(name), name)

            send(State.Completed(versionToDownload))
        }
        close()
    }

    sealed class State {

        /** Checking whether an update must be downloaded */
        object Checking : State()

        /** App is up to date: no need to download */
        object UpToDate : State()

        /**
         * The app is not up to date, but the latest version is already downloaded and ready to be installed
         * Implements [ReadyToInstall]
         */
        class AlreadyDownloaded(override val version: Version) : State(), ReadyToInstall

        /**
         * The update is being downloaded
         * @property progress from 0.0 to 1.0
         */
        class Downloading(val progress: Float) : State()

        /**
         * The download is completed
         * Implements [ReadyToInstall]
         */
        class Completed(override val version: Version) : State(), ReadyToInstall


        /** The updated is ready to be installed */
        interface ReadyToInstall {
            val version: Version
        }
    }
}
