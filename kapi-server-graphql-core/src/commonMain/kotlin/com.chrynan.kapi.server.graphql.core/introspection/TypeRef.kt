package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class TypeRef(
    @SerialName(value = "kind") val kind: TypeKind,
    @SerialName(value = "name") val name: String? = "",
    @SerialName(value = "ofType") val ofType: TypeRef? = null
) {

    fun copy(
        kind: TypeKind = this.kind,
        name: String? = this.name,
        ofType: TypeRef? = this.ofType
    ): TypeRef = TypeRef(
        kind = kind,
        name = name,
        ofType = ofType
    )

    operator fun component1(): TypeKind = kind

    operator fun component2(): String? = name

    operator fun component3(): TypeRef? = ofType

    operator fun component4(): TypeRef = rawType

    /**
     * Retrieves the nested [TypeRef] of this [TypeRef]. For instance, if this is a wrapped [TypeRef], such as, a
     * [TypeKind.LIST], then it would be a TypeRef of a TypeRef. This property gets the innermost [TypeRef]. For example,
     * if this class represented a [TypeRef] of [TypeKind.LIST] of [TypeKind.NON_NULL] of a [TypeKind.SCALAR] ([String!]), then
     * this [rawType] property would return the [TypeKind.SCALAR] [TypeRef] (String).
     */
    @Transient
    @Suppress("TRANSIENT_IS_REDUNDANT") // Want to be explicit that it is transient.
    val rawType: TypeRef by lazy { ofType?.rawType ?: this }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TypeRef) return false

        if (kind != other.kind) return false
        if (name != other.name) return false
        if (ofType != other.ofType) return false

        return rawType == other.rawType
    }

    override fun hashCode(): Int {
        var result = kind.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (ofType?.hashCode() ?: 0)
        result = 31 * result + rawType.hashCode()
        return result
    }

    override fun toString(): String =
        "TypeRef(kind=$kind, name=$name, ofType=$ofType, rawType=$rawType)"
}
