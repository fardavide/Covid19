package studio.forface.covid.android.classic.ui

import studio.forface.covid.android.ui.AbsSearchActivity
import studio.forface.covid.domain.entity.Country
import studio.forface.viewstatestore.ViewState
import timber.log.Timber

class SearchActivity : AbsSearchActivity() {

    /** Called when a List of [Country]s is available */
    override fun onSearchResult(result: List<Country>) {
        Timber.i(result.joinToString())
    }

    /** Called when an Error is received for the search */
    override fun onSearchError(error: ViewState.Error) {
        Timber.i(error.getMessage(this).toString())
    }

    /** Called when Loading state is changed */
    override fun onLoadingChange(loading: Boolean) {

    }
}
