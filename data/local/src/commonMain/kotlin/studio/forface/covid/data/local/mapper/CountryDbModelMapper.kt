package studio.forface.covid.data.local.mapper

import studio.forface.covid.data.local.CountryWithProvinceDbModel
import studio.forface.covid.data.local.wrap
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.invoke
import studio.forface.covid.domain.mapper.map

/**
 * Map a [List] of [CountryWithProvinceDbModel] to a [Country]
 * NOTE: [CountryWithProvinceDbModel]s are supposed to refer all to the same County!!
 *
 * @author Davide Farella
 */
internal class SingleCountryDbModelMapper(
    private val provinceMapper: ProvinceDbModelMapper
) : DatabaseModelMapper<List<CountryWithProvinceDbModel>, Country> {

    override fun List<CountryWithProvinceDbModel>.toEntity() = Country(
        id = first().id,
        name = first().name,
        provinces = map { provinceMapper { it.toEntity() } }
    )
}

/**
 * Map a [List] of [CountryWithProvinceDbModel] to a [List] of [Country]
 *
 * @author Davide Farella
 */
internal class MultiCountryDbModelMapper(
    private val singleCountyMapper: SingleCountryDbModelMapper,
    private val provinceMapper: ProvinceDbModelMapper
) : DatabaseModelMapper<List<CountryWithProvinceDbModel>, List<Country>> {

    /**
     * @return [List] of [Country] from the receiver list of [CountryWithProvinceDbModel]
     */
    override fun List<CountryWithProvinceDbModel>.toEntity() = groupBy { it.id }.map { (_, list) ->
        val country = singleCountyMapper { listOf(first()).toEntity() }
        val provinces = list.wrap.map(provinceMapper) { it.toEntity() }
        country.copy(provinces = provinces)
    }
}
