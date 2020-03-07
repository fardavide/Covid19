package studio.forface.covid.domain.gateway

import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.Province

/**
 * Gateway to the data source
 * @author Davide Farella
 */
interface Repository {

    /** @return List of all the available [Country]s */
    suspend fun getCountries(): List<Country>

    /** @return List of all the [Province]s for the given [Country] */
    suspend fun getProvinces(country: Country): List<Province>
}