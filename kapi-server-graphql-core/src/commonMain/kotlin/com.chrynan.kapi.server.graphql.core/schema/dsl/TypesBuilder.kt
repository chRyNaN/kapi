package com.chrynan.kapi.server.graphql.core.schema.dsl

import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class TypesBuilder internal constructor(
    val serializersModule: SerializersModule
) {

    @Suppress("UNCHECKED_CAST")
    fun <T> type(
        name: String? = null,
        kType: KType,
        serializer: KSerializer<T> = serializersModule.serializer(kType) as KSerializer<T>,
        builder: TypeBuilder.() -> Unit = {}
    ) {

    }
}

inline fun <reified T> TypesBuilder.type(
    name: String? = null,
    serializer: KSerializer<T> = serializersModule.serializer<T>(),
    noinline builder: TypeBuilder.() -> Unit = {}
) = type(
    name = name,
    kType = typeOf<T>(),
    serializer = serializer,
    builder = builder
)
