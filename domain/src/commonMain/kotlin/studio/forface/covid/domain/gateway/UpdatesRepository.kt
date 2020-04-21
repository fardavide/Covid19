package studio.forface.covid.domain.gateway

import studio.forface.covid.domain.entity.File

/**
 * Gateway to the locally cached Updates source
 *
 * @author Davide Farella
 */
interface UpdatesRepository {

    /** @return [File] from downloaded update package */
    suspend fun getUpdate(): File?

    /** This delete the downloaded update package and save the new [data] with given [name] */
    suspend fun storeUpdate(data: ByteArray, name: String)
}
