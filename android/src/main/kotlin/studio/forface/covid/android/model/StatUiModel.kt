package studio.forface.covid.android.model

import studio.forface.covid.domain.entity.Name

interface StatUiModel {
    val infectedCount: Int
    val infectedDiff: Int
    val deathsCount: Int
    val deathsDiff: Int
    val recoveredCount: Int
    val recoveredDiff: Int
    val lastUpdatedTime: CharSequence
}

data class CountryStatUiModel(
    val countryName: Name,
    override val infectedCount: Int,
    override val infectedDiff: Int,
    override val deathsCount: Int,
    override val deathsDiff: Int,
    override val recoveredCount: Int,
    override val recoveredDiff: Int,
    override val lastUpdatedTime: CharSequence
//    val diffTimestamp: String
) : StatUiModel
