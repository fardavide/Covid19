package studio.forface.covid.domain

import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.covid.domain.usecase.GetCountries
import studio.forface.covid.domain.usecase.GetCountryFullStat
import studio.forface.covid.domain.usecase.GetProvinces
import studio.forface.covid.domain.usecase.GetWorldFullStat
import studio.forface.covid.domain.usecase.GetWorldStat
import studio.forface.covid.domain.usecase.SearchCountry
import studio.forface.covid.domain.usecase.SyncCountries
import studio.forface.covid.domain.usecase.SyncCountryFullStat
import studio.forface.covid.domain.usecase.SyncProvinces
import studio.forface.covid.domain.usecase.SyncWorldFullStat
import studio.forface.covid.domain.usecase.SyncWorldStat

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

    // Search
    factory { SearchCountry(repository = get(), syncCountries = get()) }
}

val domainModule = module {

} + useCaseModule

// region Utils
operator fun Module.plus(others: Collection<Module>) = listOf(this) + others
// endregion
