package studio.forface.covid.data.remote.mapper

import studio.forface.covid.data.remote.model.UpdateVersionApiModel
import studio.forface.covid.domain.entity.UpdateVersion
import studio.forface.covid.domain.invoke

internal class UpdateVersionApiModelMapper(
    private val timeMapper: UpdateVersionTimestampApiModelMapper
) : ApiModelMapper<UpdateVersionApiModel, UpdateVersion> {

    override fun UpdateVersionApiModel.toEntity() =
        UpdateVersion(code.toInt(), name, timeMapper { timestamp.toEntity() })
}