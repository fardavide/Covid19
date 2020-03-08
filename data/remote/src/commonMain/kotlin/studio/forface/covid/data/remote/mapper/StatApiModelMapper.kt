package studio.forface.covid.data.remote.mapper

import studio.forface.covid.data.remote.model.StatApiModel
import studio.forface.covid.domain.entity.Stat
import studio.forface.covid.domain.invoke

internal class StatApiModelMapper(
    private val unixTimeMapper: UnixTimeApiModelMapper
) : ApiModelMapper<StatApiModel, Stat> {

    override fun StatApiModel.toEntity() = Stat(
        confirmed = confirmed,
        deaths = deaths,
        recovered = recovered,
        timestamp = unixTimeMapper { timestamp.toEntity() }
    )
}

internal class StatParamsMapper(
    private val timestampMapper: TimestampApiModelMapper
) : ApiModelMapper<StatParams, Stat> {

    override fun StatParams.toEntity() = Stat(
        confirmed = confirmed,
        deaths = deaths,
        recovered = recovered,
        timestamp = timestampMapper { timestamp.toEntity() }
    )
}

internal data class StatParams(
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int,
    val timestamp: String
)
