package studio.forface.covid.domain.util

import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withTimeoutOrNull
import studio.forface.covid.test.CoroutinesTest
import studio.forface.covid.test.assertBetween
import studio.forface.covid.test.coroutinesTest
import kotlin.time.milliseconds
import kotlin.test.Test

internal class CommonUtilsTest : CoroutinesTest by coroutinesTest {

    @Test
    fun `repeatCatching runs correctly`() = runBlockingTest {

        // GIVEN
        val totalRunTime = 50
        val refreshInterval = 1
        val errorInterval = 5
        val errorEvery = 3

        var count = 0
        val block = { i: Int ->
            count = i
            // Throw exception every 3 iterations for verify error interval
            if (i % errorEvery == 0) throw IllegalStateException()
        }

        // WHEN
        withTimeoutOrNull(totalRunTime.toLong()) {
            repeatCatching(refreshInterval.milliseconds, errorInterval.milliseconds, block)
        }

        // THEN
        // If all success it would be almost 50, if all exceptions it would be less than 17
        assertBetween(20 .. 25, count)
    }
}
