package studio.forface.covid.data.local.mapper

import studio.forface.covid.domain.mapper.TwoWayMapper

/**
 * Interface for a Mapper that converts a Database model into an Entity and an Entity back to a Database Model
 * Implements [TwoWayMapper] Subtype of [ParamsDatabaseModelMapper]
 *
 * @author Davide Farella
 */
internal interface DatabaseModelMapper<Entity, DatabaseModel> :
    ParamsDatabaseModelMapper<Entity, DatabaseModel, Entity>

/**
 * Interface for a Mapper that converts some Entity Params ( Entity + additional info ) into a Database Model and
 * a Database Model back to an Entity
 * Implements [TwoWayMapper]
 *
 * @author Davide Farella
 */
internal interface ParamsDatabaseModelMapper<EntityParams, DatabaseModel, Entity> :
    TwoWayMapper<EntityParams, DatabaseModel, Entity> {

    fun DatabaseModel.toEntity(): Entity

    fun EntityParams.toDatabaseModel(): DatabaseModel
}
