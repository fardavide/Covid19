package studio.forface.covid

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import studio.forface.covid.domain.usecase.GetWorldFullStat
import studio.forface.covid.domain.usecase.GetWorldStat
import kotlin.time.seconds

suspend fun main() = coroutineScope {

    val koin = startKoin {
        modules(appModule)
    }.koin

    val getWorldFullStat = koin.get<GetWorldFullStat>()
    val getWorldStat = koin.get<GetWorldStat>()

    launch {
        getWorldStat(10.seconds, 10.seconds).collect {
            println("Stats")
            println(it)
        }
    }

    Unit
}
