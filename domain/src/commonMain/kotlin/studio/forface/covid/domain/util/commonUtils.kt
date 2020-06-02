package studio.forface.covid.domain.util

import kotlin.time.Duration

/**
 * Run [block] indefinitely with an interval defined by [refreshInterval]
 * If [block] throws an exception, it will retry after [errorInterval]
 *
 * @param block [RepeatingBlock]
 */
@Suppress("TooGenericExceptionCaught")
suspend inline fun repeatCatching(
    refreshInterval: Duration,
    errorInterval: Duration,
    errorBlock: (Throwable) -> Unit = {},
    block: RepeatingBlock
) {
    var count = 0

    while (true) {
        try {
            block(count++)
            delay(refreshInterval)
        } catch (t: Throwable) {
            errorBlock(t)
            delay(errorInterval)
        }
    }
}

/**
 * Lambda that is intended to be executed multiple times.
 * `count` is the number of current iteration
 */
typealias RepeatingBlock = (count: Int) -> Unit


/** @return `true` if receiver [Boolean] is `true`, else `null` */
fun Boolean.takeIfTrue() = takeIf { it }

// region vararg's
/**
 * @return `true` if there is at least one item in [args] that matches the given [predicate]
 * Example: `` any(1, 2, 3) { it < 2 } `` -> true
 */
inline fun <T> any(vararg args: T, predicate: (T) -> Boolean) = args.any(predicate)

/**
 * @return `true` if there all the items in [args] match the given [predicate]
 * Example: `` all(1, 2, 3) { it < 4 } `` -> true
 */
inline fun <T> all(vararg args: T, predicate: (T) -> Boolean) = args.all(predicate)

/**
 * @return `true` if none of the items in [args] match the given [predicate]
 * Example: `` none(1, 2, 3) { it > 4 } `` -> true
 */
inline fun <T> none(vararg args: T, predicate: (T) -> Boolean) = args.all(predicate)

/**
 * Executes a [block] for every item in [args]
 * Example: `` forEach(1, 2, 3) { println(it) } ``
 */
inline fun <T> forEach(vararg args: T, block: (T) -> Unit) {
    args.forEach(block)
}
// endregion

