package studio.forface.covid.domain.entity

data class World(
    val id: WorldId,
    val name: Name,
    val countries: List<Country>
)

/**
 * Small stat for a [World].
 * It includes the [World], a List of [Stat] and a List of [CountrySmallStat]
 *
 * This is analogue to [CountryStat]
 */
data class WorldStat(
    val world: World,
    val stats: List<Stat>,
    val countryStats: Map<CountryId, CountrySmallStat>
)

/**
 * Full stat for a [World].
 * It has the same structure of [WorldStat], but the list [countryStats] represents a List of [CountryFullStat]
 * instead of a List of [CountrySmallStat]
 *
 * This is analogue to [CountryFullStat]
 */
data class WorldFullStat(
    val world: World,
    val stats: List<Stat>,
    val countryStats: Map<CountryId, CountryStat>
)
