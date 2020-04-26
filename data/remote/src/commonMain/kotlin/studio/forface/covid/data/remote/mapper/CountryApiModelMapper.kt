package studio.forface.covid.data.remote.mapper

import studio.forface.covid.data.remote.model.CountryFullStatApiModel
import studio.forface.covid.data.remote.model.CountrySmallStatApiModel
import studio.forface.covid.data.remote.model.CountryStatApiModel
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryFullStat
import studio.forface.covid.domain.entity.CountrySmallStat
import studio.forface.covid.domain.entity.CountryStat
import studio.forface.covid.domain.entity.plus
import studio.forface.covid.domain.invoke
import studio.forface.covid.domain.mapper.associateMap
import studio.forface.covid.domain.mapper.map

internal class CountrySmallStatApiModelMapper(
    private val countryMapper: CountryFromSmallStatApiModelMapper,
    private val statParamsMapper: StatParamsMapper
) : ApiModelMapper<CountrySmallStatApiModel, CountrySmallStat> {

    override fun CountrySmallStatApiModel.toEntity() = CountrySmallStat(
        country = countryMapper { this@toEntity.toEntity() },
        stat = statParamsMapper { StatParams(confirmed, deaths, recovered, lastUpdate).toEntity() }
    )
}

internal class CountryStatApiModelMapper(
    private val countryMapper: CountryFromStatApiModelMapper,
    private val provinceMapper: ProvinceStatApiModelMapper,
    private val statMapper: StatApiModelMapper,
    private val statParamsMapper: StatParamsMapper,
    private val idMapper: ProvinceIdApiModelMapper
) : ApiModelMapper<CountryStatApiModel, CountryStat> {

    override fun CountryStatApiModel.toEntity(): CountryStat {
        val lastStat = statParamsMapper { StatParams(confirmed, deaths, recovered, lastUpdate).toEntity() }
        val otherStats = stats.map(statMapper) { it.toEntity() }
        return CountryStat(
            country = countryMapper { this@toEntity.toEntity() },
            stats = lastStat + otherStats,
            provinceStats = provinceStats.associateMap(provinceMapper) {
                idMapper.invoke { it.id.toEntity() } to it.toEntity()
            }
        )
    }
}

internal class CountryFullStatApiModelMapper(
    private val countryMapper: CountryFromFullStatApiModelMapper,
    private val provinceMapper: ProvinceFullStatApiModelMapper,
    private val statMapper: StatApiModelMapper,
    private val statParamsMapper: StatParamsMapper,
    private val idMapper: ProvinceIdApiModelMapper
) : ApiModelMapper<CountryFullStatApiModel, CountryFullStat> {

    override fun CountryFullStatApiModel.toEntity(): CountryFullStat {
        val lastStat = statParamsMapper { StatParams(confirmed, deaths, recovered, lastUpdate).toEntity() }
        val otherStats = stats.map(statMapper) { it.toEntity() }
        return CountryFullStat(
            country = countryMapper { this@toEntity.toEntity() },
            stats = lastStat + otherStats,
            provinceStats = provinceStats.associateMap(provinceMapper) {
                idMapper.invoke { it.id.toEntity() } to it.toEntity()
            }
        )
    }
}

internal class CountryFromSmallStatApiModelMapper(
    private val idMapper: CountryIdApiModelMapper,
    private val nameMapper: NameApiModelMapper
) : ApiModelMapper<CountrySmallStatApiModel, Country> {

    override fun CountrySmallStatApiModel.toEntity() = Country(
        id = idMapper { id.toEntity() },
        name = nameMapper { name.toEntity() },
        provinces = emptyList() // TODO not supported
    )
}

internal class CountryFromStatApiModelMapper(
    private val provinceMapper: ProvinceFromStatApiModelMapper,
    private val idMapper: CountryIdApiModelMapper,
    private val nameMapper: NameApiModelMapper
) : ApiModelMapper<CountryStatApiModel, Country> {

    override fun CountryStatApiModel.toEntity() = Country(
        id = idMapper { id.toEntity() },
        name = nameMapper { name.toEntity() },
        provinces = provinceStats.map(provinceMapper) { it.toEntity() }
    )
}

internal class CountryFromFullStatApiModelMapper(
    private val provinceMapper: ProvinceFromFullStatApiModelMapper,
    private val idMapper: CountryIdApiModelMapper,
    private val nameMapper: NameApiModelMapper
) : ApiModelMapper<CountryFullStatApiModel, Country> {

    override fun CountryFullStatApiModel.toEntity() = Country(
        id = idMapper { id.toEntity() },
        name = nameMapper { name.toEntity() },
        provinces = provinceStats.map(provinceMapper) { it.toEntity() }
    )
}
