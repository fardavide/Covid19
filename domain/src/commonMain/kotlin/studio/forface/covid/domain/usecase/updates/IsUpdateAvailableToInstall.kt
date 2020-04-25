package studio.forface.covid.domain.usecase.updates

import studio.forface.covid.domain.entity.Version
import studio.forface.covid.domain.entity.compareTo

/**
 * Get whether an update for the app is available to install
 * @author Davide Farella
 */
class IsUpdateAvailableToInstall(
    private val getInstallableUpdate: GetInstallableUpdate,
    private val getAppVersion: GetAppVersion
) {

    suspend operator fun invoke() = (getInstallableUpdate() ?: Version.Empty) > getAppVersion()
}
