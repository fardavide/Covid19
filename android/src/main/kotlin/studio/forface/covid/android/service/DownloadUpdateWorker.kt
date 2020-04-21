package studio.forface.covid.android.service

import android.content.Context
import androidx.work.*
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

    override suspend fun doWork(): Result {
        runCatching { downloadUpdateIfAvailable() }
            .onSuccess { return success() }
            .onFailure { return failure(it) }

        throw AssertionError()
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
