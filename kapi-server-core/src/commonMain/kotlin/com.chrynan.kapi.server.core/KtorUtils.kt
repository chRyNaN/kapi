package com.chrynan.kapi.server.core

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.plugins.*
import io.ktor.server.util.*
import io.ktor.util.*
import io.ktor.util.converters.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.core.*
import io.ktor.utils.io.jvm.javaio.*

/**
 * Retrieves the parameters value associated with this [name] converting to type [R] using [DefaultConversionService]
 * or `null` if the value cannot be converted or there is no value associated with [name].
 */
@Suppress("unused")
inline fun <reified R : Any> Parameters.getOrNull(name: String): R? =
    try {
        getOrFail<R>(name = name)
    } catch (_: Exception) {
        null
    }

/**
 * Get headers value associated with this [name] converting to type [R] using [DefaultConversionService]
 * or fail with [MissingRequestParameterException]
 *
 * @throws MissingRequestParameterException if no values associated with this [name]
 * @throws ParameterConversionException when conversion from String to [R] fails
 */
@Suppress("KotlinRedundantDiagnosticSuppress")
inline fun <reified R : Any> Headers.getOrFail(name: String): R {
    val values = getAll(name) ?: throw MissingRequestParameterException(name)
    val typeInfo = typeInfo<R>()

    return try {
        @Suppress("UNCHECKED_CAST")
        DefaultConversionService.fromValues(values, typeInfo) as R
    } catch (cause: Exception) {
        throw ParameterConversionException(name, typeInfo.type.simpleName ?: typeInfo.type.toString(), cause)
    }
}

/**
 * Retrieves the headers value associated with this [name] converting to type [R] using [DefaultConversionService]
 * or `null` if the value cannot be converted or there is no value associated with [name].
 */
@Suppress("unused")
inline fun <reified R : Any> Headers.getOrNull(name: String): R? =
    try {
        getOrFail(name = name)
    } catch (_: Exception) {
        null
    }

/**
 * Converts this [PartData] into a [ByteArray] if possible. If this is a [PartData.FormItem] instance, then an
 * exception will be thrown as that cannot be converted into an [ByteArray].
 */
@Suppress("unused")
suspend fun PartData.asByteArray(): ByteArray =
    when (this) {
        is PartData.FormItem -> error("A PartData.FormItem does not have streaming content and cannot be converted to an Input.")
        is PartData.FileItem -> this.provider.invoke().readBytes()
        is PartData.BinaryItem -> this.provider.invoke().readBytes()
        is PartData.BinaryChannelItem -> this.provider.invoke().toByteArray()
    }
