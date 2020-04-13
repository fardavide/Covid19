package studio.forface.covid.android.model

import studio.forface.covid.domain.entity.Name

data class CountryStatsUiModel(
    val countryName: Name,
    val infectedCount: Int,
    val deathsCount: Int,
    val recoveredCount: Int,
    val lastUpdatedTime: String
)
