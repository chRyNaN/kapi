package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
class Argument(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "isDeprecated") val isDeprecated: Boolean = false,
    @SerialName(value = "deprecationReason") val deprecationReason: String? = null,
    @SerialName(value = "type") val type: Type,
    @SerialName(value = "defaultValue") val defaultValue: JsonElement? = null
) {

    fun copy(
        name: String = this.name,
        description: String? = this.description,
        isDeprecated: Boolean = this.isDeprecated,
        deprecationReason: String? = this.deprecationReason,
        type: Type = this.type,
        defaultValue: JsonElement? = this.defaultValue
    ): Argument = Argument(
        name = name,
        description = description,
        isDeprecated = isDeprecated,
        deprecationReason = deprecationReason,
        type = type,
        defaultValue = defaultValue
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Argument) return false

        if (name != other.name) return false
        if (description != other.description) return false
        if (isDeprecated != other.isDeprecated) return false
        if (deprecationReason != other.deprecationReason) return false
        if (type != other.type) return false

        return defaultValue == other.defaultValue
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + isDeprecated.hashCode()
        result = 31 * result + (deprecationReason?.hashCode() ?: 0)
        result = 31 * result + type.hashCode()
        result = 31 * result + (defaultValue?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        "Argument(name='$name', description=$description, isDeprecated=$isDeprecated, deprecationReason=$deprecationReason, type=$type, defaultValue=$defaultValue)"
}
