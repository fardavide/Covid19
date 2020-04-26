package studio.forface.covid.android.mapper

import com.soywiz.klock.days
import studio.forface.covid.android.model.CountryStatsUiModel
import studio.forface.covid.android.model.UiModelMapper
import studio.forface.covid.domain.entity.CountryStat
import studio.forface.covid.domain.entity.diff
import studio.forface.covid.domain.entity.rem
import studio.forface.covid.domain.util.formatDate

/**
 * Map [CountryStat] to [CountryStatsUiModel]
 * Implements [UiModelMapper]
 *
 * @author Davide Farella
 */
class CountryStatUiModelMapper : UiModelMapper<CountryStat, CountryStatsUiModel> {

    override fun CountryStat.toUiModel(): CountryStatsUiModel {
        val lastStat = stats.first()
        val diff = stats % 1.days
        var temp = lastStat
        val lastUpdateTime = stats
            .takeWhile { stat ->
                (stat equalsNoTime temp).also { temp = stat }
            }.last().timestamp

        return CountryStatsUiModel(
            countryName = country.name,
            infectedCount = lastStat.confirmed,
            infectedDiff = diff.confirmed,
            deathsCount = lastStat.deaths,
            deathsDiff = diff.deaths,
            recoveredCount = lastStat.recovered,
            recoveredDiff = diff.recovered,
            lastUpdatedTime = lastUpdateTime.formatDate()
        )
    }
}
