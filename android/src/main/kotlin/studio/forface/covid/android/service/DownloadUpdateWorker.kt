package studio.forface.covid.android.service

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.produceIn
import org.koin.core.KoinComponent
import org.koin.core.inject
import studio.forface.covid.domain.usecase.updates.DownloadUpdateIfAvailable
import kotlin.time.Duration
import kotlin.time.seconds

/**
 * A worker that periodically check availability of new updates for the app
 * Inherit from [BaseWorker]
 *
 * @author Davide Farella
 */
class DownloadUpdateWorker(
    appContext: Context,
    params: WorkerParameters
) : BaseWorker(appContext, params), KoinComponent {

    private val downloadUpdateIfAvailable by inject<DownloadUpdateIfAvailable>()

    override suspend fun doWork() = coroutineScope {
        try {
            for (state in downloadUpdateIfAvailable().produceIn(this)) {

                when (state) {
                    DownloadUpdateIfAvailable.State.Checking -> { /* noop */ }
                    DownloadUpdateIfAvailable.State.UpToDate -> return@coroutineScope success()
                    is DownloadUpdateIfAvailable.State.Downloading -> showProgress(state.progress)
                    DownloadUpdateIfAvailable.State.AlreadyDownloaded, DownloadUpdateIfAvailable.State.Completed -> {
                        promptInstall()
                        return@coroutineScope success()
                    }
                }
            }
        } catch (t: Throwable) {
            return@coroutineScope failure(t)
        }

        throw AssertionError("This should never happen")
    }

    private fun showProgress(progress: Float) {
        TODO("show progress")
    }

    private fun promptInstall() {
        TODO("prompt install")
    }


    class Enqueuer(
        override val workManager: WorkManager,
        private val repeatInterval: Duration,
        private val flexTimeInterval: Duration
    ) : BaseWorker.Enqueuer {

        override fun invoke(replace: Boolean /* default is `true` */) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = PeriodicWorkRequestBuilder<DownloadUpdateWorker>(
                repeatInterval,
                flexTimeInterval
            )
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 10.seconds)
                .build()

            workManager.enqueueUniquePeriodicWork(NAME, replace, request)
        }
    }
}

private const val NAME = "DownloadUpdateWorker"
