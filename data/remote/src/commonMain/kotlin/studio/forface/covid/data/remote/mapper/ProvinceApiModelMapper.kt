package studio.forface.covid.data.remote.mapper

import studio.forface.covid.data.remote.model.ProvinceFullStatApiModel
import studio.forface.covid.data.remote.model.ProvinceStatApiModel
import studio.forface.covid.domain.entity.Province
import studio.forface.covid.domain.entity.ProvinceFullStat
import studio.forface.covid.domain.entity.ProvinceStat
import studio.forface.covid.domain.entity.plus
import studio.forface.covid.domain.invoke
import studio.forface.covid.domain.mapper.map

internal class ProvinceStatApiModelMapper(
    private val provinceMapper: ProvinceFromStatApiModelMapper,
    private val statParamsMapper: StatParamsMapper
) : ApiModelMapper<ProvinceStatApiModel, ProvinceStat> {

    override fun ProvinceStatApiModel.toEntity() = ProvinceStat(
        province = provinceMapper { this@toEntity.toEntity() },
        stat = statParamsMapper { StatParams(confirmed, deaths, recovered, lastUpdate).toEntity() }
    )
}

internal class ProvinceFullStatApiModelMapper(
    private val provinceMapper: ProvinceFromFullStatApiModelMapper,
    private val statMapper: StatApiModelMapper,
    private val statParamsMapper: StatParamsMapper
) : ApiModelMapper<ProvinceFullStatApiModel, ProvinceFullStat> {

    override fun ProvinceFullStatApiModel.toEntity(): ProvinceFullStat {
        val lastStat = statParamsMapper { StatParams(confirmed, deaths, recovered, lastUpdate).toEntity() }
        val otherStats = stats.map(statMapper) { it.toEntity() }
        return ProvinceFullStat(
            province = provinceMapper { this@toEntity.toEntity() },
            stats = lastStat + otherStats
        )
    }
}

internal class ProvinceFromStatApiModelMapper(
    private val idMapper: ProvinceIdApiModelMapper,
    private val nameMapper: NameApiModelMapper,
    private val locationMapper: LocationApiModelMapper
) : ApiModelMapper<ProvinceStatApiModel, Province> {

    override fun ProvinceStatApiModel.toEntity() = Province(
        id = idMapper { id.toEntity() },
        name = nameMapper { name.toEntity() },
        location = locationMapper { LocationParams(lat, lng).toEntity() }
    )
}

internal class ProvinceFromFullStatApiModelMapper(
    private val idMapper: ProvinceIdApiModelMapper,
    private val nameMapper: NameApiModelMapper,
    private val locationMapper: LocationApiModelMapper
) : ApiModelMapper<ProvinceFullStatApiModel, Province> {

    override fun ProvinceFullStatApiModel.toEntity() = Province(
        id = idMapper { id.toEntity() },
        name = nameMapper { name.toEntity() },
        location = locationMapper { LocationParams(lat, lng).toEntity() }
    )
}
