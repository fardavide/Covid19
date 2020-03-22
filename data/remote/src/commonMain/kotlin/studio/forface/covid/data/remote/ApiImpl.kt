package studio.forface.covid.data.remote

import studio.forface.covid.data.remote.mapper.CountryFromStatApiModelMapper
import studio.forface.covid.data.remote.mapper.CountryFullStatApiModelMapper
import studio.forface.covid.data.remote.mapper.CountrySmallStatApiModelMapper
import studio.forface.covid.data.remote.mapper.CountryStatApiModelMapper
import studio.forface.covid.data.remote.mapper.ProvinceFromStatApiModelMapper
import studio.forface.covid.data.remote.mapper.ProvinceFullStatApiModelMapper
import studio.forface.covid.data.remote.mapper.ProvinceStatApiModelMapper
import studio.forface.covid.data.remote.mapper.WorldFullStatApiModelMapper
import studio.forface.covid.data.remote.mapper.WorldStatApiModelMapper
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.ProvinceId
import studio.forface.covid.domain.gateway.Api
import studio.forface.covid.domain.invoke
import studio.forface.covid.domain.mapper.map

/**
 * Default implementation of [Api]
 * @author Davide Farella
 */
internal class ApiImpl(
    private val service: CovidService,
    private val countryMapper: CountryFromStatApiModelMapper,
    private val provinceMapper: ProvinceFromStatApiModelMapper,
    private val worldStatMapper: WorldStatApiModelMapper,
    private val worldFullStatMapper: WorldFullStatApiModelMapper,
    private val countrySmallStatMapper: CountrySmallStatApiModelMapper,
    private val countryStatMapper: CountryStatApiModelMapper,
    private val countryFullStatMapper: CountryFullStatApiModelMapper,
    private val provinceStatMapper: ProvinceStatApiModelMapper,
    private val provinceFullStatMapper: ProvinceFullStatApiModelMapper
) : Api {

    override suspend fun getCountries() = service.getCountries().map(countryMapper) { it.toEntity() }
    override suspend fun getCountry(id: CountryId) = countryMapper { service.getCountry(id).toEntity() }
    override suspend fun getProvinces(id: CountryId) = service.getProvinces(id).map(provinceMapper) { it.toEntity() }

    override suspend fun getWorldStat() = worldStatMapper { service.getWorldStat().toEntity() }
    override suspend fun getWorldFullStat() = worldFullStatMapper { service.getWorldFullStat().toEntity() }

    override suspend fun getCountrySmallStat(id: CountryId) =
        countrySmallStatMapper { service.getCountrySmallStat(id).toEntity() }

    override suspend fun getCountryStat(id: CountryId) =
        countryStatMapper { service.getCountryStat(id).toEntity() }

    override suspend fun getCountryFullStat(id: CountryId) =
        countryFullStatMapper { service.getCountryFullStat(id).toEntity() }

    override suspend fun getProvinceStat(countryId: CountryId, id: ProvinceId) =
        provinceStatMapper { service.getProvinceStat(countryId, id).toEntity() }

    override suspend fun getProvinceFullStat(countryId: CountryId, id: ProvinceId) =
        provinceFullStatMapper { service.getProvinceFullStat(countryId, id).toEntity() }
}
