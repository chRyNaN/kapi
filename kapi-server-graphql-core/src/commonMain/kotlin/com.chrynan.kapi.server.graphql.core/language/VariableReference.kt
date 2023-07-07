package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.JsonElement

/**
 * Represents a reference to a variable within a GraphQL Operation.
 */
@Serializable
@SerialName(value = "VariableReference")
class VariableReference(
    @SerialName(value = "name") override val name: String,
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : Node,
    NamedNode,
    Value {

    @Transient
    override val children: List<Node> = emptyList()

    fun copy(
        name: String = this.name,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): VariableReference = VariableReference(
        name = name,
        comments = comments,
        ignoredChars = ignoredChars,
        additionalData = additionalData
    )

    override fun element(variables: Map<String, LiteralValue>): JsonElement =
        variables[name]?.literalElement
            ?: throw NoSuchElementException("There was no variable with the name `$name` provided.")

    override fun isContentEqualTo(node: Node): Boolean {
        if (this == node) return true
        if (node !is VariableReference) return false

        return name == node.name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VariableReference) return false

        if (name != other.name) return false
        if (sourceLocation != other.sourceLocation) return false
        if (comments != other.comments) return false
        if (ignoredChars != other.ignoredChars) return false
        if (additionalData != other.additionalData) return false

        return children == other.children
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "VariableReference(" +
                "name='$name', " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
