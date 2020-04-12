package studio.forface.covid.android.classic.ui

import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import studio.forface.covid.android.classic.R
import studio.forface.covid.android.error.NoQueryError
import studio.forface.covid.android.ui.AbsSearchActivity
import studio.forface.covid.domain.entity.Country
import studio.forface.viewstatestore.ViewState
import timber.log.Timber

class SearchActivity : AbsSearchActivity() {

    private val countriesAdapter = CountriesAdapter()

    override fun initUi() {
        setContentView(R.layout.activity_search)
        countries_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = countriesAdapter
        }
        search_edit_text.doOnTextChanged { text, _, _, _ -> search(text!!) }
    }

    /** Called when a List of [Country]s is available */
    override fun onSearchResult(result: List<Country>) {
        countriesAdapter.submitList(result)
    }

    /** Called when an Error is received for the search */
    override fun onSearchError(error: ViewState.Error) {
        if (error is NoQueryError) countriesAdapter.submitList(emptyList())
        Timber.i(error.getMessage(this).toString())
    }

    /** Called when Loading state is changed */
    override fun onLoadingChange(loading: Boolean) {

    }
}
