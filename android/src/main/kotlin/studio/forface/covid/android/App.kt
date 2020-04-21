package studio.forface.covid.android

import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import studio.forface.covid.android.service.DownloadUpdateWorker
import timber.log.Timber

abstract class AbsApp : Application() {

    /** This should be implemented with a [Module] for `studio.forface.covid.android.classic` or
     * `studio.forface.covid.android.compose` */
    abstract val androidImplModule: List<Module>

    private val downloadUpdateWorker by inject<DownloadUpdateWorker.Enqueuer>()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@AbsApp)
            modules(androidImplModule)
        }

        // Enqueue the worker for Download Updates regularly
        downloadUpdateWorker.invoke(replace = true)
    }
}
