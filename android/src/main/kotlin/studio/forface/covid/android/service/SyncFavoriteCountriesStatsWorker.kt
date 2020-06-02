package studio.forface.covid.android.service

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.annotation.StringRes
import androidx.core.text.buildSpannedString
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import org.koin.core.inject
import studio.forface.covid.android.R
import studio.forface.covid.android.Router
import studio.forface.covid.android.utils.toThousandsEmphasizedText
import studio.forface.covid.domain.entity.CountrySmallStat
import studio.forface.covid.domain.usecase.GetNewFavoriteCountriesStatsDiff
import studio.forface.covid.domain.util.takeIfGreaterThanZero
import studio.forface.covid.domain.util.takeIfNotEmpty
import studio.forface.fluentnotifications.enum.NotificationImportance
import studio.forface.fluentnotifications.showNotification
import kotlin.time.Duration
import kotlin.time.seconds

/**
 * A worker that periodically check availability of new Stats for Favorites Countries
 * Inherit from [BaseWorker]
 *
 * @author Davide Farella
 */
class SyncFavoriteCountriesStatsWorker(
    appContext: Context,
    params: WorkerParameters
) : BaseWorker(appContext, params) {

    private val getNewFavoriteCountriesStatsDiff by inject<GetNewFavoriteCountriesStatsDiff>()
    private val router by inject<Router>()

    override suspend fun doWork(): Result {
        return try {
            getNewFavoriteCountriesStatsDiff().takeIfNotEmpty()?.let(::showNotifications)
            success()
        } catch (t: Throwable) {
            failure(t)
        }
    }


    private fun showNotifications(statsDiff: List<CountrySmallStat>) {
        statsDiff.forEachIndexed(::showNotification)
    }

    private fun showNotification(index: Int, statDiff: CountrySmallStat) {
        val groupId = applicationContext.resources.getInteger(R.integer.notification_new_stats)
        val c = applicationContext
        val (confirmed, deaths, recovered) = statDiff.stat

        // Add 1 to index to ensure the first item to do not clash with notification group's id
        val id = groupId + index + 1
        applicationContext.showNotification(id = id) {

            behaviour {
                importance = NotificationImportance.HIGH
            }

            channel {
                idRes = R.string.notification_new_stats_channel_id
                nameRes = R.string.notification_new_stats_channel_name
                descriptionRes = R.string.notification_new_stats_channel_desc
            }

            groupBy(id = groupId) {
                titleRes = R.string.notification_new_stats_channel_name
            }

            notification {
                title = c.getString(
                    R.string.notification_new_stats_title_args,
                    statDiff.country.name.s
                )
                contentText = buildSpannedString {
                    confirmed.takeIfGreaterThanZero()?.let {
                        appendln(R.string.notification_new_stats_content_confirmed, it)
                    }
                    deaths.takeIfGreaterThanZero()?.let {
                        appendln(R.string.notification_new_stats_content_deaths, it)
                    }
                    recovered.takeIfGreaterThanZero()?.let {
                        appendln(R.string.notification_new_stats_content_recovered, it)
                    }
                }
                smallIconRes = R.drawable.ic_notification_virus

                onContentAction { startActivity(router.countryStatIntent(statDiff.country.id)) }
            }
        }
    }

    private fun SpannableStringBuilder.appendln(@StringRes prefixRes: Int, data: Int) {
        append(applicationContext.getString(prefixRes))
        appendln(data.toThousandsEmphasizedText())
    }


    class Enqueuer(
        override val workManager: WorkManager,
        private val repeatInterval: Duration,
        private val flexTimeInterval: Duration = repeatInterval
    ) : BaseWorker.Enqueuer {

        override fun invoke(replace: Boolean /* default is `true` */) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = PeriodicWorkRequestBuilder<SyncFavoriteCountriesStatsWorker>(
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

private const val NAME = "SyncFavoriteCountriesStatsWorker"
