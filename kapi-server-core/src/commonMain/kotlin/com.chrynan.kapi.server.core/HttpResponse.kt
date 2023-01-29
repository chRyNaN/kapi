@file:Suppress("unused")

package com.chrynan.kapi.server.core

import com.chrynan.kapi.core.Response
import io.ktor.http.*
import io.ktor.util.reflect.*

/**
 * A wrapper around the data associated with an HTTP response.
 */
sealed class HttpResponseData<T> private constructor() {

    abstract val statusCode: Int
    abstract val message: String?

    /**
     * The data associated with a successfully HTTP response.
     */
    data class Success<T> @PublishedApi internal constructor(
        override val statusCode: Int,
        override val message: String? = null,
        val body: T
    ) : HttpResponseData<T>()

    /**
     * The data associated with an error HTTP response.
     */
    data class Error<T, E> @PublishedApi internal constructor(
        override val statusCode: Int,
        override val message: String? = null,
        val error: E? = null
    ) : HttpResponseData<T>()
}

/**
 * A [Response] instance that wraps a [HttpResponseData] model.
 */
interface HttpResponse<T> : Response<T> {

    override fun raw(): HttpResponseData<T>
}

/**
 * Retrieves the raw [HttpResponseData] from this [Response] model if this [Response] model is a [HttpResponse]
 * instance.
 */
fun <T> Response<T>.rawHttpResponseData(): HttpResponseData<T>? =
    (this as? HttpResponse<T>)?.raw()

/**
 * Creates an instance of [HttpResponse] for a success from the provided values.
 */
inline fun <reified T> Response.Companion.success(
    statusCode: Int,
    message: String? = null,
    body: T,
    headers: Headers = Headers.Empty
): HttpResponse<T> = HttpResponseImpl(
    headers = headers,
    data = HttpResponseData.Success(
        statusCode = statusCode,
        message = message,
        body = body
    )
)

/**
 * Creates an instance of [HttpResponse] for an error from the provided values.
 */
inline fun <reified T> Response.Companion.error(
    statusCode: Int,
    message: String? = null,
    error: Any,
    headers: Headers = Headers.Empty
): HttpResponse<T> = HttpResponseImpl(
    headers = headers,
    data = HttpResponseData.Error(
        statusCode = statusCode,
        message = message,
        error = error
    )
)

/**
 * Implementation of the [HttpResponse] interface.
 */
@PublishedApi
internal class HttpResponseImpl<T> @PublishedApi internal constructor(
    private val headers: Headers = Headers.Empty,
    private val data: HttpResponseData<T>
) : HttpResponse<T> {

    override fun raw(): HttpResponseData<T> = data

    override fun code(): Int =
        data.statusCode

    override fun message(): String? =
        data.message

    override fun headers(): Headers = headers

    override suspend fun body(): T =
        (data as HttpResponseData.Success).body

    @Suppress("UNCHECKED_CAST")
    override suspend fun <E> error(typeInfo: TypeInfo): E =
        (data as HttpResponseData.Error<*, *>).error as E

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HttpResponseImpl<*>) return false

        if (headers != other.headers) return false
        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        var result = headers.hashCode()

        result = 31 * result + data.hashCode()

        return result
    }

    override fun toString(): String =
        "HttpResponseImpl(headers=$headers, data=$data)"
}
