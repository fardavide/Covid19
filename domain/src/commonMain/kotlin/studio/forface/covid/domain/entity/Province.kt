package studio.forface.covid.domain.entity

data class Province(
    val id: ProvinceId,
    val name: Name,
    val location: Location
)

/**
 * Small stat for a [Province].
 * It includes the [Province] and the last [Stat]
 */
data class ProvinceStat(
    val province: Province,
    val stat: Stat
)

/**
 * Small stat for a [Province].
 * It includes a List of [Stat]
 */
data class ProvinceFullStat(
    val province: Province,
    val stats: List<Stat>
)
