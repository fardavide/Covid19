package studio.forface.covid.domain.usecase

import studio.forface.covid.domain.gateway.Api
import studio.forface.covid.domain.gateway.Repository

/**
 * Sync World Stat on local cache [Repository] from remote source [Api]
 * @author Davide Farella
 */
class SyncWorldStat(private val api: Api, private val repository: Repository) {

    suspend operator fun invoke() {
        repository.store(api.getWorldStat())
    }
}
