package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.*

@Serializable
@SerialName(value = "Field")
class Field(
    @SerialName(value = "name") override val name: String,
    @SerialName(value = "alias") val alias: String? = null,
    @SerialName(value = "arguments") val arguments: Arguments = emptyArguments(),
    @SerialName(value = "directives") override val directives: List<Directive> = emptyList(),
    @SerialName(value = "selection_set") override val selectionSet: SelectionSet? = null,
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : Node,
    NamedNode,
    Selection,
    SelectionSetContainer,
    DirectivesContainer {

    @Transient
    override val children: List<Node> = buildList {
        addAll(arguments)
        addAll(directives)

        if (selectionSet != null) {
            add(selectionSet)
        }
    }

    fun copy(
        name: String = this.name,
        alias: String? = this.alias,
        arguments: Arguments = this.arguments,
        directives: List<Directive> = this.directives,
        selectionSet: SelectionSet? = this.selectionSet,
        sourceLocation: SourceLocation? = this.sourceLocation,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): Field = Field(
        name = name,
        alias = alias,
        arguments = arguments,
        directives = directives,
        selectionSet = selectionSet,
        sourceLocation = sourceLocation,
        comments = comments,
        ignoredChars = ignoredChars,
        additionalData = additionalData
    )

    override fun isContentEqualTo(node: Node): Boolean {
        if (this == node) return true
        if (node !is Field) return false

        if (name != node.name) return false

        return alias == node.alias
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Field) return false

        if (name != other.name) return false
        if (alias != other.alias) return false
        if (arguments != other.arguments) return false
        if (directives != other.directives) return false
        if (selectionSet != other.selectionSet) return false
        if (sourceLocation != other.sourceLocation) return false
        if (comments != other.comments) return false
        if (ignoredChars != other.ignoredChars) return false
        if (additionalData != other.additionalData) return false

        return children == other.children
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (alias?.hashCode() ?: 0)
        result = 31 * result + arguments.hashCode()
        result = 31 * result + directives.hashCode()
        result = 31 * result + (selectionSet?.hashCode() ?: 0)
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "Field(" +
                "name='$name', " +
                "alias=$alias, " +
                "arguments=$arguments, " +
                "directives=$directives, " +
                "selectionSet=$selectionSet, " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
