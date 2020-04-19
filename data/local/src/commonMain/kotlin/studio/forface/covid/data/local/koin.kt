package studio.forface.covid.data.local

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import studio.forface.covid.data.local.mapper.*
import studio.forface.covid.data.local.utils.TransactionProvider
import studio.forface.covid.domain.UpdatesDirectoryQualifier
import studio.forface.covid.domain.entity.*
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
    factory { SingleCountryDbModelMapper(provinceMapper = get()) }
    factory { MultiCountryDbModelMapper(singleCountyMapper = get(), provinceMapper = get()) }
    factory { CountrySmallStatDbModelMapper(countryPlainMapper = get(), countyStatPlainMapper = get()) }
    factory {
        CountryStatDbModelMapper(
            countryPlainMapper = get(),
            countyStatPlainMapper = get(),
            provinceStatMapper = get()
        )
    }
    factory {
        CountryFullStatDbModelMapper(
            countryPlainMapper = get(),
            countyStatPlainMapper = get(),
            provinceStatMapper = get()
        )
    }
    factory { SingleCountryPlainDbModelMapper(provincePlainMapper = get()) }

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
            statAdapter = get(StatAdapterQualifier)
        )
    }

    factory { get<Database>().worldQueries }
    factory { get<Database>().countryQueries }
    factory { get<Database>().provinceQueries }
    factory { get<Database>().statQueries }
} + adapterModule + mapperModule

val localDataModule = module {
    single<Repository> {
        RepositoryImpl(
            transaction = get(),
            worldQueries = get(),
            countryQueries = get(),
            provinceQueries = get(),
            statQueries = get(),
            worldStatMapper = get(),
            worldFullStatMapper = get(),
            singleCountryMapper = get(),
            multiCountryMapper = get(),
            countrySmallStatMapper = get(),
            countryStatMapper = get(),
            countryFullStatMapper = get(),
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
