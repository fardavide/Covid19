package studio.forface.covid.domain.util

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.list
import kotlinx.serialization.map
import kotlinx.serialization.parse
import kotlinx.serialization.parseList
import kotlinx.serialization.parseMap
import kotlinx.serialization.serializer

/*
 * Utilities for Serialization
 * Those utils uses kotlin-serialization
 *
 * Author: Davide Farella
 */

/** Default [StringFormat] */
val DefaultStringFormat: StringFormat = Json

/**
 * @return [T] object from the receiver [String]
 *
 * Note: this function uses reflection if no [deserializer] [DeserializationStrategy] is passed explicitly
 */
@UseExperimental(ImplicitReflectionSerializer::class)
inline fun <reified T : Any> String.deserialize(
    deserializer: DeserializationStrategy<T>? = null
): T = Serializer.parse(deserializer ?: T::class.serializer(), this)

/**
 * @return [List] of [T] object from the receiver [String]
 * This uses reflection: TODO improve for avoid it
 */
@UseExperimental(ImplicitReflectionSerializer::class)
inline fun <reified T : Any> String.deserializeList(): List<T> = Serializer.parseList(this)

/**
 * @return [Map] of [T], [V] object from the receiver [String]
 * This uses reflection: TODO improve for avoid it
 */
@UseExperimental(ImplicitReflectionSerializer::class)
inline fun <reified T : Any, reified V : Any> String.deserializeMap(): Map<T, V> =
    Serializer.parseMap(this)


/**
 * @return [String] from the receiver [T] object
 *
 * Note: this function uses reflection if no [serializer] [SerializationStrategy] is passed explicitly
 */
@UseExperimental(ImplicitReflectionSerializer::class)
inline fun <reified T : Any> T.serialize(
    serializer: SerializationStrategy<T>? = null
) = Serializer.stringify(serializer ?: T::class.serializer(), this)

/**
 * @return [String] from the receiver [List] of [T] object
 *
 * Note: this function uses reflection if no [serializer] [SerializationStrategy] is passed explicitly
 */
@UseExperimental(ImplicitReflectionSerializer::class)
inline fun <reified T : Any> List<T>.serialize(
    serializer: SerializationStrategy<List<T>>? = null
) = Serializer.stringify(serializer ?: T::class.serializer().list, this)

/**
 * @return [String] from the receiver [Map] of [T] and [V] object
 *
 * Note: this function uses reflection if no [serializer] [SerializationStrategy] is passed explicitly
 */
// TODO
//@UseExperimental(ImplicitReflectionSerializer::class)
//inline fun <reified T : Any, reified V : Any> Map<T, V>.serialize(
//    serializer: MapSerializer<T, V>? = null
//) = Serializer.stringify(serializer ?: (T::class.serializer() to V::class.serializer()).map, this)


@PublishedApi
internal val Serializer get() = DefaultStringFormat
