package studio.forface.covid.data.remote.mapper

import studio.forface.covid.data.remote.model.CountryFullStatApiModel
import studio.forface.covid.data.remote.model.CountrySmallStatApiModel
import studio.forface.covid.data.remote.model.CountryStatApiModel
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryFullStat
import studio.forface.covid.domain.entity.CountrySmallStat
import studio.forface.covid.domain.entity.CountryStat
import studio.forface.covid.domain.invoke
import studio.forface.covid.domain.mapper.associateMap
import studio.forface.covid.domain.mapper.map

internal class CountrySmallStatApiModelMapper(
    private val countryMapper: CountryApiModelMapper,
    private val statParamsMapper: StatParamsMapper
) : ApiModelMapper<CountrySmallStatApiModel, CountrySmallStat> {

    override fun CountrySmallStatApiModel.toEntity() = CountrySmallStat(
        country = countryMapper { this@toEntity.toEntity() },
        stat = statParamsMapper { StatParams(confirmed, deaths, recovered, lastUpdate).toEntity() }
    )
}

internal class CountryStatApiModelMapper(
    private val countryMapper: CountryApiModelMapper,
    private val provinceMapper: ProvinceStatApiModelMapper,
    private val statMapper: StatApiModelMapper,
    private val statParamsMapper: StatParamsMapper,
    private val idMapper: IdApiModelMapper
) : ApiModelMapper<CountryStatApiModel, CountryStat> {

    override fun CountryStatApiModel.toEntity() = CountryStat(
        country = countryMapper { this@toEntity.toEntity() },
        stat = statParamsMapper { StatParams(confirmed, deaths, recovered, lastUpdate).toEntity() },
        otherStats = stats.map(statMapper) { it.toEntity() },
        provinceStats = provinceStats.associateMap(provinceMapper) {
            idMapper.invoke { it.id.toEntity() } to it.toEntity()
        }
    )
}

internal class CountryFullStatApiModelMapper(
    private val countryMapper: CountryApiModelMapper,
    private val provinceMapper: ProvinceFullStatApiModelMapper,
    private val statMapper: StatApiModelMapper,
    private val statParamsMapper: StatParamsMapper,
    private val idMapper: IdApiModelMapper
) : ApiModelMapper<CountryFullStatApiModel, CountryFullStat> {

    override fun CountryFullStatApiModel.toEntity() = CountryFullStat(
        country = countryMapper { this@toEntity.toEntity() },
        stat = statParamsMapper { StatParams(confirmed, deaths, recovered, lastUpdate).toEntity() },
        otherStats = stats.map(statMapper) { it.toEntity() },
        provinceStats = provinceStats.associateMap(provinceMapper) {
            idMapper.invoke { it.id.toEntity() } to it.toEntity()
        }
    )
}

// TODO replace CountryStatApiModel with CountryApiModel, which does not exist ATM
internal class CountryApiModelMapper(
    private val idMapper: IdApiModelMapper,
    private val nameMapper: NameApiModelMapper
) : ApiModelMapper<CountryStatApiModel, Country> {

    fun CountrySmallStatApiModel.toEntity() = Country(
        id = idMapper { id.toEntity() },
        name = nameMapper { name.toEntity() },
        provinces = emptyList() // TODO not supported
    )

    override fun CountryStatApiModel.toEntity() = Country(
        id = idMapper { id.toEntity() },
        name = nameMapper { name.toEntity() },
        provinces = emptyList() // TODO not supported
    )

    fun CountryFullStatApiModel.toEntity() = Country(
        id = idMapper { id.toEntity() },
        name = nameMapper { name.toEntity() },
        provinces = emptyList() // TODO not supported
    )
}
