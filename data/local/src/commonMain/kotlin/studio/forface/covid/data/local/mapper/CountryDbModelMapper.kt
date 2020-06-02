@file:Suppress("ClassNaming", "ClassName")

package studio.forface.covid.data.local.mapper

import studio.forface.covid.data.local.CountryWithProvinceStat
import studio.forface.covid.data.local.model.CountryStatPlainDbModel
import studio.forface.covid.data.local.model.CountryStatPlainDbModelImpl
import studio.forface.covid.data.local.model.CountryWithProvinceDbModel
import studio.forface.covid.data.local.model.CountryWithProvincesStatPlainDbModel
import studio.forface.covid.data.local.model.ProvinceStatPlainDbModel
import studio.forface.covid.data.local.model.ProvinceStatPlainDbModelImpl
import studio.forface.covid.data.local.model.wrap
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryFullStat
import studio.forface.covid.domain.entity.CountrySmallStat
import studio.forface.covid.domain.entity.CountryStat
import studio.forface.covid.domain.entity.ProvinceFullStat
import studio.forface.covid.domain.entity.ProvinceId
import studio.forface.covid.domain.entity.ProvinceStat
import studio.forface.covid.domain.entity.Stat
import studio.forface.covid.domain.invoke
import studio.forface.covid.domain.mapper.map

// region Country mappers
/**
 * Map a [List] of [CountryWithProvinceDbModel] to a [Country]
 * NOTE: [CountryWithProvinceDbModel]s are supposed to refer all to the same County!!
 *
 * @author Davide Farella
 */
internal class CountryWithProvinceList_Country(
    private val provinceMapper: CountryWithProvinceDbModelMapper
) : DatabaseModelMapper<List<CountryWithProvinceDbModel>, Country> {

    override fun List<CountryWithProvinceDbModel>.toEntity() = Country(
        id = first().id,
        name = first().name,
        favorite = first().favorite,
        provinces = map { provinceMapper { it.toEntity() } }
    )
}

/**
 * Map a [List] of [CountryWithProvinceDbModel] to a [List] of [Country]
 *
 * @author Davide Farella
 */
internal class CountryWithProvinceList_MultiCountry(
    private val singleCountyMapper: CountryWithProvinceList_Country,
    private val provinceMapper: ProvinceWrapperMapper
) : DatabaseModelMapper<List<CountryWithProvinceDbModel>, List<Country>> {

    override fun List<CountryWithProvinceDbModel>.toEntity() = groupBy { it.id }.map { (_, list) ->
        val country = singleCountyMapper { list.toEntity() }
        val provinces = list.wrap.map(provinceMapper) { it.toEntity() }
        country.copy(provinces = provinces)
    }
}
// endregion

// region Single CountryStat mappers
/**
 * Map a [List] of [CountryStatPlainDbModel] to a [CountrySmallStat]
 * NOTE: [CountryStatPlainDbModel]s are supposed to refer all to the same County!!
 *
 * @author Davide Farella
 */
internal class CountryStatPlainList_CountrySmallStat(
    private val countryPlainMapper: CountryStatPlainList_Country,
    private val countyStatPlainMapper: CountryStatPlainDbModelMapper
) : DatabaseModelMapper<List<CountryStatPlainDbModel>, CountrySmallStat> {

    override fun List<CountryStatPlainDbModel>.toEntity() = CountrySmallStat(
        country = countryPlainMapper { toEntity() },
        stat = countyStatPlainMapper { first().toEntity() }
    )
}

internal abstract class Abs_CountryWithProvinceStatPlainList_CountryStat<DbModel, CountryStatType, ProvinceStatType>(
    private val countryPlainMapper: CountryStatPlainList_Country,
    private val countyStatPlainMapper: CountryStatFromCountryWithProvinceStatPlainDbModelMapper,
    protected val provinceStatPlainMapper: DatabaseModelMapper<DbModel, ProvinceStatType>,
    private val buildProvinceStats:
    Abs_CountryWithProvinceStatPlainList_CountryStat<DbModel, CountryStatType, ProvinceStatType>.(
        List<CountryWithProvincesStatPlainDbModel>
    ) -> Map<ProvinceId, ProvinceStatType>,
    private val toCountryStatType: Params<ProvinceStatType>.() -> CountryStatType
) : DatabaseModelMapper<List<CountryWithProvincesStatPlainDbModel>, CountryStatType> {

    override fun List<CountryWithProvincesStatPlainDbModel>.toEntity(): CountryStatType {
        // Every group is relative to a single Province
        val groupByProvinces = groupBy { it.provinceId }

        // Take a value for each Province, ignoring different Stats with same Province
        // Each Stat will have different Province but they could have same Timestamp
        val provinceStats = groupByProvinces.map { it.value.first() }

        // Take a value for each Country Timestamp, ignoring different Stats with same Timestamp
        // Each Stat will have different Timestamp
        val countryTimestampStats = groupBy { it.countryTimestamp }.map { it.value.first() }

        return Params(
            country = countryPlainMapper { provinceStats.toCountryStatsPlain().toEntity() },
            countryStat = countryTimestampStats.map(countyStatPlainMapper) { it.toEntity() },
            provinceStats = buildProvinceStats(this)
        ).toCountryStatType()
    }

    private fun List<CountryWithProvincesStatPlainDbModel>.toCountryStatsPlain() = map {
        CountryStatPlainDbModelImpl(
            countryId = it.countryId,
            countryName = it.countryName,
            countryFavorite = it.countryFavorite,
            provinceId = it.provinceId,
            provinceName = it.provinceName,
            provinceLat = it.provinceLat,
            provinceLng = it.provinceLng,
            confirmed = it.countryConfirmed,
            deaths = it.countryDeaths,
            recovered = it.countryRecovered,
            timestamp = it.countryTimestamp
        )
    }

    fun CountryWithProvincesStatPlainDbModel.toProvinceStatsPlain(): ProvinceStatPlainDbModelImpl {
        return ProvinceStatPlainDbModelImpl(
            id = provinceId,
            name = provinceName,
            lat = provinceLat,
            lng = provinceLng,
            confirmed = provinceConfirmed,
            deaths = provinceDeaths,
            recovered = provinceRecovered,
            timestamp = provinceTimestamp
        )
    }

    data class Params<ProvinceStatType>(
        val country: Country,
        val countryStat: List<Stat>,
        val provinceStats: Map<ProvinceId, ProvinceStatType>
    )
}

/**
 * Map a [List] of [CountryWithProvincesStatPlainDbModel] to a [CountryStat]
 * NOTE: [CountryWithProvincesStatPlainDbModel]s are supposed to refer all to the same County!!
 *
 * @author Davide Farella
 */
internal class CountryWithProvinceStatPlainList_CountryStat(
    countryPlainMapper: CountryStatPlainList_Country,
    countyStatPlainMapper: CountryStatFromCountryWithProvinceStatPlainDbModelMapper,
    provinceStatMapper: ProvinceStatDbModelMapper
) : Abs_CountryWithProvinceStatPlainList_CountryStat<ProvinceStatPlainDbModel, CountryStat, ProvinceStat>(
    countryPlainMapper,
    countyStatPlainMapper,
    provinceStatMapper,
    buildProvinceStats = { list ->
        list.groupBy { it.provinceId }.mapValues { e ->
            provinceStatMapper {
                e.value.first().toProvinceStatsPlain().toEntity()
            }
        }
    },
    toCountryStatType = { CountryStat(country, countryStat, provinceStats) }
)


/**
 * Map a [List] of [CountryWithProvincesStatPlainDbModel] to a [CountryFullStat]
 * NOTE: [CountryWithProvincesStatPlainDbModel]s are supposed to refer all to the same County!!
 *
 * @author Davide Farella
 */
internal class CountryWithProvinceStatPlainList_CountryFullStat(
    countryPlainMapper: CountryStatPlainList_Country,
    countyStatPlainMapper: CountryStatFromCountryWithProvinceStatPlainDbModelMapper,
    provinceStatMapper: ProvinceFullStatDbModelMapper
) : Abs_CountryWithProvinceStatPlainList_CountryStat<List<ProvinceStatPlainDbModel>, CountryFullStat, ProvinceFullStat>(
    countryPlainMapper,
    countyStatPlainMapper,
    provinceStatMapper,
    buildProvinceStats = { list ->
        list.groupBy { it.provinceTimestamp }.map { (_, list) ->
            list.first().provinceId to provinceStatMapper { list.map { it.toProvinceStatsPlain() }.toEntity() }
        }.toMap()
    },
    toCountryStatType = { CountryFullStat(country, countryStat, provinceStats) }
)
// endregion

// region Multi CountryStat mappers
internal class CountryWithProvinceStatPlainList_MultiCountryStat(
    private val singleCountryStatMapper: CountryWithProvinceStatPlainList_CountryStat
) : DatabaseModelMapper<List<CountryWithProvinceStat>, List<CountryStat>> {
    override fun List<CountryWithProvinceStat>.toEntity() = groupBy { it.countryId }
        .mapValues { (_, statsPlainList) -> singleCountryStatMapper { statsPlainList.toEntity() } }
        .values.toList()
}
// endregion

// region Plain mappers
/**
 * Map a [List] of [CountryStatPlainDbModel] to a [Country]
 * NOTE: [CountryStatPlainDbModel]s are supposed to refer all to the same County!!
 *
 * @author Davide Farella
 */
internal class CountryStatPlainList_Country(
    private val provincePlainMapper: ProvincePlainDbModelMapper
) : DatabaseModelMapper<List<CountryStatPlainDbModel>, Country> {

    override fun List<CountryStatPlainDbModel>.toEntity(): Country {
        val first = first()
        return Country(
            first.countryId,
            first.countryName,
            first.countryFavorite,
            toProvinces().map(provincePlainMapper) { it.toEntity() })
    }

    private fun List<CountryStatPlainDbModel>.toProvinces() = map {
        ProvinceStatPlainDbModelImpl(
            id = it.provinceId,
            name = it.provinceName,
            lat = it.provinceLat,
            lng = it.provinceLng,
            confirmed = it.confirmed,
            deaths = it.deaths,
            recovered = it.recovered,
            timestamp = it.timestamp
        )
    }
}
// endregion
