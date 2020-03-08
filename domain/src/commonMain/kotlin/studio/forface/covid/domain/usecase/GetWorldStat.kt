package studio.forface.covid.domain.usecase

import kotlinx.coroutines.flow.Flow
import studio.forface.covid.domain.entity.WorldStat
import studio.forface.covid.domain.gateway.Api
import studio.forface.covid.domain.gateway.Repository

/**
 * Get [WorldStat]
 */
class GetWorldStat(private val repository: Repository, private val api: Api) {

    operator fun invoke(): Flow<WorldStat> =
        repository.getWorldStat()
}
