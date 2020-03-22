package studio.forface.covid.data.local.utils

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import com.squareup.sqldelight.runtime.coroutines.mapToOneNotNull
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrDefault
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.dropWhile
import kotlin.coroutines.CoroutineContext

/** @return [Flow] of [T] from receiver [Query] of [T] */
internal fun <T : Any> Query<T>.asOneFlow(
    context: CoroutineContext = Dispatchers.Default
) = asFlow().mapToOne(context)

/** @return [Flow] of NOT NULL [T] - with [defaultValue] if `null` - from receiver [Query] of [T] */
internal fun <T : Any> Query<T>.asOneOrDefaultFlow(
    defaultValue: T,
    context: CoroutineContext = Dispatchers.Default
) = asFlow().mapToOneOrDefault(defaultValue, context)

/** @return [Flow] of NULLABLE [T] from receiver [Query] of [T] */
internal fun <T : Any> Query<T>.asOneOrNullFlow(
    context: CoroutineContext = Dispatchers.Default
) = asFlow().mapToOneOrNull(context)

/** @return [Flow] of NOT NULL [T] from receiver [Query] of [T] */
internal fun <T : Any> Query<T>.asOneNotNullFlow(
    context: CoroutineContext = Dispatchers.Default
) = asFlow().mapToOneNotNull(context)

/** @return [Flow] of [List] of [T] from receiver [Query] of [T] */
internal fun <T : Any> Query<T>.asListFlow(
    context: CoroutineContext = Dispatchers.Default
) = asFlow().mapToList(context).dropWhile { it.isEmpty() }
