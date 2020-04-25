package studio.forface.covid.domain.usecase.updates

import studio.forface.covid.domain.entity.Version
import studio.forface.covid.domain.entity.compareTo
import studio.forface.covid.domain.gateway.UpdatesApi

/**
 * Get whether an update for the app is available to download
 * @author Davide Farella
 */
class IsUpdateAvailableToDownload(
    private val api: UpdatesApi,
    private val getInstallableUpdate: GetInstallableUpdate
) {

    suspend operator fun invoke() = api.getLastUpdateVersion() > (getInstallableUpdate() ?: Version.Empty)
}
