package studio.forface.covid.domain.util

import kotlinx.coroutines.delay
import kotlin.time.Duration

suspend fun delay(duration: Duration) = delay(duration.toLongMilliseconds())
