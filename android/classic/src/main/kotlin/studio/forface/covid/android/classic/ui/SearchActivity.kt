package studio.forface.covid.android.classic.ui

import android.content.Context
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import studio.forface.covid.android.classic.R
import studio.forface.covid.android.classic.Router
import studio.forface.covid.android.classic.utils.showSnackbar
import studio.forface.covid.android.classic.utils.startActivity
import studio.forface.covid.android.error.NoQueryError
import studio.forface.covid.android.error.NoResultError
import studio.forface.covid.android.ui.AbsSearchActivity
import studio.forface.covid.domain.entity.Country
import studio.forface.viewstatestore.ViewState

class SearchActivity : AbsSearchActivity() {

    override val router = Router()

    private val countriesAdapter =
        CountriesAdapter(onClick = router::toCountryStat, onFavorite = ::onFavorite)

    override fun initUi() {
        setContentView(R.layout.activity_search)
        countries_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = countriesAdapter
        }
        search_edit_text.doOnTextChanged { text, _, _, _ ->
            app_bar_layout.setExpanded(true)
            search(text!!)
        }
    }

    /** Called when a List of [Country]s is available */
    override fun onSearchResult(result: List<Country>) {
        countriesAdapter.submitList(result)
        search_text_layout.error = null
    }

    /** Called when an Error is received for the search */
    override fun onSearchError(error: ViewState.Error) {
        when (error) {
            is NoQueryError -> countriesAdapter.submitList(emptyList())
            is NoResultError -> search_text_layout.error = error.getMessage(this)
        }
    }

    /** Called when Loading state is changed */
    override fun onLoadingChange(loading: Boolean) {}

    /** This must toggle the favorite state for the given Country */
    override fun onFavorite(country: Country) {
        showSnackbar(R.string.error_feature_not_available)
    }

    companion object {

        /** Start [SearchActivity] using its class Intent */
        fun launch(context: Context) {
            context.startActivity<SearchActivity>()
        }
    }
}
