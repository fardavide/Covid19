package studio.forface.covid.domain.usecase.updates

import studio.forface.covid.domain.entity.UpdateVersion
import studio.forface.covid.domain.gateway.UpdatesApi
import studio.forface.covid.domain.gateway.UpdatesRepository

/**
 * Download and save locally the last update
 *
 * * Input:
 *  * [UpdateVersion] to download
 *
 * @author Davide Farella
 */
class DownloadUpdate(
    private val api: UpdatesApi,
    private val repository: UpdatesRepository,
    private val buildDownloadableUpdateFileName: BuildDownloadableUpdateFileName
) {

    suspend operator fun invoke(version: UpdateVersion) {
        val fileName = buildDownloadableUpdateFileName(version.name)
        repository.storeUpdate(api.getUpdateFile(fileName), fileName)
    }
}
