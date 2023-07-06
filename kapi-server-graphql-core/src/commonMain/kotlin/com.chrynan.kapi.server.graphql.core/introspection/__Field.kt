package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("ClassName")
@Serializable
class __Field(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "isDeprecated") val isDeprecated: Boolean = false,
    @SerialName(value = "deprecationReason") val deprecationReason: String? = null,
    @SerialName(value = "type") val type: __Type,
    @SerialName(value = "args") val args: List<__InputValue> = emptyList()
) : IntrospectionType {

    @SerialName(value = "__typename")
    override val __typename: String = "__Field"

    fun copy(
        name: String = this.name,
        description: String? = this.description,
        isDeprecated: Boolean = this.isDeprecated,
        deprecationReason: String? = this.deprecationReason,
        type: __Type = this.type,
        args: List<__InputValue> = this.args
    ): __Field = __Field(
        name = name,
        description = description,
        isDeprecated = isDeprecated,
        deprecationReason = deprecationReason,
        type = type,
        args = args
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is __Field) return false

        if (name != other.name) return false
        if (description != other.description) return false
        if (isDeprecated != other.isDeprecated) return false
        if (deprecationReason != other.deprecationReason) return false
        if (type != other.type) return false

        return args == other.args
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + isDeprecated.hashCode()
        result = 31 * result + (deprecationReason?.hashCode() ?: 0)
        result = 31 * result + type.hashCode()
        result = 31 * result + args.hashCode()
        return result
    }

    override fun toString(): String =
        "__Field(name='$name', description=$description, isDeprecated=$isDeprecated, deprecationReason=$deprecationReason, type=$type, args=$args)"
}
