package studio.forface.covid.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import studio.forface.covid.domain.DEFAULT_ERROR_INTERVAL
import studio.forface.covid.domain.DEFAULT_REFRESH_INTERVAL
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.gateway.Api
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.util.delay
import studio.forface.covid.domain.util.repeatCatching
import kotlin.time.Duration

/**
 * Get a list of all the available countries sorted by their [Country.name]
 *
 * @param refreshInterval: optional interval for refresh the data from the remote source
 * @param errorInterval: optional interval for retry the data sync when failed
 *
 * * Output:
 *  * a[Flow] of list of [Country]
 *
 * @author Davide Farella
 */
class GetCountries(
    private val repository: Repository,
    private val syncCountries: SyncCountries,
    private val refreshInterval: Duration = DEFAULT_REFRESH_INTERVAL,
    private val errorInterval: Duration = DEFAULT_ERROR_INTERVAL
) {

    operator fun invoke() = channelFlow {

        // Sync each interval and publish error
        launch {
            repeatCatching(refreshInterval, errorInterval) {
                syncCountries()
            }
        }

        // Observe changes from repository
        repository.getCountries().collect { send(it) }
    }
}
