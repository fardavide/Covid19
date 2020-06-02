package studio.forface.covid.android.mapper

import android.content.Context
import com.soywiz.klock.days
import studio.forface.covid.android.R
import studio.forface.covid.android.model.CountryStatUiModel
import studio.forface.covid.android.model.UiModelMapper
import studio.forface.covid.android.utils.formatRelative
import studio.forface.covid.domain.entity.CountryStat
import studio.forface.covid.domain.entity.rem

/**
 * Map [CountryStat] to [CountryStatUiModel]
 * Implements [UiModelMapper]
 *
 * @author Davide Farella
 */
class CountryStatUiModelMapper(
    private val context: Context
) : UiModelMapper<CountryStat, CountryStatUiModel> {

    override fun CountryStat.toUiModel(): CountryStatUiModel {
        val lastStat = stats.first()
        val diff = stats % 1.days

        return CountryStatUiModel(
            countryName = country.name,
            infectedCount = lastStat.confirmed,
            infectedDiff = diff.confirmed,
            deathsCount = lastStat.deaths,
            deathsDiff = diff.deaths,
            recoveredCount = lastStat.recovered,
            recoveredDiff = diff.recovered,
            lastUpdatedTime = context.getString(
                R.string.updated_arg,
                lastStat.timestamp.formatRelative(context)
            )
        )
    }
}
