package studio.forface.covid.android

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import studio.forface.covid.android.mapper.CountryStatUiModelMapper
import studio.forface.covid.android.viewmodel.CountryStatViewModel
import studio.forface.covid.android.viewmodel.SearchViewModel
import studio.forface.covid.data.dataModule
import studio.forface.covid.domain.UpdatesDirectoryQualifier
import studio.forface.covid.domain.domainModule
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.util.DispatcherProvider

private val mapperModule = module {
    factory { CountryStatUiModelMapper() }
}

private val viewModelModule = module {
    viewModel { (countryId: CountryId) ->
        CountryStatViewModel(
            countryId,
            getCountryStat = get(),
            mapper = get(),
            dispatcherProvider = get()
        )
    }
    viewModel { SearchViewModel(searchCountry = get(), dispatcherProvider = get()) }
} + domainModule + mapperModule

val androidModule = module {

    factory(UpdatesDirectoryQualifier) {
        get<Context>().cacheDir
    }

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

} + viewModelModule + dataModule
