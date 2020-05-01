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
import studio.forface.covid.android.R
import studio.forface.covid.android.receiver.PromptUpdateInstallReceiver
import studio.forface.covid.domain.usecase.updates.DownloadUpdateIfAvailable
import studio.forface.covid.domain.usecase.updates.DownloadUpdateIfAvailable.State
import studio.forface.fluentnotifications.enum.NotificationImportance
import studio.forface.fluentnotifications.setForegroundAsync
import studio.forface.fluentnotifications.showNotification
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
                    State.Checking -> { /* noop */ }
                    is State.Downloading -> showProgress(state.progress)
                    State.UpToDate -> return@coroutineScope success()
                    is State.ReadyToInstall -> {
                        promptInstall(state.version.name)
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
        setProgressAsync(progress)

        setForegroundAsync(idRes = R.integer.notification_update_download) {

            behaviour {
                importance = NotificationImportance.LOW
            }

            channel {
                idRes = R.string.notification_update_download_channel_id
                nameRes = R.string.notification_update_download_channel_name
                descriptionRes = R.string.notification_update_download_channel_desc
            }

            notification {
                titleRes = R.string.notification_update_downloading_title
                smallIconRes = R.drawable.ic_notification_virus
                setProgress((progress * 100).toInt(), 100)
                cleanable = false
            }
        }
    }


    private fun promptInstall(versionName: String) {
        applicationContext.showNotification(idRes = R.integer.notification_update_install) {

            behaviour {
                importance = NotificationImportance.HIGH
            }

            channel {
                idRes = R.string.notification_update_install_channel_id
                nameRes = R.string.notification_update_install_channel_name
                descriptionRes = R.string.notification_update_install_channel_desc
            }

            notification {
                title = applicationContext.getString(R.string.notification_update_install_title_args, versionName)
                contentTextRes = R.string.notification_update_install_content
                smallIconRes = R.drawable.ic_notification_virus

                onContentAction { start<PromptUpdateInstallReceiver>() }
            }
        }
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
