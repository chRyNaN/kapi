@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TypeKind(val serialName: String) {

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

        fun getBySerialName(name: String, ignoreCase: Boolean = true): TypeKind? =
            values().firstOrNull {
                if (ignoreCase) {
                    it.serialName.lowercase() == name.lowercase()
                } else {
                    it.serialName == name
                }
            }
    }
}
