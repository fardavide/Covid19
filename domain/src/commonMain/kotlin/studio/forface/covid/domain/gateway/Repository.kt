package studio.forface.covid.domain.gateway

import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import studio.forface.covid.domain.DEFAULT_ERROR_INTERVAL
import studio.forface.covid.domain.DEFAULT_REFRESH_INTERVAL
import studio.forface.covid.domain.entity.*
import studio.forface.covid.domain.unsupported
import studio.forface.covid.domain.util.delay
import kotlin.jvm.JvmName
import kotlin.time.Duration

/**
 * Gateway to the locally cached data source
 * This will be our Single Source of Truth, asserting the fact that it will always be synced with remote data
 *
 * @author Davide Farella
 */
interface Repository {

    /** @return List of all the available [Country]s */
    fun getCountries(): Flow<List<Country>>

    /** @return List of all the [Province]s for the given [CountryId] */
    fun getProvinces(id: CountryId): Flow<List<Province>>

    /** Save [Country]s to local cache */
    suspend fun storeCountries(countries: List<Country>)

    /** Save [Province]s to local cache */
    suspend fun storeProvinces(provinces: List<Province>)

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

/**
 * A Repository with no cache, it will retrieve data from [Api] every declared interval.
 * This is meant to be a placeholder until the local cache will be implemented
 *
 * @author Davide Farella
 */
@Suppress("MaxLineLength", "MaximumLineLength")
internal class NoCacheRepository(
    private val api: Api,
    private val refreshInterval: Duration = DEFAULT_REFRESH_INTERVAL,
    private val errorInterval: Duration = DEFAULT_ERROR_INTERVAL
) : Repository {

    override fun getCountries(): Flow<List<Country>> = repeatFlow { api.getCountries() }
    override fun getProvinces(id: CountryId): Flow<List<Province>> = repeatFlow { api.getProvinces(id) }

    override suspend fun storeCountries(countries: List<Country>) = unsupported
    override suspend fun storeProvinces(provinces: List<Province>) = unsupported
    override suspend fun store(stat: WorldStat) = unsupported
    override suspend fun store(stat: WorldFullStat) = unsupported
    override suspend fun store(stat: CountrySmallStat) = unsupported
    override suspend fun store(stat: CountryStat) = unsupported
    override suspend fun store(stat: CountryFullStat) = unsupported
    override suspend fun store(stat: ProvinceStat) = unsupported
    override suspend fun store(stat: ProvinceFullStat) = unsupported

    override fun getWorldStat(): Flow<WorldStat> = repeatFlow { api.getWorldStat() }
    override fun getWorldFullStat(): Flow<WorldFullStat> = repeatFlow { api.getWorldFullStat() }
    override fun getCountrySmallStat(id: CountryId): Flow<CountrySmallStat> = repeatFlow { api.getCountrySmallStat(id) }
    override fun getCountryStat(id: CountryId): Flow<CountryStat> = repeatFlow { api.getCountryStat(id) }
    override fun getCountryFullStat(id: CountryId): Flow<CountryFullStat> = repeatFlow { api.getCountryFullStat(id) }
    override fun getProvinceStat(id: ProvinceId): Flow<ProvinceStat> = repeatFlow { api.getProvinceStat(id) }
    override fun getProvinceFullStat(id: ProvinceId): Flow<ProvinceFullStat> = repeatFlow { api.getProvinceFullStat(id) }

    private fun <T> repeatFlow(block: suspend ProducerScope<T>.() -> T) = channelFlow<T> {
        while (isActive) {
            try {
                send(block())
                delay(refreshInterval)
            } catch (t: Throwable) {
                delay(errorInterval)
            }
        }
    }
}
