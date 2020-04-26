package studio.forface.covid.domain.entity

import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeSpan
import com.soywiz.klock.MonthSpan
import com.soywiz.klock.TimeSpan
import kotlinx.coroutines.coroutineScope
import studio.forface.covid.domain.util.DateTimeSpan
import kotlin.math.abs
import kotlin.time.Duration

data class Stat(
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int,
    val timestamp: DateTime
) {

    infix fun equalsNoTime(other: Stat) =
        confirmed == other.confirmed && deaths == other.deaths && recovered == other.recovered
}

/** @return [List] of [Stat] composed from the receiver [Stat] plus [others] */
operator fun Stat.plus(others: List<Stat>) = listOf(this) + others

operator fun Stat.minus(other: Stat) = Stat(
    confirmed = confirmed - other.confirmed,
    deaths = deaths - other.deaths,
    recovered = recovered - other.recovered,
    timestamp = other.timestamp
)

/**
 * Create a diff from the most recent [Stat], based on given [duration]
 * Note: premises are:
 * * the [Collection] is already ordered
 * * the [Collection] is not Empty
 *
 * @return [Stat]
 */
suspend fun <C : Collection<Stat>> C.diff(duration: Duration) = diff(DateTimeSpan(duration))

/**
 * Create a diff from the most recent [Stat], based on given [span] of [TimeSpan]
 * Note: premises are:
 * * the [Collection] is already ordered
 * * the [Collection] is not Empty
 *
 * @return [Stat]
 */
suspend fun <C : Collection<Stat>> C.diff(
    span: TimeSpan
) = diff(DateTimeSpan(MonthSpan(0), span))

/**
 * Create a diff from the most recent [Stat], based on given [span] of [DateTimeSpan]
 * Note: premises are:
 * * the [Collection] is already ordered
 * * the [Collection] is not Empty
 *
 * @return [Stat]
 */
suspend fun <C : Collection<Stat>> C.diff(
    span: DateTimeSpan
): Stat = coroutineScope {
    check(isNotEmpty()) { "The Collection is empty" }

    // Take most recent stat
    val mostRecent = first()

    // Take item until the last one's timestamp is older from now, minus the given duration
    val now = DateTime.now()
    var shouldTake = true
    val inRange = takeWhile { stat ->
        shouldTake
            .also { shouldTake = stat.timestamp + span > now }
    }

    // Find item with timestamp closest to mostRecent's timestamp, minus the given duration
    mostRecent - inRange.takeLast(2).minBy {
        abs((mostRecent.timestamp - span - it.timestamp).seconds)
    }!!
}
