package studio.forface.covid.data.local

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import studio.forface.covid.data.local.mapper.CountryFullStatDbModelMapper
import studio.forface.covid.data.local.mapper.CountrySmallStatDbModelMapper
import studio.forface.covid.data.local.mapper.CountryStatDbModelMapper
import studio.forface.covid.data.local.mapper.CountryStatFromCountryWithProvinceStatPlainDbModelMapper
import studio.forface.covid.data.local.mapper.CountryStatPlainDbModelMapper
import studio.forface.covid.data.local.mapper.LocationDbModelMapper
import studio.forface.covid.data.local.mapper.MultiCountryDbModelMapper
import studio.forface.covid.data.local.mapper.ProvinceDbModelMapper
import studio.forface.covid.data.local.mapper.ProvinceFullStatDbModelMapper
import studio.forface.covid.data.local.mapper.ProvincePlainDbModelMapper
import studio.forface.covid.data.local.mapper.ProvinceStatDbModelMapper
import studio.forface.covid.data.local.mapper.ProvinceStatPlainDbModelMapper
import studio.forface.covid.data.local.mapper.SingleCountryDbModelMapper
import studio.forface.covid.data.local.mapper.SingleCountryPlainDbModelMapper
import studio.forface.covid.data.local.mapper.UnixTimeDbModelMapper
import studio.forface.covid.data.local.utils.TransactionProvider
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.Id
import studio.forface.covid.domain.entity.Name
import studio.forface.covid.domain.entity.ProvinceId
import studio.forface.covid.domain.entity.WorldId
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.plus

private val adapterModule = module {
    // Fields
    factory<ColumnAdapter<Id, String>>(IdAdapterQualifier) { IdAdapter() }
    factory<ColumnAdapter<WorldId, String>>(WorldIdAdapterQualifier) { WorldIdAdapter() }
    factory<ColumnAdapter<CountryId, String>>(CountryIdAdapterQualifier) { CountryIdAdapter() }
    factory<ColumnAdapter<ProvinceId, String>>(ProvinceIdAdapterQualifier) { ProvinceIdAdapter() }
    factory<ColumnAdapter<Name, String>>(NameAdapterQualifier) { NameAdapter() }

    // Models
    factory { World.Adapter(idAdapter = get(WorldIdAdapterQualifier), nameAdapter = get(NameAdapterQualifier)) }
    factory { Country.Adapter(idAdapter = get(CountryIdAdapterQualifier), nameAdapter = get(NameAdapterQualifier)) }
    factory {
        Province.Adapter(
            idAdapter = get(ProvinceIdAdapterQualifier),
            country_idAdapter = get(CountryIdAdapterQualifier),
            nameAdapter = get(
                NameAdapterQualifier
            )
        )
    }
    factory { Stat.Adapter(parent_idAdapter = get(IdAdapterQualifier)) }
}

private val mapperModule = module {
    // World

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
    factory { ProvinceDbModelMapper(locationMapper = get()) }
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
            worldAdapter = get(),
            countryAdapter = get(),
            provinceAdapter = get(),
            statAdapter = get()
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

} + databaseModule

private val IdAdapterQualifier = qualifier("idAdapter")
private val WorldIdAdapterQualifier = qualifier("WorldIdAdapter")
private val CountryIdAdapterQualifier = qualifier("CountryIdAdapter")
private val ProvinceIdAdapterQualifier = qualifier("ProvinceIdAdapter")
private val NameAdapterQualifier = qualifier("nameAdapter")

internal expect val sqlDriver: SqlDriver
