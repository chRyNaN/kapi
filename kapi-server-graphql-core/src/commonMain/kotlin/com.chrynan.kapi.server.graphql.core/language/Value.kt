package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer

/**
 * Represents a GraphQL Value, which can be a value passed as an argument to an operation, or a default value assigned
 * to an argument in a schema.
 */
@Serializable
class Value(
    @SerialName(value = "element") val element: JsonElement,
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : Node {

    @Transient
    override val children: List<Node> = emptyList()

    fun copy(
        element: JsonElement = this.element,
        sourceLocation: SourceLocation? = this.sourceLocation,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): Value = Value(
        element = element,
        sourceLocation = sourceLocation,
        comments = comments,
        ignoredChars = ignoredChars,
        additionalData = additionalData
    )

    /**
     * Retrieves the value of type [T] for this [Value], using the provided [json] value to convert this value's
     * [element] into a value of type [T]. This function implementation calls the [Json.decodeFromJsonElement]
     * function.
     *
     * The type of [element] is not known when this [Value] class is instantiated. So it is up to the call-site to
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
        if (other !is Value) return false

        if (element != other.element) return false
        if (sourceLocation != other.sourceLocation) return false
        if (comments != other.comments) return false
        if (ignoredChars != other.ignoredChars) return false
        if (additionalData != other.additionalData) return false

        return children == other.children
    }

    override fun hashCode(): Int {
        var result = element.hashCode()
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "Value(" +
                "element=$element, " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
