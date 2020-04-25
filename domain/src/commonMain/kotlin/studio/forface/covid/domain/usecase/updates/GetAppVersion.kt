package studio.forface.covid.domain.usecase.updates

import studio.forface.covid.domain.entity.Version

/**
 * Get current [Version] of the App
 * This is meant to be implemented in the platform
 *
 * @author Davide Farella
 */
interface GetAppVersion {
    operator fun invoke(): Version
}
