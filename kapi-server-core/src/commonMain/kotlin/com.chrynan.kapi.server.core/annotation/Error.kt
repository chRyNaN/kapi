@file:Suppress("unused")

package com.chrynan.kapi.server.core.annotation

import kotlin.reflect.KClass
import com.chrynan.kapi.core.ApiError

/**
 * Represents errors that can be thrown from an API function.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
annotation class Errors(vararg val errors: Error<*> = [])

/**
 * Represents an error that can be thrown from an API function. When an error with the provided [exception] [KClass] is
 * thrown from the API function, it should be caught and result in an HTTP response with an [ApiError] response body
 * with the provided values and the provided HTTP [statusCode].
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
 * value will be `null`.
 * @property [instance] Corresponds to the [ApiError.instance] property. When this value is blank, the
 * [ApiError.instance] value will be `null`.
 * @property [help] Corresponds to the [ApiError.help] property. When this value is blank, the [ApiError.help] value
 * will be `null`.
 */
@MustBeDocumented
@Target()
annotation class Error<T : Throwable>(
    val statusCode: Int,
    val exception: KClass<T>,
    val type: String = "about:blank",
    val title: String = "",
    val details: String = "",
    val instance: String = "",
    val help: String = ""
)
