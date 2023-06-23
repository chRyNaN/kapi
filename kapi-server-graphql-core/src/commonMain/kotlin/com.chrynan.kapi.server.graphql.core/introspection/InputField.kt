package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class InputField(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "type") val type: Type,
    @SerialName(value = "defaultValue") val defaultValue: String? = null
) {

    fun copy(
        name: String = this.name,
        description: String? = this.description,
        type: Type = this.type,
        defaultValue: String? = this.defaultValue
    ): InputField = InputField(
        name = name,
        description = description,
        type = type,
        defaultValue = defaultValue
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InputField) return false

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
        "InputField(name='$name', description=$description, type=$type, defaultValue=$defaultValue)"
}
