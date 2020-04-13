package studio.forface.covid.android.classic

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

    /** Navigate to Home page */
    override fun toHome() {
        toSearch()
        activity.finish()
    }

    /** Navigate to Search page */
    override fun toSearch() {
        SearchActivity.launch(activity)
    }

    /** Navigate to CountryStat page for Country with given [id] */
    override fun toCountryStat(id: CountryId) {
        CountryStatActivity.launch(activity, id)
    }
}

fun FragmentActivity.Router() = ClassicRouter(this)
