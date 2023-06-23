package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Schema(
    @SerialName(value = "types") val types: List<Type> = emptyList(),
    @SerialName(value = "queryType") val queryType: Type,
    @SerialName(value = "mutationType") val mutationType: Type,
    @SerialName(value = "subscriptionType") val subscriptionType: Type,
    @SerialName(value = "directives") val directives: List<Directive> = emptyList()
) {

    fun copy(
        types: List<Type> = this.types,
        queryType: Type = this.queryType,
        mutationType: Type = this.mutationType,
        subscriptionType: Type = this.subscriptionType,
        directives: List<Directive> = this.directives
    ): Schema = Schema(
        types = types,
        queryType = queryType,
        mutationType = mutationType,
        subscriptionType = subscriptionType,
        directives = directives
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Schema) return false

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
        "Schema(types=$types, queryType=$queryType, mutationType=$mutationType, subscriptionType=$subscriptionType, directives=$directives)"
}
