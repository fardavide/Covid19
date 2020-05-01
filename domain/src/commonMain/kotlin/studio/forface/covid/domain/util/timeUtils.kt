package studio.forface.covid.domain.util

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeRange
import kotlinx.coroutines.delay
import kotlin.time.Duration

suspend fun delay(duration: Duration) = delay(duration.toLongMilliseconds())

fun DateTime.formatDate() = format(DefaultDateFormat)
fun DateTime.formatTime() = format(DefaultTimeFormat)
fun DateTime.formatHours() = format(DateFormat("h a")).replace("12 am", "0").substringBefore(" am")
fun DateTime.formatDateTime() = format(DefaultDateTimeFormat)

val DefaultDateFormat = DateFormat("EEE, dd MMM yyyy")
val DefaultTimeFormat = DateFormat("HH:mm:ss")
val DefaultDateTimeFormat = DateFormat("${DefaultDateFormat.format} ${DefaultTimeFormat.format}")

/**
 * @return [com.soywiz.klock.DateTimeSpan] from given [duration]
 * Precision is in seconds
 */
fun DateTimeSpan(duration: Duration) = com.soywiz.klock.DateTimeSpan(duration.inSeconds.toInt())
