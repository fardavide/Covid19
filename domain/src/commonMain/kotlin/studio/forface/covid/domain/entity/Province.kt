package studio.forface.covid.domain.entity

data class Province(
    val id: Id,
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
 * It includes the [ProvinceStat] and a List of other [Stat]
 */
data class ProvinceFullStat(
    val lastStat: ProvinceStat,
    val otherStats: List<Stat>
)
