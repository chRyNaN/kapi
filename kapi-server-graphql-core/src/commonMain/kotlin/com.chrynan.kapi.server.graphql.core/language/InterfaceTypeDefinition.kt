package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class InterfaceTypeDefinition(
    @SerialName(value = "name") override val name: String,
    @SerialName(value = "implements") val implements: List<TypeName> = emptyList(),
    @SerialName(value = "field_definitions") val fieldDefinitions: List<FieldDefinition> = emptyList(),
    @SerialName(value = "directives") override val directives: List<Directive> = emptyList(),
    @SerialName(value = "description") override val description: Description? = null,
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : ImplementingTypeDefinition,
    DirectivesContainer,
    NamedNode,
    DescribedNode {

    @Transient
    override val children: List<Node> = buildList {
        addAll(implements)
        addAll(fieldDefinitions)
        addAll(directives)
    }

    fun copy(
        name: String = this.name,
        implements: List<TypeName> = this.implements,
        fieldDefinitions: List<FieldDefinition> = this.fieldDefinitions,
        directives: List<Directive> = this.directives,
        description: Description? = this.description,
        sourceLocation: SourceLocation? = this.sourceLocation,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): InterfaceTypeDefinition = InterfaceTypeDefinition(
        name = name,
        implements = implements,
        fieldDefinitions = fieldDefinitions,
        directives = directives,
        description = description,
        sourceLocation = sourceLocation,
        comments = comments,
        ignoredChars = ignoredChars,
        additionalData = additionalData
    )

    override fun isContentEqualTo(node: Node): Boolean {
        if (this == node) return true
        if (node !is InterfaceTypeDefinition) return false

        return name == node.name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InterfaceTypeDefinition) return false

        if (name != other.name) return false
        if (implements != other.implements) return false
        if (fieldDefinitions != other.fieldDefinitions) return false
        if (directives != other.directives) return false
        if (description != other.description) return false
        if (sourceLocation != other.sourceLocation) return false
        if (comments != other.comments) return false
        if (ignoredChars != other.ignoredChars) return false
        if (additionalData != other.additionalData) return false

        return children == other.children
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + implements.hashCode()
        result = 31 * result + fieldDefinitions.hashCode()
        result = 31 * result + directives.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "InterfaceTypeDefinition(" +
                "name='$name', " +
                "implements=$implements, " +
                "fieldDefinitions=$fieldDefinitions, " +
                "directives=$directives, " +
                "description=$description, " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
