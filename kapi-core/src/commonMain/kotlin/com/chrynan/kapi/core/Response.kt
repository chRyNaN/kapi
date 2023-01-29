@file:Suppress("unused")

package com.chrynan.kapi.core

import io.ktor.http.*
import io.ktor.util.reflect.*

/**
 * Represents a response from an HTTP API request. This is similar to the
 * [Retrofit Response](https://github.com/square/retrofit/blob/master/retrofit/src/main/java/retrofit2/Response.java)
 * class but can wrap an HttpResponse from the Ktor library.
 */
interface Response<T> {

    /**
     * The HTTP Status code.
     */
    fun code(): Int

    /**
     * The HTTP Status message or `null` if unknown.
     */
    fun message(): String?

    /**
     * The HTTP [Headers].
     */
    fun headers(): Headers

    /**
     * The raw Http Response value that this interface wraps, or `null` if this implementation does not wrap another
     * Http Response value.
     */
    fun raw(): Any?

    /**
     * The deserialized response body of a successful HTTP request.
     */
    suspend fun body(): T

    /**
     * The deserialized response body of an unsuccessful HTTP request.
     */
    suspend fun <E> error(typeInfo: TypeInfo): E

    /**
     * Returns `true` if the [code] falls within the range 200 (inclusive) to 300 (exclusive), `false` otherwise.
     */
    fun isSuccessful(): Boolean =
        code() in 200 until 300

    companion object
}

/**
 * The deserialized response body of an unsuccessful HTTP request.
 *
 * @see [Response.error]
 */
suspend inline fun <T, reified E> Response<T>.error(): E = error(typeInfo = typeInfo<E>())

/**
 * Retrieves the [Response.body] model or `null` if an exception was thrown.
 */
suspend fun <T> Response<T>.bodyOrNull(): T? =
    try {
        body()
    } catch (_: Exception) {
        null
    }

/**
 * Retrieves the [error] model or `null` if there was no [error].
 */
suspend fun <T, E> Response<T>.errorOrNull(typeInfo: TypeInfo): E? =
    try {
        error(typeInfo = typeInfo)
    } catch (_: Exception) {
        null
    }

/**
 * Retrieves the [error] model or `null` if there was no [error].
 */
suspend inline fun <T, reified E> Response<T>.errorOrNull(): E? =
    try {
        error(typeInfo = typeInfo<E>())
    } catch (_: Exception) {
        null
    }
