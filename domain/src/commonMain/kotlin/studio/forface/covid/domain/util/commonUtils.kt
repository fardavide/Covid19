package studio.forface.covid.domain.util

import kotlin.time.Duration

/**
 * Run [block] indefinitely with an interval defined by [refreshInterval]
 * If [block] throws an exception, it will retry after [errorInterval]
 *
 * @param block [RepeatingBlock]
 */
@Suppress("TooGenericExceptionCaught")
suspend inline fun repeatCatching(refreshInterval: Duration, errorInterval: Duration, block: RepeatingBlock) {
    var count = 0

    while (true) {
        try {
            block(count++)
            delay(refreshInterval)
        } catch (t: Throwable) {
            delay(errorInterval)
        }
    }
}

/**
 * Lambda that is intended to be executed multiple times.
 * `count` is the number of current iteration
 */
typealias RepeatingBlock = (count: Int) -> Unit
