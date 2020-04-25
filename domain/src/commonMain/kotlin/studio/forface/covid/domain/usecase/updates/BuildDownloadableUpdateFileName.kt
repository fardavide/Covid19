package studio.forface.covid.domain.usecase.updates

import studio.forface.covid.domain.entity.UpdateVersion

/**
 * Build the name of the update to download for a given versionName.
 * It's mean to be implemented on platform
 *
 * * Input:
 *  * [UpdateVersion]
 *  or
 *  * [String] version name
 *
 * * Output:
 *  * [String] full name of the file to download
 *
 *
 * @author Davide Farella
 */
interface BuildDownloadableUpdateFileName {

    operator fun invoke(updateVersion: UpdateVersion) = this(updateVersion.name)
    operator fun invoke(versionName: String): String
}
