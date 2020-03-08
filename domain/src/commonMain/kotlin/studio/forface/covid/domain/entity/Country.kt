package studio.forface.covid.domain.entity

data class Country(
    val id: Id,
    val name: Name,
    val provinces: List<Province> = emptyList()
)

/**
 * Small stat for a [Country].
 * It includes the [Country] and the last [Stat]
 *
 * This is analogue to [ProvinceStat]
 */
data class CountrySmallStat(
    val country: Country,
    val stat: Stat
)

/**
 * Medium stat for a [Country].
 * It includes the [Country], the last [Stat], a List of other [Stat] and a List of [ProvinceStat], if any
 */
data class CountryStat(
    val country: Country,
    val lastStat: Stat,
    val otherStats: List<Stat>,
    val provinceStats: Map<ProvinceId, ProvinceStat> = emptyMap()
)

/**
 * Full stat for a [Country].
 * It has the same structure of [CountryStat], but the list [provinceStats] represents a List of [ProvinceFullStat]
 * instead of a List of [ProvinceStat]
 */
data class CountryFullStat(
    val country: Country,
    val lastStat: Stat,
    val otherStats: List<Stat>,
    val provinceStats: Map<ProvinceId, ProvinceFullStat> = emptyMap()
)
