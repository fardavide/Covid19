package studio.forface.covid.android.ui

import android.os.Bundle
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import studio.forface.covid.android.service.DownloadUpdateWorker
import studio.forface.covid.android.viewmodel.SearchViewModel
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.util.takeIfTrue
import studio.forface.viewstatestore.ViewState

/**
 * Abstract Activity for search, its meant to be implemented in
 * `studio.forface.covid.android.classic` and `studio.forface.covid.android.compose`
 *
 * @author Davide Farella
 */
abstract class AbsSearchActivity : BaseActivity() {

    private val searchViewModel by viewModel<SearchViewModel>()
    private val downloadUpdateWorker by inject<DownloadUpdateWorker.Enqueuer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchViewModel.countries.observe {
            doOnData(::onSearchResult)
            doOnError(::onSearchError)
            doOnLoadingChange(::onLoadingChange)
        }
    }

    override fun onStart() {
        super.onStart()
        downloadUpdateWorker.invoke(replace = true)
    }

    /** Start the search for Countries matching the given [countryName] */
    protected fun search(countryName: CharSequence) {
        searchViewModel.search.offer(countryName.toString())
            .takeIfTrue() ?: throw IllegalStateException("'search' Channel is busy, this should not happen")
    }

    /** Called when a List of [Country]s is available */
    protected abstract fun onSearchResult(result: List<Country>)

    /** Called when an Error is received for the search */
    protected abstract fun onSearchError(error: ViewState.Error)

    /** Called when Loading state is changed */
    protected abstract fun onLoadingChange(loading: Boolean)

    /** This must toggle the favorite state for the given Country */
    protected open fun onFavorite(country: Country) {
        searchViewModel.toggleFavorite(country)
    }

    /** Navigate to Country Stat for Country with given [id] */
    protected fun goToCountryStat(id: CountryId) {
        router.toCountryStat(id)
    }
}
