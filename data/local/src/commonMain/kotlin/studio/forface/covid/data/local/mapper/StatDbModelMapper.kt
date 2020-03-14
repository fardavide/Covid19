package studio.forface.covid.data.local.mapper

import studio.forface.covid.data.local.StatDbModel
import studio.forface.covid.domain.entity.Stat
import studio.forface.covid.domain.invoke

internal class StatDbModelMapper(
    private val unixTimeMapper: UnixTimeDbModelMapper
) : DatabaseModelMapper<StatDbModel, Stat> {

    override fun StatDbModel.toEntity() = Stat(
        confirmed = confirmed,
        deaths = deaths,
        recovered = recovered,
        timestamp = unixTimeMapper { timestamp.toEntity() }
    )
}
