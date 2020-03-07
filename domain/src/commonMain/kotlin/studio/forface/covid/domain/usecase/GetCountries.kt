package studio.forface.covid.domain.usecase

import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.gateway.Repository

/**
 * Get a list of all the available countries sorted by their [Country.name]
 * @author Davide Farella
 */
class GetCountries(private val repository: Repository) {

    suspend operator fun invoke(): List<Country> {
        return repository.getCountries().sortedBy { it.name }
    }
}