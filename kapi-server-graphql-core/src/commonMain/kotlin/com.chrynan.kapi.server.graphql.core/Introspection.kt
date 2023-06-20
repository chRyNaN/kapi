package com.chrynan.kapi.server.graphql.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Kind(val serialName: String) {

    @SerialName(value = "ENUM")
    ENUM(serialName = "ENUM"),

    @SerialName(value = "INTERFACE")
    INTERFACE(serialName = "INTERFACE"),

    @SerialName(value = "OBJECT")
    OBJECT(serialName = "OBJECT"),

    @SerialName(value = "INPUT_OBJECT")
    INPUT_OBJECT(serialName = "INPUT_OBJECT"),

    @SerialName(value = "SCALAR")
    SCALAR(serialName = "SCALAR"),

    @SerialName(value = "NON_NULL")
    NON_NULL(serialName = "NON_NULL"),

    @SerialName(value = "LIST")
    LIST(serialName = "LIST"),

    @SerialName(value = "UNION")
    UNION(serialName = "UNION");

    companion object {

        fun fromSerialName(name: String, ignoreCase: Boolean = true): Kind? =
            values().firstOrNull {
                if (ignoreCase) {
                    it.serialName.lowercase() == name.lowercase()
                } else {
                    it.serialName == name
                }
            }
    }
}

@Serializable
data class TypeRef(
    @SerialName(value = "kind") val kind: Kind,
    @SerialName(value = "name") val name: String? = "",
    @SerialName(value = "ofType") val ofType: TypeRef? = null
) {

    /**
     * Retrieves the nested [TypeRef] of this [TypeRef]. For instance, if this is a wrapped [TypeRef], such as, a
     * [Kind.LIST], then it would be a TypeRef of a TypeRef. This property gets the inner-most [TypeRef]. For example,
     * if this class represented a [TypeRef] of [Kind.LIST] of [Kind.NON_NULL] of a [Kind.SCALAR] ([String!]), then
     * this [rawType] property would return the [Kind.SCALAR] [TypeRef] (String).
     */
    val rawType: TypeRef = ofType?.rawType ?: this
}
