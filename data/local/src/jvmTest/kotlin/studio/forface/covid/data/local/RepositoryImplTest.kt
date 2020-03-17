package studio.forface.covid.data.local

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.BeforeClass
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.Location
import studio.forface.covid.domain.entity.Name
import studio.forface.covid.domain.entity.Province
import studio.forface.covid.domain.entity.ProvinceId
import studio.forface.covid.domain.entity.World
import studio.forface.covid.test.CoroutinesTest
import studio.forface.covid.test.coroutinesTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Test suite for [RepositoryImpl]
 */
internal class RepositoryImplTest : CoroutinesTest by coroutinesTest {

    @BeforeClass
    fun beforeTest() {
        loadKoinModules(localDataModule)
    }

    private val koin by lazy { GlobalContext.get() }
    private fun repository() = koin.get<RepositoryImpl>()

    @Test
    fun `verify store and get Countries`() = runBlockingTest {
        // GIVEN
        with(repository()) {

            // WHEN
            storeCountries(WORLD.countries)
            val output = getCountries().first()

            // THEN
            assertEquals(WORLD.countries, output)
        }
    }

    @Test
    fun `verify store and get WorldStat`() = runBlockingTest {
        with(repository()) {
            // GIVEN

        }
    }

    private companion object Mocks {

        // Provinces
        val LOMBARDIA = Province(ProvinceId("lombardia"), Name("Lombardia"), Location(12.14, 13.1433))
        val MOLISE = Province(ProvinceId("molise"), Name("Molise"), Location(14.0, 15.0))
        val LAZIO = Province(ProvinceId("lazio"), Name("Lazio"), Location(16.0, 19.0))
        val CAMPANIA = Province(ProvinceId("campania"), Name("Campania"), Location(0.0, 0.5345))

        // Countries
        val ITALY = Country(CountryId("italy"), Name("Italy"), provinces = listOf(LOMBARDIA, MOLISE, LAZIO, CAMPANIA))
        val MYANMAR = Country(CountryId("myanmar"), Name("Myanmar"))
        val THAILAND = Country(CountryId("thailand"), Name("Thailand"))
        val CHINA = Country(CountryId("china"), Name("China"))

        // World
        val WORLD = World(WorldId, Name("world"), countries = listOf(ITALY, MYANMAR, THAILAND, CHINA))
    }
}
