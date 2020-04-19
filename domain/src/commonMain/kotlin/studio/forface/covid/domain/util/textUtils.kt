package studio.forface.covid.domain.util

fun String.takeIfNotBlank() = takeIf { it.isNotBlank() }

/**
 * Assert that receiver [String.isNotBlank]
 * @return receiver [String] or
 * @throws IllegalStateException is blank
 */
inline fun String.checkNotBlank(lazyMessage: () -> Any) =
    apply { check(isNotBlank(), lazyMessage) }
