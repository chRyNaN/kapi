@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DirectiveLocation(val serialName: String) {

    @SerialName(value = "SCHEMA")
    SCHEMA(serialName = "SCHEMA"),

    @SerialName(value = "QUERY")
    QUERY(serialName = "QUERY"),

    @SerialName(value = "MUTATION")
    MUTATION(serialName = "MUTATION"),

    @SerialName(value = "SUBSCRIPTION")
    SUBSCRIPTION(serialName = "SUBSCRIPTION"),

    @SerialName(value = "SCALAR")
    SCALAR(serialName = "SCALAR"),

    @SerialName(value = "OBJECT")
    OBJECT(serialName = "OBJECT"),

    @SerialName(value = "FIELD_DEFINITION")
    FIELD_DEFINITION(serialName = "FIELD_DEFINITION"),

    @SerialName(value = "ARGUMENT_DEFINITION")
    ARGUMENT_DEFINITION(serialName = "ARGUMENT_DEFINITION"),

    @SerialName(value = "INTERFACE")
    INTERFACE(serialName = "INTERFACE"),

    @SerialName(value = "UNION")
    UNION(serialName = "UNION"),

    @SerialName(value = "ENUM")
    ENUM(serialName = "ENUM"),

    @SerialName(value = "ENUM_VALUE")
    ENUM_VALUE(serialName = "ENUM_VALUE"),

    @SerialName(value = "INPUT_OBJECT")
    INPUT_OBJECT(serialName = "INPUT_OBJECT"),

    @SerialName(value = "INPUT_FIELD_DEFINITION")
    INPUT_FIELD_DEFINITION(serialName = "INPUT_FIELD_DEFINITION"),

    @SerialName(value = "FIELD")
    FIELD(serialName = "FIELD"),

    @SerialName(value = "FRAGMENT_DEFINITION")
    FRAGMENT_DEFINITION(serialName = "FRAGMENT_DEFINITION"),

    @SerialName(value = "FRAGMENT")
    FRAGMENT_SPREAD(serialName = "FRAGMENT"),

    @SerialName(value = "INLINE_FRAGMENT")
    INLINE_FRAGMENT(serialName = "INLINE_FRAGMENT"),

    @SerialName(value = "VARIABLE_DEFINITION")
    VARIABLE_DEFINITION(serialName = "VARIABLE_DEFINITION");

    companion object {

        fun getBySerialName(name: String, ignoreCase: Boolean = true): DirectiveLocation? =
            values().firstOrNull {
                if (ignoreCase) {
                    it.serialName.lowercase() == name.lowercase()
                } else {
                    it.serialName == name
                }
            }
    }
}
