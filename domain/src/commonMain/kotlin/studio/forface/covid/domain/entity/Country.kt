package studio.forface.covid.domain.entity

import com.soywiz.klock.DateTime

data class Country(
    val id: Id,
    val name: Name,
    val provinces: List<Province> = emptyList()
)

/**
 * Small stat for a [Country].
 * It includes the [Country] and the last [Stat] with relative timestamp.
 *
 * This is analogue to [ProvinceStat]
 */
data class CountrySmallStat(
    val country: Country,
    val stat: Stat,
    val lastUpdate: DateTime
)

/**
 * Medium stat for a [Country].
 * It includes the [Country], the last [Stat] with relative timestamp, a List of other [Stat] and a List of
 * [ProvinceStat], if any
 */
data class CountryStat(
    val country: Country,
    val lastStat: Stat,
    val lastUpdate: DateTime,
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
    val lastUpdate: DateTime,
    val otherStats: List<Stat>,
    val provinceStats: Map<ProvinceId, ProvinceFullStat> = emptyMap()
)
