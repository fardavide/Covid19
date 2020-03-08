package studio.forface.covid.domain.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.forface.covid.domain.Invokable
import kotlin.jvm.JvmName

/**
 * Interface for a generic Mapper
 * Implements [Invokable]
 *
 * @author Davide Farella
 */
interface Mapper : Invokable

interface OneWayMapper<in In, out Out> : Mapper

/** [Collection.map] within [OneWayMapper]. [In] -> [Out] */
@JvmName("oneWayMapList")
fun <M : OneWayMapper<In, Out>, In, Out, C : Collection<In>> C.map(mapper: M, block: M.(In) -> Out) =
    map { mapper.block(it) }

/** [Collection.associate] within [OneWayMapper]. [In] -> [Out] */
@JvmName("oneWayAssociateMapList")
fun <M : OneWayMapper<In, Out>, In, Out, C : Collection<In>, P : Pair<K, Out>, K> C.associateMap(
    mapper: M,
    transform: M.(In) -> P
) = associate { mapper.transform(it) }

/** [Flow.map] within [OneWayMapper]. [In] -> [Out] */
@JvmName("oneWayMapFlow")
fun <M : OneWayMapper<In, Out>, In, Out, F : Flow<In>> F.map(mapper: M, block: M.(In) -> Out) =
    map { mapper.block(it) }

interface TwoWayMapper<in ModelIn, Entity, out ModelOut> : Mapper

/** [Collection.map] within [TwoWayMapper]. [ModelIn] -> [Entity] */
@JvmName("twoWayMapList")
fun <M : TwoWayMapper<ModelIn, Entity, ModelOut>, ModelIn, Entity, ModelOut, C : Collection<ModelIn>> C.map(
    mapper: M,
    block: M.(ModelIn) -> Entity
) = map { mapper.block(it) }

/** [Collection.map] within [TwoWayMapper]. [Entity] -> [ModelOut] */
@JvmName("twoWayMapList2")
fun <M : TwoWayMapper<ModelIn, Entity, ModelOut>, ModelIn, Entity, ModelOut, C : Collection<Entity>> C.map(
    mapper: M,
    block: M.(Entity) -> ModelOut
) = map { mapper.block(it) }

/** [Collection.associate] within [TwoWayMapper]. [MI] -> [Map] of [K], [Entity] */
@JvmName("twoWayAssociateMapList")
fun <M : TwoWayMapper<MI, Entity, MO>, MI, Entity, MO, C : Collection<MI>, P : Pair<K, Entity>, K> C.associateMap(
    mapper: M,
    transform: M.(MI) -> P
) = associate { mapper.transform(it) }

/** [Collection.associate] within [TwoWayMapper]. [Entity] -> [Map] of [K], [MO] */
@JvmName("twoWayAssociateMapList2")
fun <M : TwoWayMapper<MI, Entity, MO>, MI, Entity, MO, C : Collection<Entity>, P : Pair<K, MO>, K> C.associateMap(
    mapper: M,
    transform: M.(Entity) -> P
) = associate { mapper.transform(it) }

/** [Flow.map] within [TwoWayMapper]. [ModelIn] -> [Entity] */
@JvmName("twoWayMapFlow")
fun <M : TwoWayMapper<ModelIn, Entity, ModelOut>, ModelIn, Entity, ModelOut, F : Flow<ModelIn>> F.map(
    mapper: M,
    block: M.(ModelIn) -> ModelOut
) = map { mapper.block(it) }

/** [Flow.map] within [TwoWayMapper]. [Entity] -> [ModelOut] */
@JvmName("twoWayMapFlow2")
fun <M : TwoWayMapper<ModelIn, Entity, ModelOut>, ModelIn, Entity, ModelOut, F : Flow<Entity>> F.map(
    mapper: M,
    block: M.(Entity) -> ModelOut
) = map { mapper.block(it) }
