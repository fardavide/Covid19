package studio.forface.covid.data.local

import com.soywiz.klock.DateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.map
import studio.forface.covid.data.local.mapper.CountryFullStatDbModelMapper
import studio.forface.covid.data.local.mapper.CountrySmallStatDbModelMapper
import studio.forface.covid.data.local.mapper.CountryStatDbModelMapper
import studio.forface.covid.data.local.mapper.MultiCountryDbModelMapper
import studio.forface.covid.data.local.mapper.ProvinceFullStatDbModelMapper
import studio.forface.covid.data.local.mapper.ProvinceStatDbModelMapper
import studio.forface.covid.data.local.mapper.SingleCountryDbModelMapper
import studio.forface.covid.data.local.mapper.UnixTimeDbModelMapper
import studio.forface.covid.data.local.mapper.WorldFullStatDbModelMapper
import studio.forface.covid.data.local.mapper.WorldStatDbModelMapper
import studio.forface.covid.data.local.utils.TransactionProvider
import studio.forface.covid.data.local.utils.asListFlow
import studio.forface.covid.data.local.utils.asOneFlow
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryFullStat
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.CountrySmallStat
import studio.forface.covid.domain.entity.CountryStat
import studio.forface.covid.domain.entity.Id
import studio.forface.covid.domain.entity.Name
import studio.forface.covid.domain.entity.Province
import studio.forface.covid.domain.entity.ProvinceFullStat
import studio.forface.covid.domain.entity.ProvinceId
import studio.forface.covid.domain.entity.ProvinceStat
import studio.forface.covid.domain.entity.Stat
import studio.forface.covid.domain.entity.World
import studio.forface.covid.domain.entity.WorldFullStat
import studio.forface.covid.domain.entity.WorldId
import studio.forface.covid.domain.entity.WorldStat
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.invoke
import studio.forface.covid.domain.mapper.map

/**
 * Default Implementation of [Repository]
 * @author Davide Farella
 */
internal class RepositoryImpl(
    private val transaction: TransactionProvider,
    private val worldQueries: WorldQueries,
    private val countryQueries: CountryQueries,
    private val provinceQueries: ProvinceQueries,
    private val statQueries: StatQueries,
    private val worldStatMapper: WorldStatDbModelMapper,
    private val worldFullStatMapper: WorldFullStatDbModelMapper,
    private val singleCountryMapper: SingleCountryDbModelMapper,
    private val multiCountryMapper: MultiCountryDbModelMapper,
    private val countrySmallStatMapper: CountrySmallStatDbModelMapper,
    private val countryStatMapper: CountryStatDbModelMapper,
    private val countryFullStatMapper: CountryFullStatDbModelMapper,
    private val provinceStatMapper: ProvinceStatDbModelMapper,
    private val provinceFullStatMapper: ProvinceFullStatDbModelMapper,
    private val timeMapper: UnixTimeDbModelMapper
) : Repository {

    override fun getCountries(): Flow<List<Country>> =
        countryQueries.selectAllCountriesWithProvinces().asListFlow()
            .map(multiCountryMapper) { it.toEntity() }

    override fun getCountries(query: Name): Flow<List<Country>> {
        return countryQueries.selectAllCountryWithProvincesByName(query.s).asListFlow()
            .map(multiCountryMapper) { it.toEntity() }
    }

    override fun getProvinces(id: CountryId): Flow<List<Province>> =
        countryQueries.selectAllCountryWithProvincesById(id).asListFlow()
            .map(singleCountryMapper) { it.toEntity() }.map { it.provinces }

    override suspend fun storeCountries(countries: List<Country>) {
        transaction {
            for (country in countries) blockingStore(country)
        }
    }

    override fun getWorldStat(): Flow<WorldStat> = worldQueries.selectAllWorldStat().asListFlow()
        .map(worldStatMapper) { it.toEntity() }

    override fun getWorldFullStat(): Flow<WorldFullStat> = worldQueries.selectAllWorldWithProvinceStat().asListFlow()
        .map(worldFullStatMapper) { it.toEntity() }

    override fun getCountrySmallStat(id: CountryId): Flow<CountrySmallStat> =
        countryQueries.selectAllCountryStatsById(id).asListFlow()
            .map(countrySmallStatMapper) { it.toEntity() }

    override fun getCountryStat(id: CountryId): Flow<CountryStat> =
        countryQueries.selectAllCountryWithProvinceStatsById(id).asListFlow()
            .map(countryStatMapper) { it.toEntity() }

    override fun getCountryFullStat(id: CountryId): Flow<CountryFullStat> =
        countryQueries.selectAllCountryWithProvinceStatsById(id).asListFlow().dropWhile { it.isEmpty() }
            .map(countryFullStatMapper) { it.toEntity() }

    override fun getProvinceStat(id: ProvinceId): Flow<ProvinceStat> =
        provinceQueries.selectLastProvinceStatById(id).asOneFlow()
            .map(provinceStatMapper) { it.toEntity() }

    override fun getProvinceFullStat(id: ProvinceId): Flow<ProvinceFullStat> =
        provinceQueries.selectAllProvinceStatsById(id).asListFlow()
            .map(provinceFullStatMapper) { it.toEntity() }

    override suspend fun store(stat: WorldStat) {
        transaction {
            val (world, worldStats, countryStats) = stat
            blockingStore(world)
            for (worldStat in worldStats) blockingStore(world.id, worldStat)
            for (countryStat in countryStats) blockingStore(countryStat.value)
        }
    }

    override suspend fun store(stat: WorldFullStat) {
        transaction {
            val (world, worldStats, countryStats) = stat
            blockingStore(world)
            for (worldStat in worldStats) blockingStore(world.id, worldStat)
            for (countryStat in countryStats) blockingStore(countryStat.value)
        }
    }

    override suspend fun store(stat: CountrySmallStat) {
        transaction {
            val (country, s) = stat
            blockingStore(country)
            blockingStore(country.id, s)
        }
    }

    override suspend fun store(stat: CountryStat) {
        transaction {
            val (country, countryStats, provinceStats) = stat
            blockingStore(country)
            for (countryStat in countryStats) blockingStore(country.id, countryStat)
            for ((_, provinceStat) in provinceStats) blockingStore(provinceStat)
        }
    }

    override suspend fun store(stat: CountryFullStat) {
        transaction {
            val (country, countryStats, provinceStats) = stat
            blockingStore(country)
            for (countyStat in countryStats) blockingStore(country.id, countyStat)
            for ((_, provinceStat) in provinceStats) blockingStore(provinceStat)
        }
    }

    override suspend fun store(stat: ProvinceStat) {
        transaction {
            val (province, provinceStat) = stat
            blockingStore(province.id, provinceStat)
        }
    }

    override suspend fun store(stat: ProvinceFullStat) {
        transaction {
            blockingStore(stat)
        }
    }

    // region Blocking
    private fun blockingStore(world: World) {
        worldQueries.insert(world.id, world.name)
        for (country in world.countries) blockingStore(country)
    }

    private fun blockingStore(country: Country) {
        countryQueries.insert(country.id, country.name)
        for (province in country.provinces) blockingStore(country.id, province)
    }

    private fun blockingStore(countryId: CountryId, province: Province) {
        provinceQueries.insert(province.id, countryId, province.name, province.location.lat, province.location.lng)
    }

    private fun blockingStore(stat: CountrySmallStat) {
        val (country, countyStat) = stat
        blockingStore(country.id, countyStat)
    }

    private fun blockingStore(stat: CountryStat) {
        val (country, countyStats) = stat
        for (countryStat in countyStats) blockingStore(country.id, countryStat)
    }

    private fun blockingStore(stat: ProvinceStat) {
        val (province, provinceStat) = stat
        blockingStore(province.id, provinceStat)
    }

    private fun blockingStore(stat: ProvinceFullStat) {
        val (province, provinceStats) = stat
        for (provinceStat in provinceStats) blockingStore(province.id, provinceStat)
    }

    private fun blockingStore(parentId: Id, stat: Stat) {
        statQueries.insert(
            parentId,
            confirmed = stat.confirmed,
            deaths = stat.deaths,
            recovered = stat.recovered,
            timestamp = stat.timestamp.toModel()
        )
    }
    // endregion

    private fun DateTime.toModel() = timeMapper { toModel() }
    private fun Double.toEntity() = timeMapper { toEntity() }
}

// TODO: find better place
internal val WorldId = WorldId("world")
