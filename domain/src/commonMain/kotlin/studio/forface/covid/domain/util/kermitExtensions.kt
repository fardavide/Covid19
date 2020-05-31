package studio.forface.covid.domain.util

import co.touchlab.kermit.Kermit

fun Kermit.e(throwable: Throwable) = e(throwable) { throwable.message ?: "" }
