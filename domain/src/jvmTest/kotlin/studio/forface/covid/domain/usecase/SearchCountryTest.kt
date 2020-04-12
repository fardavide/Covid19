package studio.forface.covid.domain.usecase

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.Name
import studio.forface.covid.test.CoroutinesTest
import studio.forface.covid.test.coroutinesTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * __Unit__ test class for [SearchCountry]
 * @author Davide Farella
 */
internal class SearchCountryTest : CoroutinesTest by coroutinesTest {

    private val dbList = listOf("italy", "china", "myanmar", "france", "germany", "mauritania")

    @Test
    fun `SearchCountry works correctly`() = runBlockingTest {

        // GIVEN
        val db = dbList.map { Country(CountryId(it), Name(it)) }
        val searchCountry = SearchCountry(
            repository = mockk {
                every { getCountries(Name(any())) } answers {
                    flowOf(db.filter { it.name.s.contains(firstArg<String>(), ignoreCase = true) })
                }
            },
            syncCountries = mockk(relaxed = true)
        )
        val results = mutableListOf<List<Country>>()
        val query = Channel<Name>()
        val search = searchCountry.invoke(query)
        val j = launch {
            search.collect { results += it }
        }

        // WHEN
        query.send(Name("ita"))
        query.send(Name("fra"))

        // THEN
        assertNames(listOf("italy", "mauritania"), results[0])
        assertNames(listOf("france"), results[1])

        query.close()
        j.cancel()
    }

    @Test
    fun `results are correct if db is updated after search is called`() = runBlockingTest {

        // GIVEN
        val db = Channel<List<String>>()
        val searchCountry = SearchCountry(
            repository = mockk {
                every { getCountries(Name(any())) } answers {
                    db.consumeAsFlow().map {
                        it.map { Country(CountryId(it), Name(it)) }
                            .filter { it.name.s.contains(firstArg<String>(), ignoreCase = true) }
                    }
                }
            },
            syncCountries = mockk(relaxed = true)
        )
        val results = mutableListOf<List<Country>>()
        val query = Channel<Name>()
        val search = searchCountry.invoke(query)
        val j = launch {
            search.collect { results += it }
        }

        // WHEN
        query.send(Name("ita"))
        db.send(dbList)

        // THEN
        assertNames(listOf("italy", "mauritania"), results[0])

        query.close()
        j.cancel()
    }

    private fun assertNames(expectedNames: List<String>, actualCountries: List<Country>) {
        assertEquals(expectedNames, actualCountries.map { it.name.s })
    }
}
