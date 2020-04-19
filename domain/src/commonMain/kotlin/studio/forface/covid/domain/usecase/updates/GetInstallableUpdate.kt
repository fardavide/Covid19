package studio.forface.covid.domain.usecase.updates

import studio.forface.covid.domain.entity.InstallableUpdateVersion
import studio.forface.covid.domain.gateway.UpdatesApi
import studio.forface.covid.domain.gateway.UpdatesRepository

/**
 * Get [InstallableUpdateVersion] downloaded and available on local memory
 * Can return `null` if no File is available
 *
 * @author Davide Farella
 */
class GetInstallableUpdate (
    private val repository: UpdatesRepository,
    private val api: UpdatesApi,
    private val buildDownloadableUpdateFileName: BuildDownloadableUpdateFileName
) {

    suspend operator fun invoke(): InstallableUpdateVersion? {
        val file = repository.getUpdate() ?: return null
        // Create a filename using a dummy string as replacement of the version, then take the
        // part before and after that replacement and remove them from the filename
        val fileVersionName = buildDownloadableUpdateFileName(VERSION_REPLACEMENT).let {
            val (pre, post) = it.split(VERSION_REPLACEMENT)
            file.name.substringAfter(pre).substringBefore(post)
        }
        val fileVersion = api.getUpdateVersion(fileVersionName)
        return InstallableUpdateVersion(file = file.bufferedSource(), updateVersion = fileVersion)
    }

    private companion object {
        const val VERSION_REPLACEMENT = "###"
    }
}