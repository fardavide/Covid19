@file:OptIn(ExperimentalCoroutinesApi::class)

package studio.forface.covid.domain.usecase

import com.soywiz.klock.DateTime
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.CountrySmallStat
import studio.forface.covid.domain.entity.CountryStat
import studio.forface.covid.domain.entity.Name
import studio.forface.covid.domain.entity.Stat
import kotlin.test.*

/**
 * __Unit__ test suite [GetNewFavoriteCountriesStatsDiff]
 * @author Davide Farella
 */
class GetNewFavoriteCountriesStatsDiffTest {

    private fun getNewFavoriteCountriesStatsDiff(
        before: List<CountryStat>,
        after: List<CountryStat>
    ): GetNewFavoriteCountriesStatsDiff {
        var didSync = false
        return GetNewFavoriteCountriesStatsDiff(
            repository = mockk {
                every { getFavoriteCountriesStats() } answers {
                    if (!didSync) flowOf(before) else flowOf(after)
                }
            },
            syncFavoriteCountriesStats = mockk {
                coEvery { this@mockk() } answers { didSync = true }
            }
        )
    }

    @Test
    fun `GetNewFavoriteCountriesStatsDiff works correctly`() = runBlockingTest {
        val now = DateTime.now()

        // GIVEN
        val before = listOf(
            CountryStat(
                Country(CountryId("italy"), Name("Italy")),
                listOf(
                    Stat(120, 20, 30, now),
                    Stat(100, 10, 20, now)
                )
            ),
            CountryStat(
                Country(CountryId("china"), Name("China")),
                listOf(
                    Stat(200, 30, 20, now),
                    Stat(100, 10, 20, now)
                )
            ),
            CountryStat(
                Country(CountryId("burma"), Name("Burma")),
                listOf(
                    Stat(0, 0, 0, now)
                )
            )
        )
        val after = listOf(
            CountryStat(
                Country(CountryId("italy"), Name("Italy")),
                listOf(
                    Stat(125, 24, 32, now),
                    Stat(120, 20, 30, now),
                    Stat(100, 10, 20, now)
                )
            ),
            CountryStat(
                Country(CountryId("china"), Name("China")),
                listOf(
                    Stat(200, 30, 20, now),
                    Stat(200, 30, 20, now),
                    Stat(100, 10, 20, now)
                )
            ),
            CountryStat(
                Country(CountryId("burma"), Name("Burma")),
                listOf(
                    Stat(0, 0, 0, now)
                )
            )
        )

        // WHEN
        val result = getNewFavoriteCountriesStatsDiff(before, after).invoke()

        // THEN
        assertEquals(1, result.size)
        assertEquals(
            CountrySmallStat(
                Country(CountryId("italy"), Name("Italy")),
                Stat(5, 4, 2, now)
            ),
            result.first()
        )
    }

    @Test
    fun `GetNewFavoriteCountriesStatsDiff returns nothing if database was empty`() = runBlockingTest {
        val now = DateTime.now()

        // GIVEN
        val before = listOf<CountryStat>()
        val after = listOf(
            CountryStat(
                Country(CountryId("italy"), Name("Italy")),
                listOf(
                    Stat(125, 24, 32, now),
                    Stat(120, 20, 30, now),
                    Stat(100, 10, 20, now)
                )
            ),
            CountryStat(
                Country(CountryId("burma"), Name("Burma")),
                listOf(
                    Stat(0, 0, 0, now)
                )
            )
        )

        // WHEN
        val result = getNewFavoriteCountriesStatsDiff(before, after).invoke()

        // THEN
        assertEquals(0, result.size)
    }
}
