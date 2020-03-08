package studio.forface.covid

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import studio.forface.covid.domain.usecase.GetWorldFullStat

suspend fun main() = coroutineScope {

    val koin = startKoin {
        modules(appModule)
    }.koin

    val getWorldFullStat = koin.get<GetWorldFullStat>()

    launch {
        getWorldFullStat().collect {
            println(it)
        }
    }

    Unit
}
