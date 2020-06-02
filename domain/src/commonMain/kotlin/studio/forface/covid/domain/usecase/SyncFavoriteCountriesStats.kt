package studio.forface.covid.domain.usecase

import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.gateway.Api
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.util.mapAsync

/**
 * Sync Country Stats of Favorite countries on local cache [Repository] from remote source [Api]
 * @author Davide Farella
 */
class SyncFavoriteCountriesStats(private val api: Api, private val repository: Repository) {

    suspend operator fun invoke() {
//        val stats = repository.getFavoriteCountries().first()
//            .mapAsync { api.getCountryStat(it.id) }
//        repository.store(stats)
    }
}
