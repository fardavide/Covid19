package studio.forface.covid.domain.util

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import kotlinx.coroutines.delay
import kotlin.time.Duration

suspend fun delay(duration: Duration) = delay(duration.toLongMilliseconds())

fun DateTime.formatDate() = format(DefaultDateFormat)
fun DateTime.formatTime() = format(DefaultTimeFormat)
fun DateTime.formatDateTime() = format(DefaultDateTimeFormat)

val DefaultDateFormat = DateFormat("EEE, dd MMM yyyy")
val DefaultTimeFormat = DateFormat("HH:mm:ss z")
val DefaultDateTimeFormat = DateFormat("${DefaultDateFormat.format} ${DefaultTimeFormat.format}")
