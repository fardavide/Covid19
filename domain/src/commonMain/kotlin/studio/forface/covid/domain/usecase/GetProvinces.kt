package studio.forface.covid.domain.usecase

import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.Province
import studio.forface.covid.domain.gateway.Repository

/**
 * Get a list of all the available Provinces for a given Country sorted by their [Province.name]
 * @author Davide Farella
 */
class GetProvinces(private val repository: Repository) {

    suspend operator fun invoke(country: Country): List<Province> {
        return repository.getProvinces(country).sortedBy { it.name }
    }
}