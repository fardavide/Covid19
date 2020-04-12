package studio.forface.covid.domain.usecase

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import studio.forface.covid.domain.DEFAULT_ERROR_INTERVAL
import studio.forface.covid.domain.DEFAULT_REFRESH_INTERVAL
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.Name
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.util.repeatCatching
import kotlin.time.Duration

/**
 * Get a list of all the available countries with [Country.name] matching the given query, sorted
 * by their [Country.name]
 *
 * @param refreshInterval: optional interval for refresh the data from the remote source
 * @param errorInterval: optional interval for retry the data sync when failed
 *
 * * Input:
 *  * [ReceiveChannel] of query [Name]
 *
 * * Output:
 *  * [Flow] of list of [Country]
 *
 * @author Davide Farella
 */
class SearchCountry(
    private val repository: Repository,
    private val syncCountries: SyncCountries,
    private val refreshInterval: Duration = DEFAULT_REFRESH_INTERVAL,
    private val errorInterval: Duration = DEFAULT_ERROR_INTERVAL
) {

    operator fun invoke(
        query: ReceiveChannel<Name>,
        refreshInterval: Duration = this.refreshInterval,
        errorInterval: Duration = this.errorInterval
    ) = channelFlow {

        // Sync every refresh interval
        launch {
            repeatCatching(refreshInterval, errorInterval) {
                syncCountries()
            }
        }

        // Observe changes from repository
        query.receiveAsFlow().flatMapLatest {
            repository.getCountries(it)
        }.collect { send(it) }
    }
}
