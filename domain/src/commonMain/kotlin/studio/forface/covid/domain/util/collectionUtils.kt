package studio.forface.covid.domain.util

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * Map concurrently each [T] element in the [Iterable]
 * @see map
 * @return [List] of [V]
 */
suspend inline fun <T, V> Iterable<T>.mapAsync(crossinline mapper: suspend (T) -> V) =
    coroutineScope {
        map { async { mapper(it) } }.map { it.await() }
    }

/** @return receiver [Collection] if not empty, else `null` */
fun <C : Collection<T>, T> C.takeIfNotEmpty() = takeIf { isNotEmpty() }

