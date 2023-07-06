package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Transient
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * Defines the GraphQL Introspection Type model.
 *
 * @see [GraphQL Specification](https://spec.graphql.org/June2018/#sec-Introspection).
 */
@OptIn(ExperimentalSerializationApi::class)
@Suppress("ClassName")
@Serializable
@JsonClassDiscriminator("kind")
sealed class __Type private constructor() : IntrospectionType {

    @SerialName(value = "__typename")
    override val __typename: String = "__Type"

    @SerialName(value = "kind")
    abstract val kind: __TypeKind

    @SerialName(value = "name")
    abstract val name: String

    @SerialName(value = "description")
    abstract val description: String?

    @SerialName(value = "interfaces")
    abstract val interfaces: kotlin.collections.List<__Type>?

    @SerialName(value = "fields")
    abstract val fields: kotlin.collections.List<__Field>?

    @SerialName(value = "possibleTypes")
    abstract val possibleTypes: kotlin.collections.List<__Type>?

    @SerialName(value = "enumValues")
    abstract val enumValues: kotlin.collections.List<__EnumValue>?

    @SerialName(value = "inputFields")
    abstract val inputFields: kotlin.collections.List<__InputValue>?

    @SerialName(value = "ofType")
    abstract val ofType: __Type?

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is __Type) return false

        if (kind != other.kind) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (interfaces != other.interfaces) return false
        if (fields != other.fields) return false
        if (possibleTypes != other.possibleTypes) return false
        if (enumValues != other.enumValues) return false
        if (inputFields != other.inputFields) return false

        return ofType == other.ofType
    }

    override fun hashCode(): Int {
        var result = kind.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (interfaces?.hashCode() ?: 0)
        result = 31 * result + (fields?.hashCode() ?: 0)
        result = 31 * result + (possibleTypes?.hashCode() ?: 0)
        result = 31 * result + (enumValues?.hashCode() ?: 0)
        result = 31 * result + (inputFields?.hashCode() ?: 0)
        result = 31 * result + (ofType?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        "Type(kind=$kind, " +
                "name='$name', " +
                "description=$description, " +
                "interfaces=$interfaces, " +
                "fields=$fields, " +
                "possibleTypes=$possibleTypes, " +
                "enumValues=$enumValues, " +
                "inputFields=$inputFields, " +
                "ofType=$ofType)"

    @Serializable
    @SerialName(value = "SCALAR")
    class Scalar(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null
    ) : __Type() {

        @Transient
        override val kind: __TypeKind = __TypeKind.SCALAR

        @Transient
        override val interfaces: kotlin.collections.List<__Type>? = null

        @Transient
        override val fields: kotlin.collections.List<__Field>? = null

        @Transient
        override val possibleTypes: kotlin.collections.List<__Type>? = null

        @Transient
        override val enumValues: kotlin.collections.List<__EnumValue>? = null

        @Transient
        override val inputFields: kotlin.collections.List<__InputValue>? = null

        @Transient
        override val ofType: __Type? = null

        fun copy(
            name: String = this.name,
            description: String? = this.description
        ): Scalar = Scalar(
            name = name,
            description = description
        )
    }

    @Serializable
    @SerialName(value = "OBJECT")
    class Object(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "interfaces") override val interfaces: kotlin.collections.List<__Type> = emptyList(),
        @SerialName(value = "fields") override val fields: kotlin.collections.List<__Field> = emptyList()
    ) : __Type() {

        @Transient
        override val kind: __TypeKind = __TypeKind.OBJECT

        @Transient
        override val possibleTypes: kotlin.collections.List<__Type>? = null

        @Transient
        override val enumValues: kotlin.collections.List<__EnumValue>? = null

        @Transient
        override val inputFields: kotlin.collections.List<__InputValue>? = null

        @Transient
        override val ofType: __Type? = null

        fun copy(
            name: String = this.name,
            description: String? = this.description,
            interfaces: kotlin.collections.List<__Type> = this.interfaces,
            fields: kotlin.collections.List<__Field> = this.fields
        ): Object = Object(
            name = name,
            description = description,
            interfaces = interfaces,
            fields = fields
        )
    }

    @Serializable
    @SerialName(value = "INTERFACE")
    class Interface(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "possibleTypes") override val possibleTypes: kotlin.collections.List<__Type>? = null,
        @SerialName(value = "fields") override val fields: kotlin.collections.List<__Field> = emptyList()
    ) : __Type() {

        @Transient
        override val kind: __TypeKind = __TypeKind.INTERFACE

        @Transient
        override val interfaces: kotlin.collections.List<__Type>? = null

        @Transient
        override val enumValues: kotlin.collections.List<__EnumValue>? = null

        @Transient
        override val inputFields: kotlin.collections.List<__InputValue>? = null

        @Transient
        override val ofType: __Type? = null

        fun copy(
            name: String = this.name,
            description: String? = this.description,
            possibleTypes: kotlin.collections.List<__Type>? = this.possibleTypes,
            fields: kotlin.collections.List<__Field> = this.fields
        ): Interface = Interface(
            name = name,
            description = description,
            possibleTypes = possibleTypes,
            fields = fields
        )
    }

    @Serializable
    @SerialName(value = "UNION")
    class Union(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "possibleTypes") override val possibleTypes: kotlin.collections.List<__Type>? = null
    ) : __Type() {

        @Transient
        override val kind: __TypeKind = __TypeKind.UNION

        @Transient
        override val interfaces: kotlin.collections.List<__Type>? = null

        @Transient
        override val fields: kotlin.collections.List<__Field>? = null

        @Transient
        override val enumValues: kotlin.collections.List<__EnumValue>? = null

        @Transient
        override val inputFields: kotlin.collections.List<__InputValue>? = null

        @Transient
        override val ofType: __Type? = null

        fun copy(
            name: String = this.name,
            description: String? = this.description,
            possibleTypes: kotlin.collections.List<__Type>? = this.possibleTypes
        ): Union = Union(
            name = name,
            description = description,
            possibleTypes = possibleTypes
        )
    }

    @Serializable
    @SerialName(value = "ENUM")
    class Enum(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "enumValues") override val enumValues: kotlin.collections.List<__EnumValue> = emptyList()
    ) : __Type() {

        @Transient
        override val kind: __TypeKind = __TypeKind.ENUM

        @Transient
        override val interfaces: kotlin.collections.List<__Type>? = null

        @Transient
        override val fields: kotlin.collections.List<__Field>? = null

        @Transient
        override val possibleTypes: kotlin.collections.List<__Type>? = null

        @Transient
        override val inputFields: kotlin.collections.List<__InputValue>? = null

        @Transient
        override val ofType: __Type? = null

        fun copy(
            name: String = this.name,
            description: String? = this.description,
            enumValues: kotlin.collections.List<__EnumValue> = this.enumValues
        ): Enum = Enum(
            name = name,
            description = description,
            enumValues = enumValues
        )
    }

    @Serializable
    @SerialName(value = "INPUT_OBJECT")
    class InputObject(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "inputFields") override val inputFields: kotlin.collections.List<__InputValue> = emptyList()
    ) : __Type() {

        @Transient
        override val kind: __TypeKind = __TypeKind.INPUT_OBJECT

        @Transient
        override val interfaces: kotlin.collections.List<__Type>? = null

        @Transient
        override val fields: kotlin.collections.List<__Field>? = null

        @Transient
        override val possibleTypes: kotlin.collections.List<__Type>? = null

        @Transient
        override val enumValues: kotlin.collections.List<__EnumValue>? = null

        @Transient
        override val ofType: __Type? = null

        fun copy(
            name: String = this.name,
            description: String? = this.description,
            inputFields: kotlin.collections.List<__InputValue> = this.inputFields
        ): InputObject = InputObject(
            name = name,
            description = description,
            inputFields = inputFields
        )
    }

    @Serializable
    @SerialName(value = "NON_NULL")
    class NonNull(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "ofType") override val ofType: __Type
    ) : __Type() {

        @Transient
        override val kind: __TypeKind = __TypeKind.NON_NULL

        @Transient
        override val interfaces: kotlin.collections.List<__Type>? = null

        @Transient
        override val fields: kotlin.collections.List<__Field>? = null

        @Transient
        override val possibleTypes: kotlin.collections.List<__Type>? = null

        @Transient
        override val enumValues: kotlin.collections.List<__EnumValue>? = null

        @Transient
        override val inputFields: kotlin.collections.List<__InputValue>? = null

        fun copy(
            name: String = this.name,
            description: String? = this.description,
            ofType: __Type = this.ofType
        ): NonNull = NonNull(
            name = name,
            description = description,
            ofType = ofType
        )
    }

    @Serializable
    @SerialName(value = "LIST")
    class List(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "ofType") override val ofType: __Type
    ) : __Type() {

        @Transient
        override val kind: __TypeKind = __TypeKind.NON_NULL

        @Transient
        override val interfaces: kotlin.collections.List<__Type>? = null

        @Transient
        override val fields: kotlin.collections.List<__Field>? = null

        @Transient
        override val possibleTypes: kotlin.collections.List<__Type>? = null

        @Transient
        override val enumValues: kotlin.collections.List<__EnumValue>? = null

        @Transient
        override val inputFields: kotlin.collections.List<__InputValue>? = null

        fun copy(
            name: String = this.name,
            description: String? = this.description,
            ofType: __Type = this.ofType
        ): List = List(
            name = name,
            description = description,
            ofType = ofType
        )
    }
}

/**
 * Retrieves the nested [__Type] of this [__Type]. For instance, if this is a wrapped [__Type], such as, a
 * [__TypeKind.LIST], then it would be a TypeRef of a TypeRef. This property gets the innermost [__Type]. For example,
 * if this class represented a [__Type] of [__TypeKind.LIST] of [__TypeKind.NON_NULL] of a [__TypeKind.SCALAR] ([String!]),
 * then this [rawType] property would return the [__TypeKind.SCALAR] [__Type] (String).
 */
@Suppress("RecursivePropertyAccessor")
val __Type.rawType: __Type
    get() = ofType?.rawType ?: this
