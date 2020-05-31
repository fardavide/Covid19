package studio.forface.covid.android.classic.ui

import android.content.Context
import kotlinx.android.synthetic.main.activity_country_stat.*
import studio.forface.covid.android.classic.R
import studio.forface.covid.android.classic.Router
import studio.forface.covid.android.classic.utils.showSnackbar
import studio.forface.covid.android.classic.utils.startActivity
import studio.forface.covid.android.model.CountryStatUiModel
import studio.forface.covid.android.ui.AbsCountryStatActivity
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.util.e
import studio.forface.viewstatestore.ViewState

class CountryStatActivity : AbsCountryStatActivity() {

    override val router = Router()

    override fun initUi() {
        setContentView(R.layout.activity_country_stat)
    }

    /** Called when [CountryStatUiModel] is available */
    override fun onStatsResult(result: CountryStatUiModel) {
        toolbar.title = result.countryName.s
        stat_view.setStat(result)
    }

    /** Called when an Error is received while getting Stats */
    override fun onStatsError(error: ViewState.Error) {
        kermit.e(error.throwable)
        showSnackbar(error)
    }

    /** Called when Loading state is changed */
    override fun onLoadingChange(loading: Boolean) {}

    companion object {

        /** Start [CountryStatActivity] using its class Intent */
        fun launch(context: Context, countryId: CountryId) {
            context.startActivity<CountryStatActivity>(ARG_COUNTRY_ID to countryId.s)
        }
    }
}
