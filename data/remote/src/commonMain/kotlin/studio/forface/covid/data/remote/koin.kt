package studio.forface.covid.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import studio.forface.covid.data.remote.mapper.CountryFromFullStatApiModelMapper
import studio.forface.covid.data.remote.mapper.CountryFromSmallStatApiModelMapper
import studio.forface.covid.data.remote.mapper.CountryFromStatApiModelMapper
import studio.forface.covid.data.remote.mapper.CountryFullStatApiModelMapper
import studio.forface.covid.data.remote.mapper.CountryIdApiModelMapper
import studio.forface.covid.data.remote.mapper.CountrySmallStatApiModelMapper
import studio.forface.covid.data.remote.mapper.CountryStatApiModelMapper
import studio.forface.covid.data.remote.mapper.LocationApiModelMapper
import studio.forface.covid.data.remote.mapper.NameApiModelMapper
import studio.forface.covid.data.remote.mapper.ProvinceFromFullStatApiModelMapper
import studio.forface.covid.data.remote.mapper.ProvinceFromStatApiModelMapper
import studio.forface.covid.data.remote.mapper.ProvinceFullStatApiModelMapper
import studio.forface.covid.data.remote.mapper.ProvinceIdApiModelMapper
import studio.forface.covid.data.remote.mapper.ProvinceStatApiModelMapper
import studio.forface.covid.data.remote.mapper.StatApiModelMapper
import studio.forface.covid.data.remote.mapper.StatParamsMapper
import studio.forface.covid.data.remote.mapper.TimestampApiModelMapper
import studio.forface.covid.data.remote.mapper.UnixTimeApiModelMapper
import studio.forface.covid.data.remote.mapper.UpdateVersionApiModelMapper
import studio.forface.covid.data.remote.mapper.UpdateVersionTimestampApiModelMapper
import studio.forface.covid.data.remote.mapper.WorldFromFullStatApiModelMapper
import studio.forface.covid.data.remote.mapper.WorldFromStatApiModelMapper
import studio.forface.covid.data.remote.mapper.WorldFullStatApiModelMapper
import studio.forface.covid.data.remote.mapper.WorldIdApiModelMapper
import studio.forface.covid.data.remote.mapper.WorldStatApiModelMapper
import studio.forface.covid.domain.ConfigurationNameQualifier
import studio.forface.covid.domain.gateway.Api
import studio.forface.covid.domain.gateway.UpdatesApi

// Qualifier
val HostQualifier = qualifier("host")
val UpdatesRegularHostQualifier = qualifier("updates_regular_host")
val UpdatesRawHostQualifier = qualifier("updates_raw_host")

private val clientModule = module {

    single {
        HttpClient(HttpClientEngine) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}

private val mapperModule = module {

    // Fields
    factory { WorldIdApiModelMapper() }
    factory { CountryIdApiModelMapper() }
    factory { ProvinceIdApiModelMapper() }
    factory { NameApiModelMapper() }
    factory { LocationApiModelMapper() }
    factory { TimestampApiModelMapper() }
    factory { UnixTimeApiModelMapper() }

    // Models
    factory {
        CountryFromSmallStatApiModelMapper(
            idMapper = get(),
            nameMapper = get()
        )
    }
    factory {
        CountryFromStatApiModelMapper(
            provinceMapper = get(),
            idMapper = get(),
            nameMapper = get()
        )
    }
    factory {
        CountryFromFullStatApiModelMapper(
            provinceMapper = get(),
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
        ProvinceFromStatApiModelMapper(
            idMapper = get(),
            nameMapper = get(),
            locationMapper = get()
        )
    }
    factory {
        ProvinceFromFullStatApiModelMapper(
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
        WorldFromStatApiModelMapper(
            countyMapper = get(),
            idMapper = get(),
            nameMapper = get()
        )
    }
    factory {
        WorldFromFullStatApiModelMapper(
            countyMapper = get(),
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

private val updatesMapperModule = module {
    factory { UpdateVersionTimestampApiModelMapper() }
    factory { UpdateVersionApiModelMapper(timeMapper = get()) }
}

private val serviceModule = module {
    factory(HostQualifier) { "enrichman.github.io/covid19" }
    factory { CovidService(client = get(), host = get(HostQualifier)) }
} + mapperModule

private val updatesServiceModule = module {
    factory(UpdatesRegularHostQualifier) { "github.com/4face-studi0/Covid19/raw/master/releases" }
    factory(UpdatesRawHostQualifier) { "raw.githubusercontent.com/4face-studi0/Covid19/master/releases" }
    factory {
        UpdatesService(
            client = get(),
            regularHost = get(UpdatesRegularHostQualifier),
            rawHost = get(UpdatesRawHostQualifier),
            configName = get(ConfigurationNameQualifier)
        )
    }
} + updatesMapperModule

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

    factory<UpdatesApi> {
        UpdatesApiImpl(service = get(), mapper = get())
    }

} + serviceModule + updatesServiceModule + clientModule

expect val HttpClientEngine: HttpClientEngine
