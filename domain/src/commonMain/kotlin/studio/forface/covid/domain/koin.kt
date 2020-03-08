package studio.forface.covid.domain

import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.covid.domain.gateway.NoCacheRepository
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.usecase.GetCountries
import studio.forface.covid.domain.usecase.GetProvinces
import studio.forface.covid.domain.usecase.GetWorldStat

private val useCaseModule = module {
    factory { GetCountries(repository = get(), syncCountries = get()) }
    factory { GetProvinces(repository = get(), api = get()) }
    factory { GetWorldStat(repository = get(), api = get()) }
}

val domainModule = module {
    single<Repository> { NoCacheRepository(api = get()) }

} + useCaseModule

// region Utils
operator fun Module.plus(others: Collection<Module>) = listOf(this) + others
// endregion
