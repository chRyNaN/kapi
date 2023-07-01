package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class FragmentDefinition(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "type_condition") val typeCondition: TypeName,
    @SerialName(value = "selection_set") val selectionSet: SelectionSet,
    @SerialName(value = "directives") val directives: List<Directive> = emptyList(),
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : Definition {

    @Transient
    override val children: List<Node> = buildList {
        add(typeCondition)
        addAll(directives)
        add(selectionSet)
    }

    fun copy(
        name: String = this.name,
        typeCondition: TypeName = this.typeCondition,
        selectionSet: SelectionSet = this.selectionSet,
        directives: List<Directive> = this.directives,
        sourceLocation: SourceLocation? = this.sourceLocation,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): FragmentDefinition = FragmentDefinition(
        name = name,
        typeCondition = typeCondition,
        selectionSet = selectionSet,
        directives = directives,
        sourceLocation = sourceLocation,
        comments = comments,
        ignoredChars = ignoredChars,
        additionalData = additionalData
    )

    override fun isContentEqualTo(node: Node): Boolean {
        if (this == node) return true
        if (node !is FragmentDefinition) return false

        return name == node.name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FragmentDefinition) return false

        if (name != other.name) return false
        if (typeCondition != other.typeCondition) return false
        if (selectionSet != other.selectionSet) return false
        if (directives != other.directives) return false
        if (sourceLocation != other.sourceLocation) return false
        if (comments != other.comments) return false
        if (ignoredChars != other.ignoredChars) return false
        if (additionalData != other.additionalData) return false

        return children == other.children
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + typeCondition.hashCode()
        result = 31 * result + selectionSet.hashCode()
        result = 31 * result + directives.hashCode()
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "FragmentDefinition(" +
                "name='$name', " +
                "typeCondition=$typeCondition, " +
                "selectionSet=$selectionSet, " +
                "directives=$directives, " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
