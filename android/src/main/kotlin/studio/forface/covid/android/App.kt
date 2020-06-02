package studio.forface.covid.android

import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import studio.forface.covid.android.service.DownloadUpdateWorker
import studio.forface.covid.android.service.SyncFavoriteCountriesStatsWorker

/**
 * Base [Application] that contains initialization for Android services and libs
 * @author Davide Farella
 */
abstract class AbsApp : Application() {

    /** This should be implemented with a [Module] for `studio.forface.covid.android.classic` or
     * `studio.forface.covid.android.compose` */
    abstract val androidImplModule: List<Module>

    private val downloadUpdateWorker by inject<DownloadUpdateWorker.Enqueuer>(
        qualifier<DownloadUpdateWorker>())
    private val syncFavoriteCountriesStatsWorker by inject<SyncFavoriteCountriesStatsWorker.Enqueuer>(
        qualifier<SyncFavoriteCountriesStatsWorker>())

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AbsApp)
            modules(androidImplModule)
        }

        // Enqueue the worker for Download Updates regularly
        downloadUpdateWorker(replace = true)

        // Enqueue the worker for Download and Display new Data for Favorite Countries regularly
        syncFavoriteCountriesStatsWorker(replace = true)
    }
}
