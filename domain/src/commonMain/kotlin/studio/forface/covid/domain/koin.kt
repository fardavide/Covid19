package studio.forface.covid.domain

import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import studio.forface.covid.domain.usecase.GetCountries
import studio.forface.covid.domain.usecase.GetCountryFullStat
import studio.forface.covid.domain.usecase.GetCountryStat
import studio.forface.covid.domain.usecase.GetNewFavoriteCountriesStatsDiff
import studio.forface.covid.domain.usecase.GetProvinces
import studio.forface.covid.domain.usecase.GetWorldFullStat
import studio.forface.covid.domain.usecase.GetWorldStat
import studio.forface.covid.domain.usecase.SearchCountry
import studio.forface.covid.domain.usecase.SyncCountries
import studio.forface.covid.domain.usecase.SyncCountryFullStat
import studio.forface.covid.domain.usecase.SyncCountryStat
import studio.forface.covid.domain.usecase.SyncFavoriteCountriesStats
import studio.forface.covid.domain.usecase.SyncProvinces
import studio.forface.covid.domain.usecase.SyncWorldFullStat
import studio.forface.covid.domain.usecase.SyncWorldStat
import studio.forface.covid.domain.usecase.UpdateCountryFavorite
import studio.forface.covid.domain.usecase.updates.DownloadUpdate
import studio.forface.covid.domain.usecase.updates.DownloadUpdateIfAvailable
import studio.forface.covid.domain.usecase.updates.GetInstallableUpdate

val ConfigurationNameQualifier = qualifier("configuration_name")
val UpdatesDirectoryQualifier = qualifier("updates_directory")

private val updatesModule = module {

    factory {
        DownloadUpdate(
            api = get(),
            repository = get(),
            buildDownloadableUpdateFileName = get()
        )
    }
    factory {
        DownloadUpdateIfAvailable(
            api = get(),
            repository = get(),
            getAppVersion =get(),
            getInstallableUpdate = get(),
            buildDownloadableUpdateFileName = get()
        )
    }
    factory {
        GetInstallableUpdate(
            api = get(),
            repository = get(),
            buildDownloadableUpdateFileName = get()
        )
    }
}

private val useCaseModule = module {

    // Get
    factory { GetCountries(repository = get(), syncCountries = get()) }
    factory { GetCountryStat(repository = get(), syncCountryStat = get()) }
    factory { GetCountryFullStat(repository = get(), syncCountryFullStat = get()) }
    factory { GetProvinces(repository = get(), syncProvinces = get()) }
    factory { GetWorldStat(repository = get(), syncWorldStat = get()) }
    factory { GetWorldFullStat(repository = get(), syncWorldFullStat = get()) }

    // Sync
    factory { SyncCountries(api = get(), repository = get()) }
    factory { SyncCountryStat(api = get(), repository = get()) }
    factory { SyncCountryFullStat(api = get(), repository = get()) }
    factory { SyncProvinces(api = get(), repository = get()) }
    factory { SyncWorldStat(api = get(), repository = get()) }
    factory { SyncWorldFullStat(api = get(), repository = get()) }

    // Favorites
    factory { GetNewFavoriteCountriesStatsDiff(repository = get(), syncFavoriteCountriesStats = get()) }
    factory { SyncFavoriteCountriesStats(api = get(), repository = get()) }
    factory { UpdateCountryFavorite(repository = get()) }

    // Search
    factory { SearchCountry(repository = get(), syncCountries = get()) }

} + updatesModule

val domainModule = module {

} + useCaseModule

// region Utils
operator fun Module.plus(others: Collection<Module>) = listOf(this) + others
// endregion
