package com.chrynan.kapi.server.graphql.core.introspection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.collections.List

/**
 * Defines the GraphQL Introspection Type model.
 *
 * @see [GraphQL Specification](https://spec.graphql.org/June2018/#sec-Introspection).
 */
@Serializable(with = TypeSerializer::class)
sealed class Type private constructor() {

    @SerialName(value = "kind")
    abstract val kind: TypeKind

    @SerialName(value = "name")
    abstract val name: String

    @SerialName(value = "description")
    abstract val description: String?

    @SerialName(value = "interfaces")
    abstract val interfaces: List<Type>?

    @SerialName(value = "fields")
    abstract val fields: List<Field>?

    @SerialName(value = "possibleTypes")
    abstract val possibleTypes: List<Type>?

    @SerialName(value = "enumValues")
    abstract val enumValues: List<Enum.Value>?

    @SerialName(value = "inputFields")
    abstract val inputFields: List<InputField>?

    @SerialName(value = "ofType")
    abstract val ofType: Type?

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Type) return false

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
    class Scalar(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null
    ) : Type() {

        @SerialName(value = "kind")
        override val kind: TypeKind = TypeKind.SCALAR

        @SerialName(value = "interfaces")
        override val interfaces: List<Type>? = null

        @SerialName(value = "fiends")
        override val fields: List<Field>? = null

        @SerialName(value = "possibleTypes")
        override val possibleTypes: List<Type>? = null

        @SerialName(value = "enumValues")
        override val enumValues: List<Enum.Value>? = null

        @SerialName(value = "inputFields")
        override val inputFields: List<InputField>? = null

        @SerialName(value = "ofType")
        override val ofType: Type? = null
    }

    @Serializable
    class Object(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "interfaces") override val interfaces: List<Type> = emptyList(),
        @SerialName(value = "fields") override val fields: List<Field> = emptyList()
    ) : Type() {

        @SerialName(value = "kind")
        override val kind: TypeKind = TypeKind.OBJECT

        @SerialName(value = "possibleTypes")
        override val possibleTypes: List<Type>? = null

        @SerialName(value = "enumValues")
        override val enumValues: List<Enum.Value>? = null

        @SerialName(value = "inputFields")
        override val inputFields: List<InputField>? = null

        @SerialName(value = "ofType")
        override val ofType: Type? = null
    }

    @Serializable
    class Interface(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "possibleTypes") override val possibleTypes: List<Type>? = null,
        @SerialName(value = "fields") override val fields: List<Field> = emptyList()
    ) : Type() {

        @SerialName(value = "kind")
        override val kind: TypeKind = TypeKind.INTERFACE

        @SerialName(value = "interfaces")
        override val interfaces: List<Type>? = null

        @SerialName(value = "enumValues")
        override val enumValues: List<Enum.Value>? = null

        @SerialName(value = "inputFields")
        override val inputFields: List<InputField>? = null

        @SerialName(value = "ofType")
        override val ofType: Type? = null
    }

    @Serializable
    class Union(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "possibleTypes") override val possibleTypes: List<Type>? = null
    ) : Type() {

        @SerialName(value = "kind")
        override val kind: TypeKind = TypeKind.UNION

        @SerialName(value = "interfaces")
        override val interfaces: List<Type>? = null

        @SerialName(value = "fiends")
        override val fields: List<Field>? = null

        @SerialName(value = "enumValues")
        override val enumValues: List<Enum.Value>? = null

        @SerialName(value = "inputFields")
        override val inputFields: List<InputField>? = null

        @SerialName(value = "ofType")
        override val ofType: Type? = null
    }

    @Serializable
    class Enum(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "enumValues") override val enumValues: List<Value> = emptyList()
    ) : Type() {

        @SerialName(value = "kind")
        override val kind: TypeKind = TypeKind.ENUM

        @SerialName(value = "interfaces")
        override val interfaces: List<Type>? = null

        @SerialName(value = "fiends")
        override val fields: List<Field>? = null

        @SerialName(value = "possibleTypes")
        override val possibleTypes: List<Type>? = null

        @SerialName(value = "inputFields")
        override val inputFields: List<InputField>? = null

        @SerialName(value = "ofType")
        override val ofType: Type? = null

        @Serializable
        data class Value(
            @SerialName(value = "name") val name: String,
            @SerialName(value = "description") val description: String? = null,
            @SerialName(value = "isDeprecated") val isDeprecated: Boolean = false,
            @SerialName(value = "deprecationReason") val deprecationReason: String? = null
        )
    }

    @Serializable
    class InputObject(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "inputFields") override val inputFields: List<InputField> = emptyList()
    ) : Type() {

        @SerialName(value = "kind")
        override val kind: TypeKind = TypeKind.INPUT_OBJECT

        @SerialName(value = "interfaces")
        override val interfaces: List<Type>? = null

        @SerialName(value = "fiends")
        override val fields: List<Field>? = null

        @SerialName(value = "possibleTypes")
        override val possibleTypes: List<Type>? = null

        @SerialName(value = "enumValues")
        override val enumValues: List<Enum.Value>? = null

        @SerialName(value = "ofType")
        override val ofType: Type? = null
    }

    @Serializable
    class NonNull(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "ofType") override val ofType: Type
    ) : Type() {

        @SerialName(value = "kind")
        override val kind: TypeKind = TypeKind.NON_NULL

        @SerialName(value = "interfaces")
        override val interfaces: List<Type>? = null

        @SerialName(value = "fiends")
        override val fields: List<Field>? = null

        @SerialName(value = "possibleTypes")
        override val possibleTypes: List<Type>? = null

        @SerialName(value = "enumValues")
        override val enumValues: List<Enum.Value>? = null

        @SerialName(value = "inputFields")
        override val inputFields: List<InputField>? = null
    }

    @Serializable
    class Collection(
        @SerialName(value = "name") override val name: String,
        @SerialName(value = "description") override val description: String? = null,
        @SerialName(value = "ofType") override val ofType: Type
    ) : Type() {

        @SerialName(value = "kind")
        override val kind: TypeKind = TypeKind.NON_NULL

        @SerialName(value = "interfaces")
        override val interfaces: List<Type>? = null

        @SerialName(value = "fiends")
        override val fields: List<Field>? = null

        @SerialName(value = "possibleTypes")
        override val possibleTypes: List<Type>? = null

        @SerialName(value = "enumValues")
        override val enumValues: List<Enum.Value>? = null

        @SerialName(value = "inputFields")
        override val inputFields: List<InputField>? = null
    }
}

/**
 * Retrieves the nested [Type] of this [Type]. For instance, if this is a wrapped [Type], such as, a
 * [TypeKind.LIST], then it would be a TypeRef of a TypeRef. This property gets the innermost [Type]. For example,
 * if this class represented a [Type] of [TypeKind.LIST] of [TypeKind.NON_NULL] of a [TypeKind.SCALAR] ([String!]),
 * then this [rawType] property would return the [TypeKind.SCALAR] [Type] (String).
 */
@Suppress("RecursivePropertyAccessor")
val Type.rawType: Type
    get() = ofType?.rawType ?: this

internal object TypeSerializer : KSerializer<Type> {

    override val descriptor: SerialDescriptor
        get() = TODO("Not yet implemented")

    override fun serialize(encoder: Encoder, value: Type) {
        TODO("Not yet implemented")
    }

    override fun deserialize(decoder: Decoder): Type {
        TODO("Not yet implemented")
    }
}
