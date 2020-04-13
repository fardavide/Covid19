package studio.forface.covid.android.classic

import android.content.Context
import androidx.core.app.ComponentActivity
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
class ClassicRouter(private val context: Context) : Router {

    /** Navigate to Home page */
    override fun toHome() {
        toSearch()
        if (context is ComponentActivity) context.finish()
    }

    /** Navigate to Search page */
    override fun toSearch() {
        SearchActivity.launch(context)
    }

    /** Navigate to CountryStat page for Country with given [id] */
    override fun toCountryStat(id: CountryId) {
        CountryStatActivity.launch(context, id)
    }
}
