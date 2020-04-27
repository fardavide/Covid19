package studio.forface.covid.domain.util

fun Int.takeIfGreaterThanZero() = takeIf { it > 0 }
