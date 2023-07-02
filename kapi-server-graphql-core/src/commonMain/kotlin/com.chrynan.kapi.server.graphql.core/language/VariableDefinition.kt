package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json

@Serializable
@SerialName(value = "VariableDefinition")
class VariableDefinition(
    @SerialName(value = "name") override val name: String,
    @SerialName(value = "type") val type: TypeName,
    @SerialName(value = "default_value") val defaultValue: Value? = null,
    @SerialName(value = "directives") override val directives: List<Directive> = emptyList(),
    @SerialName(value = "source_location") override val sourceLocation: SourceLocation? = null,
    @SerialName(value = "comments") override val comments: List<Comment> = emptyList(),
    @SerialName(value = "ignored_chars") override val ignoredChars: IgnoredChars = IgnoredChars.EMPTY,
    @SerialName(value = "additional_data") override val additionalData: Map<String, String> = emptyMap()
) : Node,
    NamedNode,
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
        type: TypeName = this.type,
        defaultValue: Value? = this.defaultValue,
        directives: List<Directive> = this.directives,
        sourceLocation: SourceLocation? = this.sourceLocation,
        comments: List<Comment> = this.comments,
        ignoredChars: IgnoredChars = this.ignoredChars,
        additionalData: Map<String, String> = this.additionalData
    ): VariableDefinition = VariableDefinition(
        name = name,
        type = type,
        defaultValue = defaultValue,
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
        if (node !is VariableDefinition) return false

        return name == node.name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VariableDefinition) return false

        if (name != other.name) return false
        if (type != other.type) return false
        if (defaultValue != other.defaultValue) return false
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
        result = 31 * result + directives.hashCode()
        result = 31 * result + (sourceLocation?.hashCode() ?: 0)
        result = 31 * result + comments.hashCode()
        result = 31 * result + ignoredChars.hashCode()
        result = 31 * result + additionalData.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun toString(): String =
        "VariableDefinition(" +
                "name='$name', " +
                "type=$type, " +
                "defaultValue=$defaultValue, " +
                "directives=$directives, " +
                "sourceLocation=$sourceLocation, " +
                "comments=$comments, " +
                "ignoredChars=$ignoredChars, " +
                "additionalData=$additionalData, " +
                "children=$children)"
}
