package studio.forface.covid.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import studio.forface.covid.data.remote.mapper.*
import studio.forface.covid.data.remote.mapper.IdApiModelMapper
import studio.forface.covid.data.remote.mapper.NameApiModelMapper
import studio.forface.covid.data.remote.mapper.StatApiModelMapper
import studio.forface.covid.data.remote.mapper.TimestampApiModelMapper
import studio.forface.covid.data.remote.mapper.UnixTimeApiModelMapper
import studio.forface.covid.domain.gateway.Api

private val clientModule = module {

    single {
        HttpClient(HttpClientEngine) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}

private val serviceModule = module {
//    factory(HostQualifier) { "enrichman.github.io/covid19" }
//    factory { CovidService(client = get(), host = get(HostQualifier)) }
    factory { CovidService(client = get(), host = "enrichman.github.io/covid19") }
}

private val mapperModule = module {

    // Fields
    factory { IdApiModelMapper() }
    factory { NameApiModelMapper() }
    factory { LocationApiModelMapper() }
    factory { TimestampApiModelMapper() }
    factory { UnixTimeApiModelMapper() }

    // Models
    factory {
        CountryApiModelMapper(
            idMapper = get(),
            nameMapper = get()
        )
    }
    factory {
        CountrySmallStatApiModelMapper(
            countryMapper = get(),
            statParamsMapper = get()
        )
    }
    factory {
        CountryStatApiModelMapper(
            countryMapper = get(),
            provinceMapper = get(),
            statMapper = get(),
            statParamsMapper = get(),
            idMapper = get()
        )
    }
    factory {
        CountryFullStatApiModelMapper(
            countryMapper = get(),
            provinceMapper = get(),
            statMapper = get(),
            statParamsMapper = get(),
            idMapper = get()
        )
    }
    factory {
        ProvinceApiModelMapper(
            idMapper = get(),
            nameMapper = get(),
            locationMapper = get()
        )
    }
    factory {
        ProvinceStatApiModelMapper(
            provinceMapper = get(),
            statParamsMapper = get()
        )
    }
    factory {
        ProvinceFullStatApiModelMapper(
            provinceMapper = get(),
            statMapper = get(),
            statParamsMapper = get()
        )
    }
    factory {
        StatApiModelMapper(
            unixTimeMapper = get()
        )
    }
    factory {
        StatParamsMapper(
            timestampMapper = get()
        )
    }
    factory {
        WorldApiModelMapper(
            idMapper = get(),
            nameMapper = get()
        )
    }
    factory {
        WorldFullStatApiModelMapper(
            worldMapper = get(),
            countryMapper = get(),
            statMapper = get(),
            statParamsMapper = get(),
            idMapper = get()
        )
    }
    factory {
        WorldStatApiModelMapper(
            worldMapper = get(),
            countryMapper = get(),
            statMapper = get(),
            statParamsMapper = get(),
            idMapper = get()
        )
    }
}

val remoteDataModule = module {
    factory<Api> {
        ApiImpl(
            service = get(),
            countryMapper = get(),
            provinceMapper = get(),
            worldStatMapper = get(),
            worldFullStatMapper = get(),
            countrySmallStatMapper = get(),
            countryStatMapper = get(),
            countryFullStatMapper = get(),
            provinceStatMapper = get(),
            provinceFullStatMapper = get()
        )
    }

} + clientModule + serviceModule + mapperModule

expect val HttpClientEngine: HttpClientEngine

/** Koin `Qualifier` for host */
val HostQualifier = qualifier("base_url")