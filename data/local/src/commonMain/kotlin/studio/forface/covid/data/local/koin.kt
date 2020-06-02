package studio.forface.covid.data.local

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import studio.forface.covid.data.local.mapper.CountryWithProvinceStatPlainList_CountryFullStat
import studio.forface.covid.data.local.mapper.CountryStatPlainList_CountrySmallStat
import studio.forface.covid.data.local.mapper.CountryWithProvinceStatPlainList_CountryStat
import studio.forface.covid.data.local.mapper.CountryStatFromCountryWithProvinceStatPlainDbModelMapper
import studio.forface.covid.data.local.mapper.CountryStatPlainDbModelMapper
import studio.forface.covid.data.local.mapper.CountryWithProvinceDbModelMapper
import studio.forface.covid.data.local.mapper.LocationDbModelMapper
import studio.forface.covid.data.local.mapper.CountryWithProvinceList_MultiCountry
import studio.forface.covid.data.local.mapper.ProvinceDbModelMapper
import studio.forface.covid.data.local.mapper.ProvinceFullStatDbModelMapper
import studio.forface.covid.data.local.mapper.ProvincePlainDbModelMapper
import studio.forface.covid.data.local.mapper.ProvinceStatDbModelMapper
import studio.forface.covid.data.local.mapper.ProvinceStatPlainDbModelMapper
import studio.forface.covid.data.local.mapper.ProvinceWrapperMapper
import studio.forface.covid.data.local.mapper.CountryWithProvinceList_Country
import studio.forface.covid.data.local.mapper.CountryStatPlainList_Country
import studio.forface.covid.data.local.mapper.CountryWithProvinceStatPlainList_MultiCountryStat
import studio.forface.covid.data.local.mapper.UnixTimeDbModelMapper
import studio.forface.covid.data.local.mapper.WorldFullStatDbModelMapper
import studio.forface.covid.data.local.mapper.WorldPlainDbModelMapper
import studio.forface.covid.data.local.mapper.WorldStatDbModelMapper
import studio.forface.covid.data.local.mapper.WorldStatFromWorldWithProvincesStatPlainDModelMapper
import studio.forface.covid.data.local.mapper.WorldStatPlainDModelMapper
import studio.forface.covid.data.local.utils.TransactionProvider
import studio.forface.covid.domain.UpdatesDirectoryQualifier
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.Id
import studio.forface.covid.domain.entity.Name
import studio.forface.covid.domain.entity.ProvinceId
import studio.forface.covid.domain.entity.WorldId
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.gateway.UpdatesRepository

// Fields
private val IdAdapterQualifier = qualifier("IdAdapter")
private val WorldIdAdapterQualifier = qualifier("WorldIdAdapter")
private val CountryIdAdapterQualifier = qualifier("CountryIdAdapter")
private val ProvinceIdAdapterQualifier = qualifier("ProvinceIdAdapter")
private val NameAdapterQualifier = qualifier("nameAdapter")

// Models
private val WorldAdapterQualifier = qualifier("WorldAdapter")
private val CountryAdapterQualifier = qualifier("CountryAdapter")
private val ProvinceAdapterQualifier = qualifier("ProvinceAdapter")
private val StatAdapterQualifier = qualifier("StatAdapter")
private val FavoriteAdapterQualifier = qualifier("FavoriteAdapter")

private val adapterModule = module {
    // Fields
    factory<ColumnAdapter<Id, String>>(IdAdapterQualifier) { IdAdapter() }
    factory<ColumnAdapter<WorldId, String>>(WorldIdAdapterQualifier) { WorldIdAdapter() }
    factory<ColumnAdapter<CountryId, String>>(CountryIdAdapterQualifier) { CountryIdAdapter() }
    factory<ColumnAdapter<ProvinceId, String>>(ProvinceIdAdapterQualifier) { ProvinceIdAdapter() }
    factory<ColumnAdapter<Name, String>>(NameAdapterQualifier) { NameAdapter() }

    // Models
    factory(WorldAdapterQualifier) {
        World.Adapter(
            idAdapter = get(WorldIdAdapterQualifier),
            nameAdapter = get(NameAdapterQualifier)
        )
    }
    factory(CountryAdapterQualifier) {
        Country.Adapter(
            idAdapter = get(CountryIdAdapterQualifier),
            nameAdapter = get(NameAdapterQualifier)
        )
    }
    factory(ProvinceAdapterQualifier) {
        Province.Adapter(
            idAdapter = get(ProvinceIdAdapterQualifier),
            country_idAdapter = get(CountryIdAdapterQualifier),
            nameAdapter = get(
                NameAdapterQualifier
            )
        )
    }
    factory(StatAdapterQualifier) { Stat.Adapter(parent_idAdapter = get(IdAdapterQualifier)) }
    factory(FavoriteAdapterQualifier) { Favorite.Adapter(idAdapter = get(IdAdapterQualifier)) }
}

private val mapperModule = module {
    // World
    factory {
        WorldStatDbModelMapper(
            worldPlainMapper = get(),
            worldStatPlainMapper = get(),
            countryStatMapper = get()
        )
    }
    factory {
        WorldFullStatDbModelMapper(
            worldPlainMapper = get(),
            worldStatPlainMapper = get(),
            countryStatMapper = get()
        )
    }
    factory { WorldPlainDbModelMapper(singleCountryPlainMapper = get()) }
    factory { WorldStatPlainDModelMapper(timeMapper = get()) }
    factory { WorldStatFromWorldWithProvincesStatPlainDModelMapper(timeMapper = get()) }

    // Country
    factory { CountryWithProvinceList_Country(provinceMapper = get()) }
    factory { CountryWithProvinceList_MultiCountry(singleCountyMapper = get(), provinceMapper = get()) }
    factory { CountryStatPlainList_CountrySmallStat(countryPlainMapper = get(), countyStatPlainMapper = get()) }
    factory {
        CountryWithProvinceStatPlainList_CountryStat(
            countryPlainMapper = get(),
            countyStatPlainMapper = get(),
            provinceStatMapper = get()
        )
    }
    factory {
        CountryWithProvinceStatPlainList_CountryFullStat(
            countryPlainMapper = get(),
            countyStatPlainMapper = get(),
            provinceStatMapper = get()
        )
    }
    factory { CountryWithProvinceStatPlainList_MultiCountryStat(singleCountryStatMapper = get()) }
    factory { CountryStatPlainList_Country(provincePlainMapper = get()) }

    // Province
    factory { ProvinceWrapperMapper(provinceMapper = get(), countryWithProvinceMapper = get()) }
    factory { ProvinceDbModelMapper(locationMapper = get()) }
    factory { CountryWithProvinceDbModelMapper(locationMapper = get()) }
    factory { ProvinceStatDbModelMapper(provincePlainMapper = get(), provincePlainStatMapper = get()) }
    factory { ProvinceFullStatDbModelMapper(provincePlainMapper = get(), provinceStatPlainMapper = get()) }
    factory { ProvincePlainDbModelMapper(locationMapper = get()) }

    // Stat
    factory { CountryStatPlainDbModelMapper(timeMapper = get()) }
    factory { CountryStatFromCountryWithProvinceStatPlainDbModelMapper(timeMapper = get()) }
    factory { ProvinceStatPlainDbModelMapper(timeMapper = get()) }

    // Other
    factory { LocationDbModelMapper() }
    factory { UnixTimeDbModelMapper() }
}

private val databaseModule = module {
    single {
        Database(
            driver = sqlDriver,
            worldAdapter = get(WorldAdapterQualifier),
            countryAdapter = get(CountryAdapterQualifier),
            provinceAdapter = get(ProvinceAdapterQualifier),
            statAdapter = get(StatAdapterQualifier),
            favoriteAdapter = get(FavoriteAdapterQualifier)
        )
    }

    factory { get<Database>().worldQueries }
    factory { get<Database>().countryQueries }
    factory { get<Database>().provinceQueries }
    factory { get<Database>().statQueries }
    factory { get<Database>().favoriteQueries }
} + adapterModule + mapperModule

val localDataModule = module {
    single<Repository> {
        RepositoryImpl(
            transaction = get(),
            worldQueries = get(),
            countryQueries = get(),
            provinceQueries = get(),
            statQueries = get(),
            favoriteQueries = get(),
            worldStatMapper = get(),
            worldFullStatMapper = get(),
            singleCountryMapper = get(),
            multiCountryMapper = get(),
            countrySmallStatMapper = get(),
            countryStatMapper = get(),
            countryFullStatMapper = get(),
            multiCountryStatMapper = get(),
            provinceStatMapper = get(),
            provinceFullStatMapper = get(),
            timeMapper = get()
        )
    }
    factory { TransactionProvider(database = get()) }

    single<UpdatesRepository> {
        UpdatesRepositoryImpl(updatesDirectory = get(UpdatesDirectoryQualifier))
    }

} + databaseModule

internal expect val sqlDriver: SqlDriver
