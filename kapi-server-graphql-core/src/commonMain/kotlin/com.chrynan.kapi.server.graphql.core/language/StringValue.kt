package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable
@SerialName(value = "StringValue")
class StringValue(
    @SerialName(value = "value") val value: String,
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : Node,
    ScalarValue,
    JsonValue {

    @Transient
    override val children: List<Node> = emptyList()

    @Transient
    override val element: JsonElement = JsonPrimitive(value)

    fun copy(
        value: String = this.value,
        sourceLocation: SourceLocation? = this.sourceLocation,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): StringValue = StringValue(
        value = value,
        sourceLocation = sourceLocation,
        comments = comments,
        ignoredChars = ignoredChars,
        additionalData = additionalData
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StringValue) return false

        if (value != other.value) return false
        if (sourceLocation != other.sourceLocation) return false
        if (comments != other.comments) return false
        if (ignoredChars != other.ignoredChars) return false
        if (additionalData != other.additionalData) return false

        return children == other.children
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "StringValue(" +
                "value='$value', " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
