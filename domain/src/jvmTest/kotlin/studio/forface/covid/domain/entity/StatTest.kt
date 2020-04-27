package studio.forface.covid.domain.entity

import com.soywiz.klock.DateTime
import com.soywiz.klock.hours
import kotlinx.coroutines.test.runBlockingTest
import studio.forface.covid.test.CoroutinesTest
import studio.forface.covid.test.coroutinesTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * __Unit__ test suite for [Stat]
 * @author Davide Farella
 */
internal class StatTest : CoroutinesTest by coroutinesTest {

    @Test
    fun `diff works correctly with updated stats`() = runBlockingTest {

        // GIVEN
        val now = DateTime.now()
        val stats = listOf(
            Stat(99, 44, 65, now),
            Stat(75, 40, 63, now - 3.hours),
            Stat(72, 39, 53, now - 6.hours),
            Stat(60, 22, 51, now - 9.hours),
            Stat(54, 14, 20, now - 12.hours),
            Stat(41, 10, 16, now - 15.hours)
        )

        // WHEN
        val diff1 = stats % 4.hours
        val diff2 = stats % 11.hours

        // Then
        assertEquals(stats[0] - stats[1], diff1)
        assertEquals(stats[0] - stats[4], diff2)
    }

    @Test
    fun `diff works correctly with recent stats`() = runBlockingTest {

        // GIVEN
        val now = DateTime.now()
        val stats = listOf(
            Stat(75, 40, 63, now - 6.hours),
            Stat(72, 39, 53, now - 9.hours),
            Stat(60, 22, 51, now - 12.hours),
            Stat(54, 14, 20, now - 15.hours)
        )

        // WHEN
        val diff1 = stats % 4.hours
        val diff2 = stats % 11.hours

        // Then
        assertEquals(stats[0] - stats[1], diff1)
        assertEquals(stats[0] - stats[4], diff2)
    }

    @Test
    fun `diff works correctly with outdated stats`() = runBlockingTest {

        // GIVEN
        val now = DateTime.now()
        val stats = listOf(
            Stat(75, 40, 63, now - 12.hours),
            Stat(72, 39, 53, now - 15.hours)
        )

        // WHEN
        val diff1 = stats % 4.hours
        val diff2 = stats % 11.hours

        // Then
        assertEquals(stats[0] - stats[1], diff1)
        assertEquals(stats[0] - stats[1], diff2)
    }
}
