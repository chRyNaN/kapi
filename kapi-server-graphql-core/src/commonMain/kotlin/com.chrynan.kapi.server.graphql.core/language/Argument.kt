package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer

/**
 * Represents a GraphQL Argument passed in through a query to access a field.
 *
 * @property [name] The name of the argument.
 * @property [element] The [JsonElement] representing the value fo the argument. This can be converted to the
 * appropriate type via a call to the [value] function.
 */
class Argument(
    val name: String,
    val element: JsonElement
) {

    /**
     * Creates a copy of this [Argument] by overriding the provided values.
     */
    fun copy(
        name: String = this.name,
        element: JsonElement = this.element
    ): Argument = Argument(
        name = name,
        element = element
    )

    operator fun component1(): String = name

    operator fun component2(): JsonElement = element

    /**
     * Retrieves the value of type [T] for this [Argument], using the provided [json] value to convert this argument's
     * [element] into a value of type [T]. This function implementation calls the [Json.decodeFromJsonElement]
     * function.
     *
     * The type of [element] is not known when this [Argument] class is instantiated. So it is up to the call-site to
     * know the type of [element] according to the associated GraphQL Schema.
     *
     * @see [Json.decodeFromJsonElement]
     */
    inline fun <reified T> value(json: Json = Json.Default): T =
        json.decodeFromJsonElement(
            deserializer = json.serializersModule.serializer<T>(),
            element = element
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Argument) return false

        if (name != other.name) return false

        return element == other.element
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + element.hashCode()
        return result
    }

    override fun toString(): String =
        "Argument(name='$name', element=$element)"
}
