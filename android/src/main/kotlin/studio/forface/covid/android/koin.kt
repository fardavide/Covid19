package studio.forface.covid.android

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import studio.forface.covid.android.viewmodel.SearchViewModel
import studio.forface.covid.data.dataModule
import studio.forface.covid.domain.domainModule
import studio.forface.covid.domain.util.DispatcherProvider

private val viewModelModule = module {
    viewModel { SearchViewModel(searchCountry = get(), dispatcherProvider = get()) }
}

val androidModule = module {

    single<DispatcherProvider> {
        object : DispatcherProvider {

            /** [CoroutineDispatcher] mean to run IO operations */
            override val Io = Dispatchers.IO

            /** [CoroutineDispatcher] mean to run computational operations */
            override val Comp = Dispatchers.Default

            /** [CoroutineDispatcher] mean to run on main thread */
            override val Main = Dispatchers.Main
        }
    }

} + viewModelModule + domainModule + dataModule
