package studio.forface.covid.domain.usecase

import kotlinx.coroutines.flow.Flow
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.Province
import studio.forface.covid.domain.gateway.Api
import studio.forface.covid.domain.gateway.Repository

/**
 * Get a list of all the available Provinces for a given Country sorted by their [Province.name]
 * @author Davide Farella
 */
class GetProvinces(private val repository: Repository, private val api: Api) {

    operator fun invoke(id: CountryId): Flow<List<Province>> =
        repository.getProvinces(id)

    operator fun invoke(country: Country) = this(country.id)
}
