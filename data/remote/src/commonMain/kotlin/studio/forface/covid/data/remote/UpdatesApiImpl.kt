package studio.forface.covid.data.remote

import studio.forface.covid.data.remote.mapper.UpdateVersionApiModelMapper
import studio.forface.covid.domain.gateway.UpdatesApi
import studio.forface.covid.domain.invoke

/**
 * Default implementation of [UpdatesApi]
 * @author Davide Farella
 */
internal class UpdatesApiImpl(
    private val service: UpdatesService,
    private val mapper: UpdateVersionApiModelMapper
) : UpdatesApi {

    override suspend fun getUpdateVersion(versionName: String) =
        mapper { service.getUpdateVersion(versionName).toEntity() }

    override suspend fun getLastUpdateVersion() =
        mapper { service.getLastUpdateVersion().toEntity() }

    override suspend fun getUpdateFile(fileName: String) = service.geUpdateFile(fileName)
}
