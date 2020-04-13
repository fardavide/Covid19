package studio.forface.covid.domain.entity

import com.soywiz.klock.DateTime

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
