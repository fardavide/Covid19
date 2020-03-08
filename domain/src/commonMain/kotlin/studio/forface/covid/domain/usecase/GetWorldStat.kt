package studio.forface.covid.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import studio.forface.covid.domain.DEFAULT_ERROR_INTERVAL
import studio.forface.covid.domain.DEFAULT_REFRESH_INTERVAL
import studio.forface.covid.domain.entity.WorldStat
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.util.repeatCatching
import kotlin.time.Duration

/**
 * Get World Stat
 *
 * @param refreshInterval: optional interval for refresh the data from the remote source
 * @param errorInterval: optional interval for retry the data sync when failed
 *
 * * Output:
 *  * a[Flow] of [WorldStat]
 *
 * @author Davide Farella
 */
class GetWorldStat(
    private val repository: Repository,
    private val syncWorldStat: SyncWorldStat,
    private val refreshInterval: Duration = DEFAULT_REFRESH_INTERVAL,
    private val errorInterval: Duration = DEFAULT_ERROR_INTERVAL
) {

    operator fun invoke() = channelFlow {

        // Sync every refresh interval
        launch {
            repeatCatching(refreshInterval, errorInterval) {
                syncWorldStat()
            }
        }

        // Observe changes from repository
        repository.getWorldStat().collect { send(it) }
    }
}
