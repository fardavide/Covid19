package studio.forface.covid.domain.gateway

import okio.BufferedSource
import studio.forface.covid.domain.entity.UpdateVersion

/**
 * Gateway to the remote updates source
 * This will get updates from the remote source
 *
 * @author Davide Farella
 */
interface UpdatesApi {

    /** @return [UpdateVersion] for given [versionName] */
    suspend fun getUpdateVersion(versionName: String): UpdateVersion

    /** @return last available [UpdateVersion] */
    suspend fun getLastUpdateVersion(): UpdateVersion

    /** @return [BufferedSource] of update File */
    suspend fun getUpdateFile(fileName: String): BufferedSource
}