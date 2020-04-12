package studio.forface.covid.domain.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Provides [CoroutineDispatcher]s
 *
 * @author Davide Farella
 */
@Suppress("PropertyName", "VariableNaming")
interface DispatcherProvider {

    /** [CoroutineDispatcher] mean to run IO operations */
    val Io: CoroutineDispatcher

    /** [CoroutineDispatcher] mean to run computational operations */
    val Comp: CoroutineDispatcher

    /** [CoroutineDispatcher] mean to run on main thread */
    val Main: CoroutineDispatcher
}
