package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("ClassName")
@Serializable
class __EnumValue(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "isDeprecated") val isDeprecated: Boolean = false,
    @SerialName(value = "deprecationReason") val deprecationReason: String? = null
) : IntrospectionType {

    @SerialName(value = "__typename")
    override val __typename: String = "__EnumValue"

    fun copy(
        name: String = this.name,
        description: String? = this.description,
        isDeprecated: Boolean = this.isDeprecated,
        deprecationReason: String? = this.deprecationReason
    ): __EnumValue = __EnumValue(
        name = name,
        description = description,
        isDeprecated = isDeprecated,
        deprecationReason = deprecationReason
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is __EnumValue) return false

        if (name != other.name) return false
        if (description != other.description) return false
        if (isDeprecated != other.isDeprecated) return false

        return deprecationReason == other.deprecationReason
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + isDeprecated.hashCode()
        result = 31 * result + (deprecationReason?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        "__EnumValue(" +
                "name='$name', " +
                "description=$description, " +
                "isDeprecated=$isDeprecated, " +
                "deprecationReason=$deprecationReason)"
}
