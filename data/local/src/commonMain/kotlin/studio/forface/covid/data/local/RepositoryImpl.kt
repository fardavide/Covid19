package studio.forface.covid.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import studio.forface.covid.data.local.mapper.*
import studio.forface.covid.data.local.mapper.MultiCountryDbModelMapper
import studio.forface.covid.data.local.mapper.ProvinceDbModelMapper
import studio.forface.covid.data.local.mapper.SingleCountryDbModelMapper
import studio.forface.covid.data.local.mapper.StatDbModelMapper
import studio.forface.covid.data.local.utils.TransactionProvider
import studio.forface.covid.data.local.utils.asListFlow
import studio.forface.covid.domain.entity.*
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.Province
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.mapper.map

/**
 * Default Implementation of [Repository]
 * @author Davide Farella
 */
internal class RepositoryImpl(
    private val transaction: TransactionProvider,
    private val world: WorldQueries,
    private val country: CountryQueries,
    private val province: ProvinceQueries,
    private val singleCountryMapper: SingleCountryDbModelMapper,
    private val multiCountryMapper: MultiCountryDbModelMapper,
    private val provinceMapper: ProvinceDbModelMapper,
    private val statMapper: StatDbModelMapper
) : Repository {

    override fun getCountries(): Flow<List<Country>> =
        country.selectAllCountriesWithProvinces().asListFlow()
            .map(multiCountryMapper) { it.toEntity() }

    override fun getProvinces(id: CountryId): Flow<List<Province>> =
        country.selectCountryWithProvincesById(id).asListFlow()
            .map(singleCountryMapper) { it.toEntity() }.map { it.provinces }

    override suspend fun storeCountries(countries: List<Country>) {
        transaction {
            for (c in countries) {
                country.insert(c.id, c.name)
                
                for (p in c.provinces) {
                    province.insert(p.id, c.id, p.name, p.location.lat, p.location.lng)
                }
            }
        }
    }

    override fun getWorldStat(): Flow<WorldStat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWorldFullStat(): Flow<WorldFullStat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCountrySmallStat(id: CountryId): Flow<CountrySmallStat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCountryStat(id: CountryId): Flow<CountryStat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCountryFullStat(id: CountryId): Flow<CountryFullStat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProvinceStat(countryId: CountryId, id: ProvinceId): Flow<ProvinceStat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProvinceFullStat(countryId: CountryId, id: ProvinceId): Flow<ProvinceFullStat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun store(stat: WorldStat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun store(stat: WorldFullStat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun store(stat: CountrySmallStat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun store(stat: CountryStat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun store(stat: CountryFullStat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun store(stat: ProvinceStat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun store(stat: ProvinceFullStat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

// TODO: find better place
private val WorldId = Id("world")
