package studio.forface.covid.domain.usecase

import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.gateway.Api
import studio.forface.covid.domain.gateway.Repository

/**
 * Sync Country Full Stat - for given [CountryId] - on local cache [Repository] from remote source [Api]
 * @author Davide Farella
 */
class SyncCountryFullStat(private val api: Api, private val repository: Repository) {

    suspend operator fun invoke(id: CountryId) {
        val stats = api.getCountryFullStat(id)
        repository.store(stats)
    }
}
