package studio.forface.covid.android.model

import studio.forface.covid.domain.entity.Name

data class CountryStatsUiModel(
    val countryName: Name,
    val infectedCount: Int,
    val infectedDiff: Int,
    val deathsCount: Int,
    val deathsDiff: Int,
    val recoveredCount: Int,
    val recoveredDiff: Int,
    val lastUpdatedTime: String
//    val diffTimestamp: String
)
