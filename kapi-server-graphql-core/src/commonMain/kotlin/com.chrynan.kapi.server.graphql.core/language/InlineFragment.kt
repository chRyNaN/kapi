package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Represents an inline fragment in a GraphQL Query. An inline fragment is a fragment defined on a GraphQL Type inlined
 * within a GraphQL Query and looks like the following: `... on Type { ... }`.
 *
 * @see [FragmentSpread]
 * @see [Specification](https://spec.graphql.org/June2018/#sec-Language.Document)
 */
@Serializable
@SerialName(value = "InlineFragment")
class InlineFragment(
    @SerialName(value = "type_condition") val typeCondition: TypeName? = null,
    @SerialName(value = "directives") val directives: List<Directive> = emptyList(),
    @SerialName(value = "selection_set") val selectionSet: SelectionSet,
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : Node,
    Selection {

    @Transient
    override val children: List<Node> = buildList {
        if (typeCondition != null) {
            add(typeCondition)
        }

        addAll(directives)
        add(selectionSet)
    }

    fun copy(
        typeCondition: TypeName? = this.typeCondition,
        directives: List<Directive> = this.directives,
        selectionSet: SelectionSet = this.selectionSet,
        sourceLocation: SourceLocation? = this.sourceLocation,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): InlineFragment = InlineFragment(
        typeCondition = typeCondition,
        directives = directives,
        selectionSet = selectionSet,
        sourceLocation = sourceLocation,
        comments = comments,
        ignoredChars = ignoredChars,
        additionalData = additionalData
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InlineFragment) return false

        if (typeCondition != other.typeCondition) return false
        if (directives != other.directives) return false
        if (selectionSet != other.selectionSet) return false
        if (sourceLocation != other.sourceLocation) return false
        if (comments != other.comments) return false
        if (ignoredChars != other.ignoredChars) return false
        if (additionalData != other.additionalData) return false

        return children == other.children
    }

    override fun hashCode(): Int {
        var result = typeCondition?.hashCode() ?: 0
        result = 31 * result + directives.hashCode()
        result = 31 * result + selectionSet.hashCode()
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "InlineFragment(" +
                "typeCondition=$typeCondition, " +
                "directives=$directives, " +
                "selectionSet=$selectionSet, " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
