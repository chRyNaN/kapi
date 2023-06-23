package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DirectiveLocation(val typeName: String) {

    @SerialName(value = "SCHEMA")
    SCHEMA(typeName = "SCHEMA"),

    @SerialName(value = "QUERY")
    QUERY(typeName = "QUERY"),

    @SerialName(value = "MUTATION")
    MUTATION(typeName = "MUTATION"),

    @SerialName(value = "SUBSCRIPTION")
    SUBSCRIPTION(typeName = "SUBSCRIPTION"),

    @SerialName(value = "SCALAR")
    SCALAR(typeName = "SCALAR"),

    @SerialName(value = "OBJECT")
    OBJECT(typeName = "OBJECT"),

    @SerialName(value = "FIELD_DEFINITION")
    FIELD_DEFINITION(typeName = "FIELD_DEFINITION"),

    @SerialName(value = "ARGUMENT_DEFINITION")
    ARGUMENT_DEFINITION(typeName = "ARGUMENT_DEFINITION"),

    @SerialName(value = "INTERFACE")
    INTERFACE(typeName = "INTERFACE"),

    @SerialName(value = "UNION")
    UNION(typeName = "UNION"),

    @SerialName(value = "ENUM")
    ENUM(typeName = "ENUM"),

    @SerialName(value = "ENUM_VALUE")
    ENUM_VALUE(typeName = "ENUM_VALUE"),

    @SerialName(value = "INPUT_OBJECT")
    INPUT_OBJECT(typeName = "INPUT_OBJECT"),

    @SerialName(value = "INPUT_FIELD_DEFINITION")
    INPUT_FIELD_DEFINITION(typeName = "INPUT_FIELD_DEFINITION"),

    @SerialName(value = "FIELD")
    FIELD(typeName = "FIELD"),

    @SerialName(value = "FRAGMENT_DEFINITION")
    FRAGMENT_DEFINITION(typeName = "FRAGMENT_DEFINITION"),

    @SerialName(value = "FRAGMENT")
    FRAGMENT_SPREAD(typeName = "FRAGMENT"),

    @SerialName(value = "INLINE_FRAGMENT")
    INLINE_FRAGMENT(typeName = "INLINE_FRAGMENT"),

    @SerialName(value = "VARIABLE_DEFINITION")
    VARIABLE_DEFINITION(typeName = "VARIABLE_DEFINITION");

    companion object {

        fun getBySerialName(name: String, ignoreCase: Boolean = true): DirectiveLocation? =
            values().firstOrNull {
                if (ignoreCase) {
                    it.typeName.lowercase() == name.lowercase()
                } else {
                    it.typeName == name
                }
            }
    }
}
