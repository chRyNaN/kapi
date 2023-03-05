@file:Suppress("unused")

package com.chrynan.kapi.server.core

import io.ktor.http.content.*
import io.ktor.util.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import io.ktor.utils.io.core.internal.*
import io.ktor.utils.io.jvm.javaio.*
import io.ktor.utils.io.pool.*
import io.ktor.utils.io.streams.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.io.InputStream
import kotlin.coroutines.CoroutineContext

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
