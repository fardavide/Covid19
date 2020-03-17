package studio.forface.covid.data.local.mapper

import studio.forface.covid.data.local.model.CountryWithProvinceDbModel
import studio.forface.covid.data.local.model.ProvinceDbModel
import studio.forface.covid.data.local.model.ProvinceStatPlainDbModel
import studio.forface.covid.data.local.model.ProvinceWrapper
import studio.forface.covid.domain.entity.Province
import studio.forface.covid.domain.entity.ProvinceFullStat
import studio.forface.covid.domain.entity.ProvinceStat
import studio.forface.covid.domain.invoke
import studio.forface.covid.domain.mapper.map

// region Province mappers
internal class ProvinceWrapperMapper(
    private val provinceMapper: ProvinceDbModelMapper,
    private val countryWithProvinceMapper: CountryWithProvinceDbModelMapper
) : DatabaseModelMapper<ProvinceWrapper, Province> {

    override fun ProvinceWrapper.toEntity() = when (this) {
        is ProvinceWrapper.Standalone -> provinceMapper { province.toEntity() }
        is ProvinceWrapper.FromCounty -> countryWithProvinceMapper { countryWithProvince.toEntity() }
    }
}

internal class ProvinceDbModelMapper(
    private val locationMapper: LocationDbModelMapper
) : DatabaseModelMapper<ProvinceDbModel, Province> {

    override fun ProvinceDbModel.toEntity() = Province(
        id = id,
        name = name,
        location = locationMapper { (lat to lng).toEntity() }
    )
}

internal class CountryWithProvinceDbModelMapper(
    private val locationMapper: LocationDbModelMapper
) : DatabaseModelMapper<CountryWithProvinceDbModel, Province> {

    override fun CountryWithProvinceDbModel.toEntity() = Province(
        id = provinceId,
        name = provinceName,
        location = locationMapper { (provinceLat to provinceLng).toEntity() }
    )
}
// endregion

// region ProvinceStat mappers
internal class ProvinceStatDbModelMapper(
    private val provincePlainMapper: ProvincePlainDbModelMapper,
    private val provincePlainStatMapper: ProvinceStatPlainDbModelMapper
) : DatabaseModelMapper<ProvinceStatPlainDbModel, ProvinceStat> {

    override fun ProvinceStatPlainDbModel.toEntity() = ProvinceStat(
        province = provincePlainMapper { toEntity() },
        stat = provincePlainStatMapper { toEntity() }
    )
}

/**
 * Map a [List] of [ProvinceStatPlainDbModel] to a [ProvinceStat]
 * NOTE: [ProvinceStatPlainDbModel]s are supposed to refer all to the same Province!!
 *
 * @author Davide Farella
 */
internal class ProvinceFullStatDbModelMapper(
    private val provincePlainMapper: ProvincePlainDbModelMapper,
    private val provinceStatPlainMapper: ProvinceStatPlainDbModelMapper
) : DatabaseModelMapper<List<ProvinceStatPlainDbModel>, ProvinceFullStat> {

    override fun List<ProvinceStatPlainDbModel>.toEntity() = ProvinceFullStat(
        province = provincePlainMapper { first().toEntity() },
        stats = map(provinceStatPlainMapper) { it.toEntity() }
    )
}
// endregion

// region Plain mappers
internal class ProvincePlainDbModelMapper(
    private val locationMapper: LocationDbModelMapper
) : DatabaseModelMapper<ProvinceStatPlainDbModel, Province> {

    override fun ProvinceStatPlainDbModel.toEntity() =
        Province(id, name, locationMapper { (lat to lng).toEntity() })
}
// endregion
