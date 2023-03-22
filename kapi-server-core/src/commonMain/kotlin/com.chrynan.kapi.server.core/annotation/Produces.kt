@file:Suppress("unused")

package com.chrynan.kapi.server.core.annotation

import kotlin.reflect.KClass
import com.chrynan.kapi.core.ApiError
import com.chrynan.kapi.core.Response

/**
 * Represents information about the possible HTTP responses of an API function.
 *
 * @property [success] Represents the successful HTTP response from this API function. This will be used in conjunction
 * with the API function's return type to determine the HTTP response of the API function. The default value is an
 * empty [Success] instance with a [Success.statusCode] value of `200`.
 * @property [errors] Represents the possible HTTP error responses from this API function. When an [Error.exception] is
 * thrown from the API function, it is caught and an [ApiError] will be emitted as the HTTP response, using the values
 * provided in the [Error] instance.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
annotation class Produces(
    val success: Success = Success(),
    val errors: Array<Error<*>> = []
)

/**
 * Represents a successful response from an API function. The HTTP response body is defined by the return type of the
 * API function, but the [contentType] can be
 *
 * @property [statusCode] The HTTP status code value to respond with when this successful response is returned.
 * Defaults to 200 OK. If the return type of the API function is [Response], then the [Response.code] value will
 * override this value. It is important to keep these values in sync with each other as the value specified here will
 * be used when generating documentation.
 * @property [contentType] The supported content type returned by an API function. The content type specified must be a
 * single non-blank valid value. A blank content type provided results in a default value of `&#42;/&#42;`, which means
 * it relies on the server framework's content negotiation support (which may allow any type or specific types).
 * However, since the server framework's content negotiation support is separate from this library, it will be
 * registered in any generated documentation as supporting any type.
 * @property [description] The description of this response. If this is blank, a default description will be added to
 * the generated documentation based on the [statusCode] value.
 * @property [headers] The [ResponseHeader]s that are appended to this response.
 */
@Target()
@MustBeDocumented
annotation class Success(
    val statusCode: Int = 200,
    val contentType: String = "",
    val description: String = "",
    val headers: Array<ResponseHeader> = []
)

/**
 * Represents an error response from an API function that occurs when an exception is thrown from that function. When
 * an error with the provided [exception] [KClass] is thrown from the API function, it should be caught and result in
 * an HTTP response with an [ApiError] response body with the provided values and the provided HTTP [statusCode].
 *
 * @property [statusCode] The HTTP status code value to respond with when an exception with type [exception] [KClass]
 * is thrown from the API function. This value is also used for the [ApiError.status] value.
 * @property [exception] The exception [KClass] that when thrown from the API function results in an [ApiError] HTTP
 * response with the provided values of this annotation class.
 * @property [type] Corresponds to the [ApiError.type] property.
 * @property [title] Corresponds to the [ApiError.title] property. When this value is blank, the thrown exceptions
 * message property will be used for the [ApiError.title] value. If that is also null, then the [ApiError.title] will
 * be an empty [String].
 * @property [details] Corresponds to the [ApiError.details] property. When this value is blank, the [ApiError.details]
 * value will be `null`. This is also considered the description of the response and when this value is blank, a
 * default description will be added to the generated documentation based on the [statusCode] value.
 * @property [instance] Corresponds to the [ApiError.instance] property. When this value is blank, the
 * [ApiError.instance] value will be `null`.
 * @property [help] Corresponds to the [ApiError.help] property. When this value is blank, the [ApiError.help] value
 * will be `null`.
 * @property [headers] The [ResponseHeader]s that are appended to this response.
 */
@Target()
@MustBeDocumented
annotation class Error<T : Throwable>(
    val statusCode: Int,
    val exception: KClass<T>,
    val type: String = "about:blank",
    val title: String = "",
    val details: String = "",
    val instance: String = "",
    val help: String = "",
    val headers: Array<ResponseHeader> = []
)

/**
 * Represents a single HTTP header that can be appended to the response of an API function's HTTP response.
 *
 * @property [name] The name of the header. This value must not be blank.
 * @property [value] The value of the header. This value must not be blank.
 * @property [safeOnly] Prevents from setting unsafe headers; `true` by default.
 * @property [onlyIfAbsent] If this is `true`, this header is only appended to the response if there is no header with
 * this [name] already present; `false` by default.
 */
@Target()
@MustBeDocumented
annotation class ResponseHeader(
    val name: String,
    val value: String,
    val safeOnly: Boolean = true,
    val onlyIfAbsent: Boolean = false
)
