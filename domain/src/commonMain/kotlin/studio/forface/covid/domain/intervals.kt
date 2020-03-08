package studio.forface.covid.domain

import kotlin.time.minutes
import kotlin.time.seconds

/**
 * Default interval for refreshing data
 * 5 minutes
 */
internal val DEFAULT_REFRESH_INTERVAL = 5.minutes

/**
 * Default interval for retry after an error
 * 1 second
 */
internal val DEFAULT_ERROR_INTERVAL = 1.seconds
