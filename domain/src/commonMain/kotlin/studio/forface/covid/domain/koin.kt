package studio.forface.covid.domain

import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.covid.domain.gateway.NoCacheRepository
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.usecase.*

private val useCaseModule = module {

    // Get
    factory { GetCountries(repository = get(), syncCountries = get()) }
    factory { GetCountryFullStat(repository = get(), syncCountryFullStat = get()) }
    factory { GetProvinces(repository = get(), syncProvinces = get()) }
    factory { GetWorldStat(repository = get(), syncWorldStat = get()) }
    factory { GetWorldFullStat(repository = get(), syncWorldFullStat = get()) }

    // Sync
    factory { SyncCountries(api = get(), repository = get()) }
    factory { SyncCountryFullStat(api = get(), repository = get()) }
    factory { SyncProvinces(api = get(), repository = get()) }
    factory { SyncWorldStat(api = get(), repository = get()) }
    factory { SyncWorldFullStat(api = get(), repository = get()) }
}

val domainModule = module {

} + useCaseModule

// region Utils
operator fun Module.plus(others: Collection<Module>) = listOf(this) + others
// endregion
