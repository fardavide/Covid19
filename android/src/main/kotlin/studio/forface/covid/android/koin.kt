package studio.forface.covid.android

import android.content.Context
import androidx.work.WorkManager
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.LogcatLogger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import studio.forface.covid.android.mapper.CountryStatUiModelMapper
import studio.forface.covid.android.service.DownloadUpdateWorker
import studio.forface.covid.android.service.SyncFavoriteCountriesStatsWorker
import studio.forface.covid.android.viewmodel.CountryStatViewModel
import studio.forface.covid.android.viewmodel.SearchViewModel
import studio.forface.covid.data.dataModule
import studio.forface.covid.domain.UpdatesDirectoryQualifier
import studio.forface.covid.domain.domainModule
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.Directory
import studio.forface.covid.domain.entity.plus
import studio.forface.covid.domain.util.DispatcherProvider
import kotlin.time.hours
import kotlin.time.minutes

private val serviceModule = module {
    factory { WorkManager.getInstance(get()) }

    factory(qualifier<DownloadUpdateWorker>()) {
        DownloadUpdateWorker.Enqueuer(
            workManager = get(),
            repeatInterval = 2.hours
        )
    }
    factory(qualifier<SyncFavoriteCountriesStatsWorker>()) {
        SyncFavoriteCountriesStatsWorker.Enqueuer(
            workManager = get(),
            repeatInterval = 30.minutes
        )
    }
}

private val mapperModule = module {
    factory { CountryStatUiModelMapper(context = get()) }
}

private val viewModelModule = module {
    viewModel { (countryId: CountryId) ->
        CountryStatViewModel(
            countryId,
            getCountryStat = get(),
            mapper = get(),
            dispatcherProvider = get()
        )
    }
    viewModel {
        SearchViewModel(
            searchCountry = get(),
            updateCountryFavorite = get(),
            dispatcherProvider = get()
        )
    }
} + domainModule + mapperModule

val androidModule = module {

    factory(UpdatesDirectoryQualifier) {
        Directory(get<Context>().cacheDir) + "Updates"
    }

    single { Kermit(LogcatLogger()) }

    single<DispatcherProvider> {
        object : DispatcherProvider {

            /** [CoroutineDispatcher] mean to run IO operations */
            override val Io = Dispatchers.IO

            /** [CoroutineDispatcher] mean to run computational operations */
            override val Comp = Dispatchers.Default

            /** [CoroutineDispatcher] mean to run on main thread */
            override val Main = Dispatchers.Main
        }
    }

} + serviceModule + viewModelModule + dataModule
