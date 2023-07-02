package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json

@Serializable
@SerialName(value = "InputValueDefinition")
class InputValueDefinition(
    @SerialName(value = "name") override val name: String,
    @SerialName(value = "type") val type: Type,
    @SerialName(value = "default_value") val defaultValue: Value? = null,
    @SerialName(value = "description") override val description: Description? = null,
    @SerialName(value = "directives") override val directives: List<Directive> = emptyList(),
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : Node,
    NamedNode,
    DescribedNode,
    DirectivesContainer {

    @Transient
    override val children: List<Node> = buildList {
        add(type)

        if (defaultValue != null) {
            add(defaultValue)
        }

        addAll(directives)
    }

    fun copy(
        name: String = this.name,
        type: Type = this.type,
        defaultValue: Value? = this.defaultValue,
        description: Description? = this.description,
        directives: List<Directive> = this.directives,
        sourceLocation: SourceLocation? = this.sourceLocation,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): InputValueDefinition = InputValueDefinition(
        name = name,
        type = type,
        defaultValue = defaultValue,
        description = description,
        directives = directives,
        sourceLocation = sourceLocation,
        comments = comments,
        ignoredChars = ignoredChars,
        additionalData = additionalData
    )

    /**
     * This is a convenience function for invoking the [Value.value] function on the [defaultValue] property.
     *
     * @see [Value.value]
     */
    inline fun <reified T> defaultValue(json: Json = Json.Default): T? =
        defaultValue?.value(json = json)

    override fun isContentEqualTo(node: Node): Boolean {
        if (this == node) return true
        if (node !is InputValueDefinition) return false

        return name == node.name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InputValueDefinition) return false

        if (name != other.name) return false
        if (type != other.type) return false
        if (defaultValue != other.defaultValue) return false
        if (description != other.description) return false
        if (directives != other.directives) return false
        if (sourceLocation != other.sourceLocation) return false
        if (comments != other.comments) return false
        if (ignoredChars != other.ignoredChars) return false
        if (additionalData != other.additionalData) return false

        return children == other.children
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (defaultValue?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + directives.hashCode()
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "InputValueDefinition(" +
                "name='$name', " +
                "type=$type, " +
                "defaultValue=$defaultValue, " +
                "description=$description, " +
                "directives=$directives, " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
