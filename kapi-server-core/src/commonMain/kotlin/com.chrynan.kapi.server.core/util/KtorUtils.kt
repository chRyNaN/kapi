@file:Suppress("unused")

package com.chrynan.kapi.server.core.util

import com.chrynan.kapi.core.ApiError
import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.util.*
import io.ktor.util.converters.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import io.ktor.utils.io.core.ChunkBuffer
import io.ktor.utils.io.core.internal.*
import io.ktor.utils.io.jvm.javaio.*
import io.ktor.utils.io.pool.*
import io.ktor.utils.io.streams.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.io.InputStream
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

/**
 * Get headers value associated with this [name] converting to type [R] using the provided [conversionService] or fail
 * with [MissingRequestParameterException] if the parameter is not present or [ParameterConversionException] if the
 * parameter could not be converted to the expected type.
 *
 * @throws MissingRequestParameterException if no values associated with this [name]
 * @throws ParameterConversionException when conversion from String to [R] fails
 */
@ExperimentalServerApi
@Suppress("KotlinRedundantDiagnosticSuppress")
inline fun <reified R : Any> StringValues.getOrFail(
    name: String,
    conversionService: ConversionService = DefaultConversionService
): R = getOrFail(name = name, typeInfo = typeInfo<R>(), conversionService = conversionService)

/**
 * Retrieves the headers value associated with this [name] converting to type [R] using the provided
 * [conversionService] or `null` if there is no value associated with [name]. Note that this function can still throw
 * an exception if the parameter value cannot be converted to the expected type.
 */
@ExperimentalServerApi
@Suppress("unused")
inline fun <reified R : Any> StringValues.getOrNull(
    name: String,
    conversionService: ConversionService = DefaultConversionService
): R? = getOrNull(name = name, typeInfo = typeInfo<R>(), conversionService = conversionService)

/**
 * Get headers value associated with this [name] converting to type [R] using the provided [conversionService] or fail
 * with [MissingRequestParameterException] if the parameter is not present or [ParameterConversionException] if the
 * parameter could not be converted to the expected type.
 *
 * @throws MissingRequestParameterException if no values associated with this [name]
 * @throws ParameterConversionException when conversion from String to [R] fails
 */
@ExperimentalServerApi
@Suppress("KotlinRedundantDiagnosticSuppress")
fun <R : Any> StringValues.getOrFail(
    name: String,
    typeInfo: TypeInfo,
    conversionService: ConversionService = DefaultConversionService
): R {
    val values = getAll(name) ?: throw MissingRequestParameterException(name)

    return try {
        @Suppress("UNCHECKED_CAST")
        conversionService.fromValues(values, typeInfo) as R
    } catch (cause: Exception) {
        throw ParameterConversionException(name, typeInfo.type.simpleName ?: typeInfo.type.toString(), cause)
    }
}

/**
 * Retrieves the headers value associated with this [name] converting to type [R] using the provided
 * [conversionService] or `null` if there is no value associated with [name]. Note that this function can still throw
 * an exception if the parameter value cannot be converted to the expected type.
 */
@ExperimentalServerApi
@Suppress("unused")
fun <R : Any> StringValues.getOrNull(
    name: String,
    typeInfo: TypeInfo,
    conversionService: ConversionService = DefaultConversionService
): R? =
    try {
        getOrFail(name = name, typeInfo = typeInfo, conversionService = conversionService)
    } catch (_: MissingRequestParameterException) {
        null
    }

/**
 * Retrieves an authenticated [Principal] for `this` call.
 */
fun <P : Principal> ApplicationCall.principal(typeInfo: TypeInfo): P? =
    principal(provider = null, typeInfo = typeInfo)

/**
 * Retrieves an authenticated [Principal] for `this` call from provider with name [provider]
 */
@Suppress("UNCHECKED_CAST")
fun <P : Principal> ApplicationCall.principal(provider: String?, typeInfo: TypeInfo): P? =
    authentication.principal(provider = provider, klass = typeInfo.type as KClass<P>)

/**
 * Converts this [PartData] into a [ByteArray] if possible. If this is a [PartData.FormItem] instance, then an
 * exception will be thrown as that cannot be converted into an [ByteArray].
 */
@ExperimentalServerApi
@Suppress("unused")
suspend fun PartData.asByteArray(): ByteArray =
    when (this) {
        is PartData.FormItem -> error("A PartData.FormItem does not have streaming content and cannot be converted to an Input.")
        is PartData.FileItem -> this.provider.invoke().readBytes()
        is PartData.BinaryItem -> this.provider.invoke().readBytes()
        is PartData.BinaryChannelItem -> this.provider.invoke().toByteArray()
    }

/**
 * Responds with the provided [error] applying the [ApiError.status] code if it is not `null`.
 */
@ExperimentalServerApi
suspend fun ApplicationCall.respondError(error: ApiError) {
    error.status?.let { statusCode ->
        this.respond(status = HttpStatusCode.fromValue(statusCode), message = error)
    } ?: this.respond(message = error)
}

/**
 * Converts this [PartData] into an [InputStream] if possible. If this is a [PartData.FormItem] instance, then an
 * exception will be thrown as that cannot be converted into an [InputStream].
 *
 * @param [parent] Provided to the [ByteReadChannel.toInputStream] function if this is a [PartData.BinaryChannelItem]
 * instance.
 */
fun PartData.asInputStream(parent: Job? = null): InputStream =
    when (this) {
        is PartData.FormItem -> error("A PartData.FormItem does not have streaming content and cannot be converted to an InputStream.")
        is PartData.FileItem -> this.provider.invoke().asStream()
        is PartData.BinaryItem -> this.provider.invoke().asStream()
        is PartData.BinaryChannelItem -> this.provider.invoke().toInputStream(parent = parent)
    }

/**
 * Converts this [PartData] into an [Input] if possible. If this is a [PartData.FormItem] instance, then an
 * exception will be thrown as that cannot be converted into an [Input].
 *
 * @param [parent] Provided to the [ByteReadChannel.toInputStream] function if this is a [PartData.BinaryChannelItem]
 * instance.
 * @param [pool] Provided to the [InputStream.asInput] function if this is a [PartData.BinaryChannelItem] instance.
 */
fun PartData.asInput(parent: Job? = null, pool: ObjectPool<ChunkBuffer> = ChunkBuffer.Pool): Input =
    when (this) {
        is PartData.FormItem -> error("A PartData.FormItem does not have streaming content and cannot be converted to an Input.")
        is PartData.FileItem -> this.provider.invoke()
        is PartData.BinaryItem -> this.provider.invoke()
        is PartData.BinaryChannelItem -> this.provider.invoke().toInputStream(parent = parent).asInput(pool = pool)
    }

/**
 * Converts this [PartData] into a [ByteReadChannel] if possible. If this is a [PartData.FormItem] instance, then an
 * exception will be thrown as that cannot be converted into an [ByteReadChannel].
 *
 * @param [context] Provided to the [InputStream.toByteReadChannel] function if this is a [PartData.FileItem] or
 * [PartData.BinaryItem] instance.
 * @param [pool] Provided to the [InputStream.toByteReadChannel] function if this is a [PartData.FileItem] or
 * [PartData.BinaryItem] instance.
 */
fun PartData.asByteReadChannel(
    context: CoroutineContext = Dispatchers.IO,
    pool: ObjectPool<ByteArray> = ByteArrayPool
): ByteReadChannel =
    when (this) {
        is PartData.FormItem -> error("A PartData.FormItem does not have streaming content and cannot be converted to an Input.")
        is PartData.FileItem -> this.provider.invoke().asStream().toByteReadChannel(context = context, pool = pool)
        is PartData.BinaryItem -> this.provider.invoke().asStream().toByteReadChannel(context = context, pool = pool)
        is PartData.BinaryChannelItem -> this.provider.invoke()
    }
