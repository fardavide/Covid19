package studio.forface.covid.domain.entity

data class Stat(
    val total: Int,
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int
)
