package studio.forface.covid.domain.usecase

import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.gateway.Api
import studio.forface.covid.domain.gateway.Repository

/**
 * Sync Provinces or a given Country on local cache [Repository] from remote source [Api]
 * @author Davide Farella
 */
class SyncProvinces(private val api: Api, private val repository: Repository) {

    suspend operator fun invoke(id: CountryId) {
        repository.storeProvinces(api.getProvinces(id))
    }

    suspend operator fun invoke(country: Country) {
        this(country.id)
    }
}
