package studio.forface.covid.android

import studio.forface.covid.domain.entity.CountryId

/**
 * This object will navigate though the pages of the App.
 * Its meant to be implemented in `studio.forface.covid.android.classic` and
 * `studio.forface.covid.android.compose` with their own navigation methods
 *
 * @author Davide Farella
 */
interface Router {

    /** Navigate to Home page */
    fun toHome()

    /** Navigate to Search page */
    fun toSearch()

    /** Navigate to CountryStat page for Country with given [id] */
    fun toCountryStat(id: CountryId)
}
