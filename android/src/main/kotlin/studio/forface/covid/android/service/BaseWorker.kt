package studio.forface.covid.android.service

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.ListenableWorker.Result
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import org.koin.core.KoinComponent
import studio.forface.covid.domain.util.deserialize
import studio.forface.covid.domain.util.serialize
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

    /** @return [Result] with a [Data] of [T] */
    protected inline fun <reified T : Any> success(data: T) =
        Result.success(workDataOf(data))

    /** @see CoroutineWorker.setProgress with a [Data] of [T] */
    protected suspend inline fun <reified T : Any> setProgress(data: T) =
        setProgress(workDataOf(data))

    /** @see ListenableWorker.setProgressAsync with a [Data] of [T] */
    protected inline fun <reified T : Any> setProgressAsync(data: T) =
        setProgressAsync(workDataOf(data))

    /** @return [Data] of [T] */
    protected inline fun <reified T : Any> workDataOf(data: T) =
        androidx.work.workDataOf(SERIALIZED_DATA_KEY to data.serialize())

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


// region Serialization
/** @return [T] deserialized from receiver [Data] */
inline fun <reified T : Any> Data.deserialize(): T = getString(SERIALIZED_DATA_KEY)!!.deserialize()

@PublishedApi // inline
internal const val SERIALIZED_DATA_KEY = "serialized_data_key"
// endregion
