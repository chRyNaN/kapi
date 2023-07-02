package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName(value = "DirectiveDefinition")
class DirectiveDefinition(
    @SerialName(value = "name") override val name: String,
    @SerialName(value = "repeatable") val isRepeatable: Boolean = false,
    @SerialName(value = "description") val description: Description? = null,
    @SerialName(value = "input_values") val inputValues: List<InputValueDefinition> = emptyList(),
    @SerialName(value = "locations") val locations: List<DirectiveLocation> = emptyList(),
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : SDLDefinition,
    NamedNode {

    @Transient
    override val children: List<Node> = buildList {
        addAll(inputValues)
        addAll(locations)
    }

    fun copy(
        name: String = this.name,
        isRepeatable: Boolean = this.isRepeatable,
        description: Description? = this.description,
        inputValues: List<InputValueDefinition> = this.inputValues,
        locations: List<DirectiveLocation> = this.locations,
        sourceLocation: SourceLocation? = this.sourceLocation,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): DirectiveDefinition = DirectiveDefinition(
        name = name,
        isRepeatable = isRepeatable,
        description = description,
        inputValues = inputValues,
        locations = locations,
        sourceLocation = sourceLocation,
        comments = comments,
        ignoredChars = ignoredChars,
        additionalData = additionalData
    )

    override fun isContentEqualTo(node: Node): Boolean {
        if (this == node) return true
        if (node !is DirectiveDefinition) return false

        return name == node.name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DirectiveDefinition) return false

        if (name != other.name) return false
        if (isRepeatable != other.isRepeatable) return false
        if (description != other.description) return false
        if (inputValues != other.inputValues) return false
        if (locations != other.locations) return false
        if (sourceLocation != other.sourceLocation) return false
        if (comments != other.comments) return false
        if (ignoredChars != other.ignoredChars) return false
        if (additionalData != other.additionalData) return false

        return children == other.children
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + isRepeatable.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + inputValues.hashCode()
        result = 31 * result + locations.hashCode()
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "DirectiveDefinition(" +
                "name='$name', " +
                "isRepeatable=$isRepeatable, " +
                "description=$description, " +
                "inputValues=$inputValues, " +
                "locations=$locations, " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
