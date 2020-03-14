package studio.forface.covid.data.local

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import studio.forface.covid.data.local.utils.TransactionProvider
import studio.forface.covid.domain.entity.Id
import studio.forface.covid.domain.entity.Name
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.plus

private val adapterModule = module {
    // Fields
    factory<ColumnAdapter<Id, String>>(IdAdapterQualifier) { TODO("Not implemented") }
    factory<ColumnAdapter<Name, String>>(NameAdapterQualifier) { TODO("Not implemented") }

    // Models
    factory { Country.Adapter(idAdapter = get(IdAdapterQualifier), nameAdapter = get(NameAdapterQualifier)) }
    factory {
        Province.Adapter(
            idAdapter = get(IdAdapterQualifier), country_idAdapter = get(IdAdapterQualifier), nameAdapter = get(
                NameAdapterQualifier
            )
        )
    }
    factory { Stat.Adapter(parent_idAdapter = get(IdAdapterQualifier)) }
}

private val mapperModule = module {

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

    factory { get<Database>().countryQueries }
    factory { get<Database>().provinceQueries }
    factory { get<Database>().statQueries }
} + adapterModule + mapperModule

val localDataModule = module {
    single<Repository> {
        RepositoryImpl(
            transaction = get(),
            world = get(),
            country = get(),
            province = get(),
            singleCountryMapper = get(),
            multiCountryMapper = get(),
            provinceMapper = get(),
            statMapper = get()
        )
    }
    factory { TransactionProvider(database = get()) }

} + databaseModule

private val IdAdapterQualifier = qualifier("idAdapter")
private val NameAdapterQualifier = qualifier("nameAdapter")

internal expect val sqlDriver: SqlDriver
