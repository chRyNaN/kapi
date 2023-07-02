package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Represents a fragment spread in a GraphQL Query. A fragment spread is a reference to a fragment definition and looks
 * like the following: `...FragmentName`.
 *
 * @see [InlineFragment]
 * @see [Specification](https://spec.graphql.org/June2018/#sec-Language.Document)
 */
@Serializable
@SerialName(value = "FragmentSpread")
class FragmentSpread(
    @SerialName(value = "name") override val name: String,
    @SerialName(value = "directives") override val directives: List<Directive> = emptyList(),
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : Node,
    NamedNode,
    Selection,
    DirectivesContainer {

    @Transient
    override val children: List<Node> = directives

    fun copy(
        name: String = this.name,
        directives: List<Directive> = this.directives,
        sourceLocation: SourceLocation? = this.sourceLocation,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): FragmentSpread = FragmentSpread(
        name = name,
        directives = directives,
        sourceLocation = sourceLocation,
        comments = comments,
        ignoredChars = ignoredChars,
        additionalData = additionalData
    )

    override fun isContentEqualTo(node: Node): Boolean {
        if (this == node) return true
        if (node !is FragmentSpread) return false

        return name == node.name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FragmentSpread) return false

        if (name != other.name) return false
        if (directives != other.directives) return false
        if (sourceLocation != other.sourceLocation) return false
        if (comments != other.comments) return false
        if (ignoredChars != other.ignoredChars) return false
        if (additionalData != other.additionalData) return false

        return children == other.children
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + directives.hashCode()
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "FragmentSpread(" +
                "name='$name', " +
                "directives=$directives, " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
