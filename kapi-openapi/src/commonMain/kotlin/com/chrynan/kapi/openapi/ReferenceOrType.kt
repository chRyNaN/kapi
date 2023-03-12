@file:Suppress("unused")

package com.chrynan.kapi.openapi

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * A wrapper class around either a [Reference] or a generic type of [T]. Often in the Open API Specification, a
 * property type can either be a [Reference] object or some other object. This class provides a simple way to serialize
 * and deserialize those types and represent those union types within Kotlin code.
 */
@Serializable(with = ReferenceOrTypeSerializer::class)
sealed class ReferenceOrType<T> private constructor() {

    abstract val value: Any?

    @Serializable
    data class ReferenceInstance<T> internal constructor(
        @SerialName(value = "value") override val value: Reference
    ) : ReferenceOrType<T>()

    @Serializable
    data class TypeInstance<T> internal constructor(
        @SerialName(value = "value") override val value: T
    ) : ReferenceOrType<T>()
}

/**
 * Creates a [ReferenceOrType.ReferenceInstance] with the provided [Reference] [value].
 */
@Suppress("FunctionName")
fun <T> ReferenceOrType(value: Reference): ReferenceOrType.ReferenceInstance<T> =
    ReferenceOrType.ReferenceInstance(value = value)

/**
 * Creates a [ReferenceOrType.TypeInstance] with the provided [T] [value].
 */
@Suppress("FunctionName")
fun <T> ReferenceOrType(value: T): ReferenceOrType.TypeInstance<T> =
    ReferenceOrType.TypeInstance(value = value)

/**
 * Obtains the reference value if this [ReferenceOrType] is a [ReferenceOrType.ReferenceInstance], otherwise throws an
 * exception.
 */
fun ReferenceOrType<*>.referenceValue(): Reference =
    (this as? ReferenceOrType.ReferenceInstance<*>)?.value
        ?: error("Cannot obtain reference value from a non-reference ReferenceOrType instance.")

/**
 * Obtains the type value if this [ReferenceOrType] is a [ReferenceOrType.TypeInstance], otherwise throws an exception.
 */
fun <T> ReferenceOrType<T>.typeValue(): T =
    (this as? ReferenceOrType.TypeInstance<T>)?.value
        ?: error("Cannot obtain type value from a non-type ReferenceOrType instance.")

/**
 * Obtains the reference value if this [ReferenceOrType] is a [ReferenceOrType.ReferenceInstance], otherwise returns
 * `null`.
 */
fun ReferenceOrType<*>.referenceValueOrNull(): Reference? =
    try {
        referenceValue()
    } catch (_: Exception) {
        null
    }

/**
 * Obtains the type value if this [ReferenceOrType] is a [ReferenceOrType.TypeInstance], otherwise returns `null`.
 */
fun <T> ReferenceOrType<T>.typeValueOrNull(): T? =
    try {
        typeValue()
    } catch (_: Exception) {
        null
    }

/**
 * Determines if this [ReferenceOrType] is a [ReferenceOrType.ReferenceInstance].
 */
@OptIn(ExperimentalContracts::class)
fun ReferenceOrType<*>.isReference(): Boolean {
    contract {
        returns(true) implies (this@isReference is ReferenceOrType.ReferenceInstance<*>)
    }

    return this is ReferenceOrType.ReferenceInstance<*>
}

/**
 * Determines if this [ReferenceOrType] is a [ReferenceOrType.TypeInstance].
 */
@OptIn(ExperimentalContracts::class)
fun <T> ReferenceOrType<T>.isType(): Boolean {
    contract {
        returns(true) implies (this@isType is ReferenceOrType.TypeInstance<T>)
    }

    return this is ReferenceOrType.TypeInstance<T>
}

/**
 * A [KSerializer] implementation for the [ReferenceOrType] class.
 */
@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
internal class ReferenceOrTypeSerializer<T>(
    private val serializer: KSerializer<T>
) : KSerializer<ReferenceOrType<T>> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("ReferenceOrTypeSerializer", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: ReferenceOrType<T>) =
        when (value) {
            is ReferenceOrType.ReferenceInstance -> encoder.encodeSerializableValue(
                serializer = Reference.serializer(),
                value = value.value
            )

            is ReferenceOrType.TypeInstance -> encoder.encodeSerializableValue(
                serializer = serializer,
                value = value.value
            )
        }

    override fun deserialize(decoder: Decoder): ReferenceOrType<T> {
        val input = (this as? JsonDecoder) ?: error("The ReferenceOrType KSerializer only works with JSON.")
        val json = input.decodeJsonElement()

        return if (json is JsonObject && json.contains(Reference.PROPERTY_NAME_REF)) {
            val reference = decoder.decodeSerializableValue(deserializer = Reference.serializer())

            ReferenceOrType.ReferenceInstance(reference)
        } else {
            val type = decoder.decodeSerializableValue(deserializer = serializer)

            ReferenceOrType.TypeInstance(type)
        }
    }
}
