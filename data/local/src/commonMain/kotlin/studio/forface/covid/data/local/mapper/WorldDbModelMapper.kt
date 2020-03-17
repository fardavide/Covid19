package studio.forface.covid.data.local.mapper

import studio.forface.covid.data.local.model.CountryStatPlainDbModelImpl
import studio.forface.covid.data.local.model.CountryWithProvincesStatPlainDbModelImpl
import studio.forface.covid.data.local.model.WorldStatPlainDbModel
import studio.forface.covid.data.local.model.WorldStatPlainDbModelImpl
import studio.forface.covid.data.local.model.WorldWithProvinceStatPlainDbModel
import studio.forface.covid.domain.entity.World
import studio.forface.covid.domain.entity.WorldFullStat
import studio.forface.covid.domain.entity.WorldStat
import studio.forface.covid.domain.invoke
import studio.forface.covid.domain.mapper.map

// region World mappers

// endregion

// region WorldStat Mappers
internal class WorldStatDbModelMapper(
    private val worldPlainMapper: WorldPlainDbModelMapper,
    private val worldStatPlainMapper: WorldStatPlainDModelMapper,
    private val countryStatMapper: CountrySmallStatDbModelMapper
) : DatabaseModelMapper<List<WorldStatPlainDbModel>, WorldStat> {

    override fun List<WorldStatPlainDbModel>.toEntity(): WorldStat {
        // Every group is relative to a single Country
        val groupByCountries = groupBy { it.countryId }

        // Take a value for each Country, ignoring different Stats with same Country and Province
        // Each Stat will have different Country but they could have same Timestamp
        val countryStats = groupByCountries.map { it.value.first() }

        // Take a value for each World Timestamp, ignoring different Stats with same Timestamp
        // Each Stat will have different Timestamp
        val worldTimestampStats = groupBy { it.worldTimestamp }.map { it.value.first() }

        return WorldStat(
            world = worldPlainMapper { countryStats.toWorldStatsPlain().toEntity() },
            stats = worldTimestampStats.map(worldStatPlainMapper) { it.toEntity() },
            countryStats = groupBy { it.countryId }.mapValues {
                countryStatMapper { it.value.toCountryStatsPlain().toEntity() }
            }
        )
    }

    @Suppress("DuplicatedCode")
    private fun List<WorldStatPlainDbModel>.toWorldStatsPlain() = map {
        WorldStatPlainDbModelImpl(
            worldId = it.worldId,
            worldName = it.worldName,
            worldConfirmed = it.worldConfirmed,
            worldDeaths = it.worldDeaths,
            worldRecovered = it.worldRecovered,
            worldTimestamp = it.worldTimestamp,
            countryId = it.countryId,
            countryName = it.countryName,
            countryConfirmed = it.countryConfirmed,
            countryDeaths = it.countryDeaths,
            countryRecovered = it.countryRecovered,
            countryTimestamp = it.countryTimestamp,
            provinceId = it.provinceId,
            provinceName = it.provinceName,
            provinceLat = it.provinceLat,
            provinceLng = it.provinceLng
        )
    }

    private fun List<WorldStatPlainDbModel>.toCountryStatsPlain() = map {
        CountryStatPlainDbModelImpl(
            countryId = it.countryId,
            countryName = it.countryName,
            confirmed = it.countryConfirmed,
            deaths = it.countryDeaths,
            recovered = it.countryRecovered,
            timestamp = it.countryTimestamp,
            provinceId = it.provinceId,
            provinceName = it.provinceName,
            provinceLat = it.provinceLat,
            provinceLng = it.provinceLng
        )
    }
}

internal class WorldFullStatDbModelMapper(
    private val worldPlainMapper: WorldPlainDbModelMapper,
    private val worldStatPlainMapper: WorldStatFromWorldWithProvincesStatPlainDModelMapper,
    private val countryStatMapper: CountryStatDbModelMapper
) : DatabaseModelMapper<List<WorldWithProvinceStatPlainDbModel>, WorldFullStat> {

    override fun List<WorldWithProvinceStatPlainDbModel>.toEntity(): WorldFullStat {
        // Every group is relative to a single Country
        val groupByCountries = groupBy { it.countryId }

        // Take a value for each Country, ignoring different Stats with same Country and Province
        // Each Stat will have different Country but they could have same Timestamp
        val countryStats = groupByCountries.map { it.value.first() }

        // Take a value for each World Timestamp, ignoring different Stats with same Timestamp
        // Each Stat will have different Timestamp
        val worldTimestampStats = groupBy { it.worldTimestamp }.map { it.value.first() }

        return WorldFullStat(
            world = worldPlainMapper { countryStats.toWorldStatsPlain().toEntity() },
            stats = worldTimestampStats.map(worldStatPlainMapper) { it.toEntity() },
            countryStats = groupBy { it.countryId }.mapValues {
                countryStatMapper { it.value.toCountryStatsPlain().toEntity() }
            }
        )
    }

    @Suppress("DuplicatedCode")
    private fun List<WorldWithProvinceStatPlainDbModel>.toWorldStatsPlain() = map {
        WorldStatPlainDbModelImpl(
            worldId = it.worldId,
            worldName = it.worldName,
            worldConfirmed = it.worldConfirmed,
            worldDeaths = it.worldDeaths,
            worldRecovered = it.worldRecovered,
            worldTimestamp = it.worldTimestamp,
            countryId = it.countryId,
            countryName = it.countryName,
            countryConfirmed = it.countryConfirmed,
            countryDeaths = it.countryDeaths,
            countryRecovered = it.countryRecovered,
            countryTimestamp = it.countryTimestamp,
            provinceId = it.provinceId,
            provinceName = it.provinceName,
            provinceLat = it.provinceLat,
            provinceLng = it.provinceLng
        )
    }

    private fun List<WorldWithProvinceStatPlainDbModel>.toCountryStatsPlain() = map {
        CountryWithProvincesStatPlainDbModelImpl(
            countryId = it.countryId,
            countryName = it.countryName,
            countryConfirmed = it.countryConfirmed,
            countryDeaths = it.countryDeaths,
            countryRecovered = it.countryRecovered,
            countryTimestamp = it.countryTimestamp,
            provinceId = it.provinceId,
            provinceName = it.provinceName,
            provinceLat = it.provinceLat,
            provinceLng = it.provinceLng,
            provinceConfirmed = it.provinceConfirmed,
            provinceDeaths = it.provinceDeaths,
            provinceRecovered = it.provinceRecovered,
            provinceTimestamp = it.provinceTimestamp
        )
    }
}
// endregion

// region Plain mappers
internal class WorldPlainDbModelMapper(
    private val singleCountryPlainMapper: SingleCountryPlainDbModelMapper
) : DatabaseModelMapper<List<WorldStatPlainDbModel>, World> {

    override fun List<WorldStatPlainDbModel>.toEntity(): World {
        val (id, name) = with(first()) { worldId to worldName }
        val countries = groupBy { it.countryId }
            .map { singleCountryPlainMapper.invoke { it.value.toCountries().toEntity() } }
        return World(id, name, countries)
    }

    private fun List<WorldStatPlainDbModel>.toCountries() = map {
        CountryStatPlainDbModelImpl(
            countryId = it.countryId,
            countryName = it.countryName,
            confirmed = it.countryConfirmed,
            deaths = it.countryDeaths,
            recovered = it.countryRecovered,
            timestamp = it.countryTimestamp,
            provinceId = it.provinceId,
            provinceName = it.provinceName,
            provinceLat = it.provinceLat,
            provinceLng = it.provinceLng
        )
    }
}
// endregion
