package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Represents a GraphQL Argument passed in through a query to access a field.
 *
 * @property [name] The name of the argument.
 * @property [value] The [JsonValue] representing the value fo the argument. This can be converted to the
 * appropriate type via a call to the [value] function.
 */
@Serializable
@SerialName(value = "Argument")
class Argument(
    @SerialName(value = "name") override val name: String,
    @SerialName(value = "value") val value: Value,
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : Node,
    NamedNode {

    @Transient
    override val children: List<Node> = listOf(value)

    /**
     * Creates a copy of this [Argument] by overriding the provided values.
     */
    fun copy(
        name: String = this.name,
        value: Value = this.value,
        sourceLocation: SourceLocation? = this.sourceLocation,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): Argument = Argument(
        name = name,
        value = value,
        sourceLocation = sourceLocation,
        comments = comments,
        ignoredChars = ignoredChars,
        additionalData = additionalData
    )

    override fun isContentEqualTo(node: Node): Boolean {
        if (this == node) return true
        if (node !is Argument) return false

        return name == node.name
    }

    operator fun component1(): String = name

    operator fun component2(): Value = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Argument) return false

        if (name != other.name) return false
        if (value != other.value) return false
        if (sourceLocation != other.sourceLocation) return false
        if (comments != other.comments) return false
        if (ignoredChars != other.ignoredChars) return false
        if (additionalData != other.additionalData) return false

        return children == other.children
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "Argument(" +
                "name='$name', " +
                "value=$value, " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
