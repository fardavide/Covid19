package studio.forface.covid.domain.gateway

import kotlinx.coroutines.flow.Flow
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryFullStat
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.CountrySmallStat
import studio.forface.covid.domain.entity.CountryStat
import studio.forface.covid.domain.entity.Name
import studio.forface.covid.domain.entity.Province
import studio.forface.covid.domain.entity.ProvinceFullStat
import studio.forface.covid.domain.entity.ProvinceId
import studio.forface.covid.domain.entity.ProvinceStat
import studio.forface.covid.domain.entity.WorldFullStat
import studio.forface.covid.domain.entity.WorldStat

/**
 * Gateway to the locally cached data source
 * This will be our Single Source of Truth, asserting the fact that it will always be synced with remote data
 *
 * @author Davide Farella
 */
interface Repository {

    /** @return List of all the available [Country]s */
    fun getCountries(): Flow<List<Country>>

    /** @return List of all the available [Country]s for given [uery] */
    fun getCountries(query: Name): Flow<List<Country>>

    /** @return List of all the [Province]s for the given [CountryId] */
    fun getProvinces(id: CountryId): Flow<List<Province>>

    /** Save [Country]s and relative [Province]s to local cache */
    suspend fun storeCountries(countries: List<Country>)

    /** Update Favorite state for Contry with given [id] */
    suspend fun updateFavorite(id: CountryId, favorite: Boolean)

    // * * * STATS * * * //

    fun getWorldStat(): Flow<WorldStat>
    fun getWorldFullStat(): Flow<WorldFullStat>
    fun getCountrySmallStat(id: CountryId): Flow<CountrySmallStat>
    fun getCountryStat(id: CountryId): Flow<CountryStat>
    fun getCountryFullStat(id: CountryId): Flow<CountryFullStat>
    fun getProvinceStat(id: ProvinceId): Flow<ProvinceStat>
    fun getProvinceFullStat(id: ProvinceId): Flow<ProvinceFullStat>


    /** Save [WorldStat] to local cache */
    suspend fun store(stat: WorldStat)

    /** Save [WorldFullStat] to local cache */
    suspend fun store(stat: WorldFullStat)

    /** Save [CountrySmallStat] to local cache */
    suspend fun store(stat: CountrySmallStat)

    /** Save [CountryStat] to local cache */
    suspend fun store(stat: CountryStat)

    /** Save [CountryFullStat] to local cache */
    suspend fun store(stat: CountryFullStat)

    /** Save [ProvinceStat] to local cache */
    suspend fun store(stat: ProvinceStat)

    /** Save [ProvinceFullStat] to local cache */
    suspend fun store(stat: ProvinceFullStat)
}
