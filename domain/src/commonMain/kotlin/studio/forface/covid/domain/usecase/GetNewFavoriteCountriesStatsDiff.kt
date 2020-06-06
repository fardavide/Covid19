package studio.forface.covid.domain.usecase

import kotlinx.coroutines.flow.first
import studio.forface.covid.domain.entity.CountrySmallStat
import studio.forface.covid.domain.entity.isEmpty
import studio.forface.covid.domain.entity.minus
import studio.forface.covid.domain.gateway.Repository

/**
 * Get a List of Diff for new available Country Stat for Favorite Countries
 *
 * * Output:
 *  * [List] of [CountrySmallStat]
 *
 * @author Davide Farella
 */
class GetNewFavoriteCountriesStatsDiff(
    private val repository: Repository,
    private val syncFavoriteCountriesStats: SyncFavoriteCountriesStats
) {

    suspend operator fun invoke(): List<CountrySmallStat> {
        // Get Favorites stats
        val previousStats = repository.getFavoriteCountriesStats().first()
            .map { it.country.id to it }.toMap()
        // Sync Favorites stats
        syncFavoriteCountriesStats()

        // Make diff between new stats and old ones
        return repository.getFavoriteCountriesStats().first()
            .mapNotNull { new ->
                // Compare with old value
                previousStats[new.country.id]
                        // make diff
                    ?.let { old -> new - old }
                        // Skip if diff is empty
                    ?.takeIf { !it.stat.isEmpty() }
            }
    }
}
