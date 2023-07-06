package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("ClassName")
@Serializable
class __InputValue(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "type") val type: __Type,
    @SerialName(value = "defaultValue") val defaultValue: String? = null
) : IntrospectionType {

    @SerialName(value = "__typename")
    override val __typename: String = "__InputValue"

    fun copy(
        name: String = this.name,
        description: String? = this.description,
        type: __Type = this.type,
        defaultValue: String? = this.defaultValue
    ): __InputValue = __InputValue(
        name = name,
        description = description,
        type = type,
        defaultValue = defaultValue
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is __InputValue) return false

        if (name != other.name) return false
        if (description != other.description) return false
        if (type != other.type) return false

        return defaultValue == other.defaultValue
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + type.hashCode()
        result = 31 * result + (defaultValue?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        "__InputValue(name='$name', description=$description, type=$type, defaultValue=$defaultValue)"
}
