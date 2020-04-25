package studio.forface.covid.domain.gateway

import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryFullStat
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.CountrySmallStat
import studio.forface.covid.domain.entity.CountryStat
import studio.forface.covid.domain.entity.Province
import studio.forface.covid.domain.entity.ProvinceFullStat
import studio.forface.covid.domain.entity.ProvinceId
import studio.forface.covid.domain.entity.ProvinceStat
import studio.forface.covid.domain.entity.WorldFullStat
import studio.forface.covid.domain.entity.WorldStat

/**
 * Gateway to the remote data source
 * This will get data from the remote source
 *
 * @author Davide Farella
 */
interface Api {

    /** @return List of all the available [Country]s */
    suspend fun getCountries(): List<Country>

    /** @return [Country] for the given [CountryId] */
    suspend fun getCountry(id: CountryId): Country

    /** @return List of all the [Province]s for the given [CountryId] */
    suspend fun getProvinces(id: CountryId): List<Province>

    // * * * STATS * * * //

    suspend fun getWorldStat(): WorldStat
    suspend fun getWorldFullStat(): WorldFullStat
    suspend fun getCountrySmallStat(id: CountryId): CountrySmallStat
    suspend fun getCountryStat(id: CountryId): CountryStat
    suspend fun getCountryFullStat(id: CountryId): CountryFullStat
    suspend fun getProvinceStat(countryId: CountryId, id: ProvinceId): ProvinceStat
    suspend fun getProvinceFullStat(countryId: CountryId, id: ProvinceId): ProvinceFullStat
}
