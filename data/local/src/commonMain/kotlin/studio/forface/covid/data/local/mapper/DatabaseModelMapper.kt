package studio.forface.covid.data.local.mapper

import studio.forface.covid.domain.mapper.OneWayMapper

/**
 * Interface for a Mapper that converts a Database model into an Entity
 * Implements [OneWayMapper]
 *
 * @author Davide Farella
 */
internal interface DatabaseModelMapper<DatabaseModel, Entity> : OneWayMapper<DatabaseModel, Entity> {

    fun DatabaseModel.toEntity(): Entity
}
