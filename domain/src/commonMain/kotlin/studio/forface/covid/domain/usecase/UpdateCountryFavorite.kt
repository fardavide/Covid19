package studio.forface.covid.domain.usecase

import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.gateway.Repository

/**
 * Update favorite state for a Country
 *
 * Input:
 *  * [Country] and [Boolean] whether the Country must be favorited
 *
 * @author Davide Farella
 */
class UpdateCountryFavorite(
    private val repository: Repository
) {

    suspend operator fun invoke(country: Country, favorite: Boolean) {
        repository.updateFavorite(country.id, favorite)
    }
}
