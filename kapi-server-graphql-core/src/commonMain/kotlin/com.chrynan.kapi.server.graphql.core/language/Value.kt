@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer

/**
 * Represents a GraphQL Value which can be a value passed as an argument to an operation, or a default value assigned
 * to an argument in a schema.
 */
@Serializable
sealed interface Value : Node {

    /**
     * Retrieves the [JsonElement] representation of this [Value], optionally using the provided [variables] in the
     * case that this [Value] is a [VariableReference].
     *
     * @param [variables] The GraphQL variables passed with the GraphQL operation request. The key of the [Map] should
     * be the name of the variable, and the value should be the [JsonElement] value representation. Defaults to an
     * empty [Map].
     *
     * @throws [NoSuchElementException] if this [Value] is a [VariableReference] and the provided [variables] does not
     * contain a value for this variable.
     */
    fun element(variables: Map<String, JsonElement> = emptyMap()): JsonElement
}

/**
 * Retrieves the [JsonElement] representation of this [Value], optionally using the provided [variables] in the
 * case that this [Value] is a [VariableReference], or `null` if this [Value] is a [VariableReference] and the provided
 * [variables] does not contain a matching value for it.
 *
 * @param [variables] The GraphQL variables passed with the GraphQL operation request. The key of the [Map] should
 * be the name of the variable, and the value should be the [JsonElement] value representation. Defaults to an
 * empty [Map].
 */
fun Value.elementOrNull(variables: Map<String, JsonElement> = emptyMap()): JsonElement? =
    try {
        element(variables = variables)
    } catch (_: NoSuchElementException) {
        null
    }

/**
 * Retrieves the value of type [T] for this [Value], using the provided [json] value to convert this value's
 * [Value.element] into a value of type [T]. This function implementation calls the [Json.decodeFromJsonElement]
 * function.
 *
 * The type of [Value.element] is not known when this [Value] class is instantiated. So it is up to the
 * call-site to know the type of [Value.element] according to the associated GraphQL Schema.
 *
 * @param [variables] The GraphQL variables passed with the GraphQL operation request. The key of the [Map] should
 * be the name of the variable, and the value should be the [JsonElement] value representation. Defaults to an
 * empty [Map].
 *
 * @param [json] The [Json] instance used to convert this [Value]'s [Value.element] property to the type [T].
 *
 * @throws [NoSuchElementException] if this [Value] is a [VariableReference] and the provided [variables] does not
 * contain a value for this variable.
 *
 * @see [Json.decodeFromJsonElement]
 * @see [Value.element]
 */
inline fun <reified T> Value.value(
    variables: Map<String, JsonElement> = emptyMap(),
    json: Json = Json.Default
): T = json.decodeFromJsonElement(
    deserializer = json.serializersModule.serializer<T>(),
    element = element(variables = variables)
)

/**
 * Retrieves the value of type [T] for this [Value], using the provided [json] value to convert this value's
 * [Value.element] into a value of type [T], or `null` if this [Value] is a [VariableReference] and the provided
 * [variables] does not contain a matching value for it. This function implementation calls the
 * [Json.decodeFromJsonElement] function.
 *
 * The type of [Value.element] is not known when this [Value] class is instantiated. So it is up to the
 * call-site to know the type of [Value.element] according to the associated GraphQL Schema.
 *
 * @param [variables] The GraphQL variables passed with the GraphQL operation request. The key of the [Map] should
 * be the name of the variable, and the value should be the [JsonElement] value representation. Defaults to an
 * empty [Map].
 *
 * @param [json] The [Json] instance used to convert this [Value]'s [Value.element] property to the type [T].
 *
 * @see [Json.decodeFromJsonElement]
 * @see [Value.element]
 */
inline fun <reified T> Value.valueOrNull(
    variables: Map<String, JsonElement> = emptyMap(),
    json: Json = Json.Default
): T? = try {
    value(variables = variables, json = json)
} catch (_: NoSuchElementException) {
    null
}
