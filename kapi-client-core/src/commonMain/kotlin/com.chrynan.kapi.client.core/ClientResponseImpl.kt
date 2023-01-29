@file:Suppress("unused")

package com.chrynan.kapi.client.core

import com.chrynan.kapi.core.Response
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.reflect.*

/**
 * A [Response] instance that wraps an [HttpResponse] value from Ktor, and, therefore, whose [raw] function returns an
 * [HttpResponse].
 */
interface ClientResponse<T> : Response<T> {

    override fun raw(): HttpResponse
}

/**
 * Retrieves the raw [HttpResponse] from this [Response] model if this [Response] model is a [ClientResponse] instance.
 */
fun <T> Response<T>.rawHttpResponse(): HttpResponse? =
    (this as? ClientResponse<T>)?.raw()

/**
 * Creates an instance of [Response] from the provided [httpResponse].
 */
fun <T> Response.Companion.of(
    httpResponse: HttpResponse,
    typeInfo: TypeInfo
): ClientResponse<T> =
    ClientResponseImpl(
        httpResponse = httpResponse,
        typeInfo = typeInfo
    )

/**
 * Creates an instance of [Response] from the provided [httpResponse].
 */
inline fun <reified T> Response.Companion.of(
    httpResponse: HttpResponse
): ClientResponse<T> =
    ClientResponseImpl(
        httpResponse = httpResponse,
        typeInfo = typeInfo<T>()
    )

/**
 * Creates an instance of [Response] from this [HttpResponse].
 */
fun <T> HttpResponse.toResponse(typeInfo: TypeInfo): ClientResponse<T> =
    ClientResponseImpl(
        httpResponse = this,
        typeInfo = typeInfo
    )

/**
 * Creates an instance of [Response] from this [HttpResponse].
 */
inline fun <reified T> HttpResponse.toResponse(): ClientResponse<T> =
    ClientResponseImpl(
        httpResponse = this,
        typeInfo = typeInfo<T>()
    )

/**
 * Implementation of the [ClientResponse] interface.
 */
@PublishedApi
internal class ClientResponseImpl<T> @PublishedApi internal constructor(
    private val httpResponse: HttpResponse,
    private val typeInfo: TypeInfo
) : ClientResponse<T> {

    override fun code(): Int =
        httpResponse.status.value

    override fun message(): String? =
        httpResponse.status.description.takeIf { it.isNotBlank() }

    override fun headers(): Headers =
        httpResponse.headers

    override fun raw(): HttpResponse =
        httpResponse

    override suspend fun body(): T =
        httpResponse.body(typeInfo)

    override suspend fun <E> error(typeInfo: TypeInfo): E =
        httpResponse.body(typeInfo)

    override fun isSuccessful(): Boolean =
        httpResponse.status.value in 200 until 300

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ClientResponseImpl<*>) return false

        if (httpResponse != other.httpResponse) return false
        if (typeInfo != other.typeInfo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = httpResponse.hashCode()
        result = 31 * result + typeInfo.hashCode()
        return result
    }

    override fun toString(): String =
        "ClientResponseImpl(httpResponse=$httpResponse, typeInfo=$typeInfo)"
}
