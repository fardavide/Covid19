package studio.forface.covid.android.service

import android.content.Context
import androidx.work.*
import org.koin.core.KoinComponent
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

/**
 * A base [CoroutineWorker].
 * Every workers must inherit from this
 *
 * Inherit from [CoroutineWorker]
 * Implements [KoinComponent] for DI
 *
 * @author Davide Farella
 */
abstract class BaseWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params), KoinComponent {

    protected fun success() = Result.success()
    protected fun failure(t: Throwable) = Result.failure().also { Timber.e(t) }
    protected fun retry(t: Throwable) = Result.retry().also { Timber.e(t) }

    /**
     * An element that can enqueue a [BaseWorker]
     * This will be injected for enqueue a [BaseWorker] without calling static methods
     */
    interface Enqueuer {
        val workManager: WorkManager

        operator fun invoke(replace: Boolean = true)

        // region utils
        fun WorkManager.enqueueUniquePeriodicWork(
            name: String,
            replace: Boolean,
            periodicWork: PeriodicWorkRequest
        ) = enqueueUniquePeriodicWork(
            name,
            if (replace) ExistingPeriodicWorkPolicy.REPLACE else ExistingPeriodicWorkPolicy.KEEP,
            periodicWork
        )

        fun <B : WorkRequest.Builder<*, *>, W : WorkRequest> WorkRequest.Builder<B, W>.setBackoffCriteria(
            policy: BackoffPolicy,
            duration: Duration
        ) = setBackoffCriteria(policy, duration.inSeconds.toLong(), TimeUnit.SECONDS)
        // endregion
    }
}

// Cannot be member of 'Enqueuer' because inline
inline fun <reified W : ListenableWorker> PeriodicWorkRequestBuilder(
    repeatInterval: Duration,
    flexTimeInterval: Duration
) = PeriodicWorkRequest.Builder(
    W::class.java,
    repeatInterval.inSeconds.toLong(),
    TimeUnit.SECONDS,
    flexTimeInterval.inSeconds.toLong(),
    TimeUnit.SECONDS
)
