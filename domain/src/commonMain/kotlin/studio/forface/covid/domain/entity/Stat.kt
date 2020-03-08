package studio.forface.covid.domain.entity

import com.soywiz.klock.DateTime

data class Stat(
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int,
    val timestamp: DateTime
)
