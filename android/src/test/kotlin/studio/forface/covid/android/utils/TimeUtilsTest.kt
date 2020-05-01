package studio.forface.covid.android.utils

import android.content.Context
import com.soywiz.klock.DateTime
import com.soywiz.klock.Month
import io.mockk.every
import io.mockk.mockk
import studio.forface.covid.android.R
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * __Unit__ test suite for Time Utils
 * @author Davide Farella
 */
class TimeUtilsTest {

    private val TODAY = 10
    private val mockContext = mockk<Context> {
        every { getString(R.string.today_at_arg, any()) } answers {
            "Today at ${secondArg<Array<Any>>().first()}"
        }
        every { getString(R.string.yesterday_at_arg, any()) } answers {
            "Yesterday at ${secondArg<Array<Any>>().first()}"
        }
        every { getString(R.string.days_ago_arg, any()) } answers {
            "${secondArg<Array<Any>>().first()} days ago"
        }
        every { getString(R.string.midnight) } returns "midnight"
    }

    private val mockNow = DateTime(
        year = 2020,
        month = Month.May,
        day = TODAY,
        hour = 18
    )

//    @BeforeTest
//    fun mockCurrentTime() {
//        mockkObject(DateTime)
//        every { DateTime.now() } returns DateTime(
//            year = 2020,
//            month = Month.May,
//            day = TODAY,
//            hour = 18
//        )
//    }
//
//    @AfterTest
//    fun tearDown() {
//        unmockkObject(DateTime)
//    }

    @Test
    fun `DateTime formatRelative works correctly for today midnight`() {
        // GIVEN
        val ts = DateTime(
            year = 2020,
            month = Month.May,
            day = TODAY,
            hour = 0
        )

        // WHEN
        val s = ts.formatRelative(mockNow, mockContext)

        // THEN
        assertEquals("Today at midnight", s)
    }

    @Test
    fun `DateTime formatRelative works correctly for today morning`() {
        // GIVEN
        val ts = DateTime(
            year = 2020,
            month = Month.May,
            day = TODAY,
            hour = 10
        )

        // WHEN
        val s = ts.formatRelative(mockNow, mockContext)

        // THEN
        assertEquals("Today at 10", s)
    }

    @Test
    fun `DateTime formatRelative works correctly for yesterday afternoon`() {

        // GIVEN
        val ts = DateTime(
            year = 2020,
            month = Month.May,
            day = TODAY - 1,
            hour = 16
        )

        // WHEN
        val s = ts.formatRelative(mockNow, mockContext)

        // THEN
        assertEquals("Yesterday at 4 pm", s)
    }

    @Test
    fun `DateTime formatRelative works correctly for 3 days ago`() {

        // GIVEN
        val ts = DateTime(
            year = 2020,
            month = Month.May,
            day = TODAY - 3,
            hour = 20
        )

        // WHEN
        val s = ts.formatRelative(mockNow, mockContext)

        // THEN
        assertEquals("3 days ago", s)
    }
}
