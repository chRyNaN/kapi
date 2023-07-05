package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer

/**
 * Represents a GraphQL Value that can be represented as a [JsonElement], which can be a value passed as an argument to
 * an operation, or a default value assigned to an argument in a schema.
 */
@Serializable
@SerialName(value = "JsonValue")
sealed interface JsonValue : Value {

    /**
     * The [JsonElement] representation of this [Value].
     */
    val element: JsonElement
}

/**
 * Retrieves the value of type [T] for this [JsonValue], using the provided [json] value to convert this value's
 * [JsonValue.element] into a value of type [T]. This function implementation calls the [Json.decodeFromJsonElement]
 * function.
 *
 * The type of [JsonValue.element] is not known when this [JsonValue] class is instantiated. So it is up to the
 * call-site to know the type of [JsonValue.element] according to the associated GraphQL Schema.
 *
 * @see [Json.decodeFromJsonElement]
 */
inline fun <reified T> JsonValue.value(json: Json = Json.Default): T =
    json.decodeFromJsonElement(
        deserializer = json.serializersModule.serializer<T>(),
        element = element
    )
