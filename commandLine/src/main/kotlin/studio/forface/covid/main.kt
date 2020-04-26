@file:Suppress("FunctionName")

package studio.forface.covid

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.usecase.GetCountries
import studio.forface.covid.domain.usecase.GetCountryFullStat
import studio.forface.covid.domain.usecase.GetWorldFullStat
import kotlin.time.seconds

class Covid(scope: CoroutineScope) : CliktCommand(), KoinComponent, CoroutineScope by (scope + IO) {
    private val refresh by option(help = "Refresh every seconds").int().default(60)
    private val countries by option(help = "Get list of available Countries").flag()
    private val country by option(help = "Get stats for Country").convert { CountryId(it) }
    private val province by option(help = "Get stats for Province")

    override fun run() {
        when {
            countries -> `countries list`()
            country != null -> `full stat for Country`(country!!)
            else -> `full Stats for World`()
        }
    }

    private fun `countries list`() {
        val getCountries = get<GetCountries>()

        launch {
            getCountries(refreshInterval = refresh.seconds).collect {
                println("Available countries")
                println(it.joinToString(separator = "\n") { country -> "* ${country.name.s} ( ${country.id.s} )" })
            }
        }
    }

    private fun `full stat for Country`(id: CountryId) {
        val getCountryFullStat = get<GetCountryFullStat>()

        launch {
            getCountryFullStat(id, refreshInterval = refresh.seconds).collect {
                println("Stat for Country with id '${id.s}'")
                println(it)
            }
        }
    }

    private fun `full Stats for World`() {
        val getWorldFullStat = get<GetWorldFullStat>()

        launch {
            getWorldFullStat(refreshInterval = refresh.seconds).collect {
                println("World Stats")
                println(it)
            }
        }
    }
}

suspend fun main(args: Array<String>) = coroutineScope {
    startKoin {
        modules(appModule)
    }

    Covid(this).main(args)
}
