package studio.forface.covid.android.classic

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import studio.forface.covid.android.Router
import studio.forface.covid.android.classic.ui.CountryStatActivity
import studio.forface.covid.android.classic.ui.SearchActivity
import studio.forface.covid.domain.entity.CountryId

/**
 * This object will navigate though the pages of the App.
 * Implements [Router]
 *
 * @author Davide Farella
 */
class ClassicRouter(private val activity: FragmentActivity) : Router {

    override fun toHome() {
        toSearch()
        activity.finish()
    }

    override fun toSearch() {
        SearchActivity.launch(activity)
    }

    override fun toCountryStat(id: CountryId) {
        CountryStatActivity.launch(activity, id)
    }

    override fun countryStatIntent(id: CountryId) = CountryStatActivity.intent(activity, id)
}

fun FragmentActivity.Router() = ClassicRouter(this)
