package studio.forface.covid.data.remote.mapper

import studio.forface.covid.domain.mapper.OneWayMapper

/**
 * Interface for a Mapper that converts an Api model into an Entity
 * Implements [OneWayMapper]
 *
 * @author Davide Farella
 */
interface ApiModelMapper<in ApiModel, out Entity> : OneWayMapper<ApiModel, Entity> {

    fun ApiModel.toEntity(): Entity
}
