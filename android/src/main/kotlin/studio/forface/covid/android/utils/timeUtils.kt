package studio.forface.covid.android.utils

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.soywiz.klock.DateTime
import studio.forface.covid.android.R
import studio.forface.covid.domain.util.formatHours

/**
 * @return [CharSequence] of time formatted relatively to the current time.
 * Examples:
 * * 'Today at 10'
 * * 'Today at 4pm'
 * * 'Yesterday at 11'
 * * '3 days ago'
 *
 * @param context needed for get localised String resources, like 'today'
 */
fun DateTime.formatRelative(context: Context) = formatRelative(DateTime.now(), context)

// TODO: temporary solution given by problems with mocking DateTime.now()
@VisibleForTesting
fun DateTime.formatRelative(now: DateTime, context: Context): CharSequence {
    return when (val dayDiff = (now.startOfDay - startOfDay).days.toInt()) {
        0 -> context.getString(R.string.today_at_arg, formatHours().orMidnight(context))
        1 -> context.getString(R.string.yesterday_at_arg, formatHours().orMidnight(context))
        else -> context.getString(R.string.days_ago_arg, dayDiff)
    }
}

private fun String.orMidnight(context: Context) =
    if (this == "0") context.getString(R.string.midnight) else this
