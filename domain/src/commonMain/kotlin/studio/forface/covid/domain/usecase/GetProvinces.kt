package studio.forface.covid.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import studio.forface.covid.domain.DEFAULT_ERROR_INTERVAL
import studio.forface.covid.domain.DEFAULT_REFRESH_INTERVAL
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.Province
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.util.repeatCatching
import kotlin.time.Duration

/**
 * Get a list of all the available Provinces sorted by their [Country.name] for a given Country
 *
 * @param refreshInterval: optional interval for refresh the data from the remote source
 * @param errorInterval: optional interval for retry the data sync when failed
 *
 * * Input:
 *  * [CountryId] or [Country]
 *
 * * Output:
 *  * a [Flow] of list of [Province]
 *
 * @author Davide Farella
 */
class GetProvinces(
    private val repository: Repository,
    private val syncProvinces: SyncProvinces,
    private val refreshInterval: Duration = DEFAULT_REFRESH_INTERVAL,
    private val errorInterval: Duration = DEFAULT_ERROR_INTERVAL
) {

    operator fun invoke(id: CountryId) = channelFlow {

        // Sync every refresh interval
        launch {
            repeatCatching(refreshInterval, errorInterval) {
                syncProvinces(id)
            }
        }

        // Observe changes from repository
        repository.getProvinces(id).collect { send(it) }
    }

    operator fun invoke(country: Country) = this(country.id)
}
