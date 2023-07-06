package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("ClassName")
@Serializable
class __Schema(
    @SerialName(value = "types") val types: List<__Type> = emptyList(),
    @SerialName(value = "queryType") val queryType: __Type,
    @SerialName(value = "mutationType") val mutationType: __Type? = null,
    @SerialName(value = "subscriptionType") val subscriptionType: __Type? = null,
    @SerialName(value = "directives") val directives: List<__Directive> = emptyList()
) : IntrospectionType {

    @SerialName(value = "__typename")
    override val __typename: String = "__Schema"

    fun copy(
        types: List<__Type> = this.types,
        queryType: __Type = this.queryType,
        mutationType: __Type? = this.mutationType,
        subscriptionType: __Type? = this.subscriptionType,
        directives: List<__Directive> = this.directives
    ): __Schema = __Schema(
        types = types,
        queryType = queryType,
        mutationType = mutationType,
        subscriptionType = subscriptionType,
        directives = directives
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is __Schema) return false

        if (types != other.types) return false
        if (queryType != other.queryType) return false
        if (mutationType != other.mutationType) return false
        if (subscriptionType != other.subscriptionType) return false

        return directives == other.directives
    }

    override fun hashCode(): Int {
        var result = types.hashCode()
        result = 31 * result + queryType.hashCode()
        result = 31 * result + mutationType.hashCode()
        result = 31 * result + subscriptionType.hashCode()
        result = 31 * result + directives.hashCode()
        return result
    }

    override fun toString(): String =
        "__Schema(" +
                "types=$types, " +
                "queryType=$queryType, " +
                "mutationType=$mutationType, " +
                "subscriptionType=$subscriptionType, " +
                "directives=$directives)"
}
