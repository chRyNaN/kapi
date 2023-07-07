@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core

import com.chrynan.kapi.server.graphql.core.introspection.__Type
import com.chrynan.kapi.server.graphql.core.language.Arguments
import com.chrynan.kapi.server.graphql.core.language.LiteralValue
import com.chrynan.kapi.server.graphql.core.language.TypeDefinition
import graphql.execution.MergedField
import graphql.language.SelectionSet
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface Type<T> {

    val name: String
    val introspection: __Type
    val definition: TypeDefinition
    val kotlin: KType
    val serializer: KSerializer<T>
    val serializersModule: SerializersModule
}

interface Value<T> {

    val type: Type<T>
    val value: T
}

interface Resolver<T> {

    suspend fun resolve(arguments: Map<String, LiteralValue>): T
}

interface ResolvedType<T> {

    val value: T

    val kType: KType

    val serializer: KSerializer<T>

    val serializersModule: SerializersModule

    suspend fun getField(name: String, arguments: Map<String, LiteralValue>): ResolvedType<*>
}

@Suppress("UNCHECKED_CAST")
suspend inline fun <T, reified R> ResolvedType<T>.getTypedField(
    name: String,
    arguments: Arguments
): ResolvedType<R> =
    getField(name = name, arguments = arguments) as ResolvedType<R>

suspend fun <T> ResolvedType<T>.execute(
    json: Json,
    selectionSet: SelectionSet
): JsonElement {
    // TODO: Convert the SelectionSet into the groupedFieldSet.
    // Refer to the following: https://spec.graphql.org/June2018/#sec-Executing-Selection-Sets
    val groupedFieldSet: Map<String, List<MergedField>> = emptyMap()

    buildJsonObject { }
    groupedFieldSet.values.flatten()
        .forEach { field ->
            val resolvedType = this.getField(name = field.name, arguments = Arguments()) // TODO: Handle arguments

            val jsonElement = if (field.singleField.selectionSet.selections.isNotEmpty()) {
                resolvedType.execute(
                    json = json,
                    selectionSet = field.singleField.selectionSet
                )
            } else {
                json.encodeToJsonElement(value = resolvedType)
            }
        }

    TODO()
}

abstract class ResolvedTypeImpl<T> @PublishedApi internal constructor(
    override val value: T,
    override val kType: KType,
    override val serializer: KSerializer<T>,
    override val serializersModule: SerializersModule,
    private val getField: suspend (name: String, arguments: Arguments) -> ResolvedType<*>
) : ResolvedType<T> {

    override suspend fun getField(name: String, arguments: Arguments): ResolvedType<*> =
        getField.invoke(name, arguments)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ResolvedTypeImpl<*>) return false

        if (value != other.value) return false
        if (kType != other.kType) return false
        if (serializer != other.serializer) return false
        if (serializersModule != other.serializersModule) return false

        return getField == other.getField
    }

    override fun hashCode(): Int {
        var result = value?.hashCode() ?: 0
        result = 31 * result + kType.hashCode()
        result = 31 * result + serializer.hashCode()
        result = 31 * result + serializersModule.hashCode()
        result = 31 * result + getField.hashCode()
        return result
    }

    override fun toString(): String =
        "ResolvedTypeImpl(value=$value, kType=$kType, serializer=$serializer, serializersModule=$serializersModule, getField=$getField)"
}

@Suppress("FunctionName")
inline fun <reified T> ResolvedType(
    value: T,
    kType: KType = typeOf<T>(),
    serializersModule: SerializersModule = EmptySerializersModule(),
    serializer: KSerializer<T> = serializersModule.serializer(),
    noinline getField: suspend (name: String, arguments: Arguments) -> ResolvedType<*>
) = object : ResolvedTypeImpl<T>(
    value = value,
    kType = kType,
    serializersModule = serializersModule,
    serializer = serializer,
    getField = getField
) {}

@Suppress("FunctionName")
inline fun <reified T> ResolvedScalarType(
    value: T,
    kType: KType = typeOf<T>(),
    serializersModule: SerializersModule = EmptySerializersModule(),
    serializer: KSerializer<T> = serializersModule.serializer()
) = ResolvedType(
    value = value,
    kType = kType,
    serializersModule = serializersModule,
    serializer = serializer,
    getField = { _, _ -> error("Scalar types do not have fields.") }
)

@Suppress("FunctionName")
inline fun <reified T> ResolvedEnumType(
    value: T,
    kType: KType = typeOf<T>(),
    serializersModule: SerializersModule = EmptySerializersModule(),
    serializer: KSerializer<T> = serializersModule.serializer()
) = ResolvedType(
    value = value,
    kType = kType,
    serializersModule = serializersModule,
    serializer = serializer,
    getField = { _, _ -> error("Enum types do not have fields.") }
)

@Suppress("FunctionName")
inline fun <reified T> ResolvedSchemaType(
    value: T,
    kType: KType = typeOf<T>(),
    serializersModule: SerializersModule = EmptySerializersModule(),
    serializer: KSerializer<T> = serializersModule.serializer(),
    crossinline query: suspend () -> ResolvedType<*>,
    noinline mutation: (suspend () -> ResolvedType<*>)? = null,
    noinline subscription: (suspend () -> ResolvedType<*>)? = null,
) = ResolvedType(
    value = value,
    kType = kType,
    serializersModule = serializersModule,
    serializer = serializer,
    getField = { name, _ ->
        when (name) {
            "query" -> query.invoke()
            "mutation" -> mutation?.invoke() ?: error("No mutation field is available on this GraphQL Schema.")
            "subscription" -> subscription?.invoke()
                ?: error("No subscription field is available on this GraphQL Schema.")

            else -> error("Unsupported GraphQL Schema field.")
        }
    }
)