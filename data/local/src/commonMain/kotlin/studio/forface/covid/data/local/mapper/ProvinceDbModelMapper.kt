package studio.forface.covid.data.local.mapper

import studio.forface.covid.data.local.CountryWithProvinceDbModel
import studio.forface.covid.data.local.ProvinceDbModel
import studio.forface.covid.data.local.ProvinceWrapper
import studio.forface.covid.domain.entity.Province
import studio.forface.covid.domain.invoke

internal class ProvinceDbModelMapper(
    private val locationMapper: LocationDbModelMapper
) : DatabaseModelMapper<ProvinceWrapper, Province> {

    override fun ProvinceWrapper.toEntity() = when (this) {
        is ProvinceWrapper.Standalone -> province.toEntity()
        is ProvinceWrapper.FromCounty -> countryWithProvince.toEntity()
    }

    fun ProvinceDbModel.toEntity() = Province(
        id = id,
        name = name,
        location = locationMapper { (lat to lng).toEntity() }
    )

    fun CountryWithProvinceDbModel.toEntity() = Province(
        id = provinceId,
        name = provinceName,
        location = locationMapper { (provinceLat to provinceLng).toEntity() }
    )
}
