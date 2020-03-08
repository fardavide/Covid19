package studio.forface.covid.domain.gateway

import studio.forface.covid.domain.entity.*

/**
 * Gateway to the remote data source
 * This will get data from the remote source
 *
 * @author Davide Farella
 */
interface Api {

    /** @return List of all the available [Country]s */
    suspend fun getCountries(): List<Country>

    /** @return List of all the [Province]s for the given [CountryId] */
    suspend fun getProvinces(id: CountryId): List<Province>

    // * * * STATS * * * //

    suspend fun getWorldStat(): WorldStat
    suspend fun getWorldFullStat(): WorldFullStat
    suspend fun getCountrySmallStat(id: CountryId): CountrySmallStat
    suspend fun getCountryStat(id: CountryId): CountryStat
    suspend fun getCountryFullStat(id: CountryId): CountryFullStat
    suspend fun getProvinceStat(id: ProvinceId): ProvinceStat
    suspend fun getProvinceFullStat(id: ProvinceId): ProvinceFullStat
}
