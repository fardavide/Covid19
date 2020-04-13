package studio.forface.covid.android.ui

import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.covid.android.model.CountryStatsUiModel
import studio.forface.covid.android.viewmodel.CountryStatViewModel
import studio.forface.covid.domain.entity.CountryId
import studio.forface.viewstatestore.ViewState

/**
 * Abstract Activity for view Country stats, its meant to be implemented in
 * `studio.forface.covid.android.classic` and `studio.forface.covid.android.compose`
 *
 * @author Davide Farella
 */
abstract class AbsCountryStatActivity : BaseActivity() {

    private val countryStatViewModel by viewModel<CountryStatViewModel> {
        parametersOf(CountryId(intent.getStringExtra(ARG_COUNTRY_ID)!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        countryStatViewModel.stats.observe {
            doOnData(::onStatsResult)
            doOnError(::onStatsError)
            doOnLoadingChange(::onLoadingChange)
        }
    }

    /** Called when [CountryStatsUiModel] is available */
    protected abstract fun onStatsResult(result: CountryStatsUiModel)

    /** Called when an Error is received while getting Stats */
    protected abstract fun onStatsError(error: ViewState.Error)

    /** Called when Loading state is changed */
    protected abstract fun onLoadingChange(loading: Boolean)

    protected companion object {
        const val ARG_COUNTRY_ID = "Country_id"
    }
}
