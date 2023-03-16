@file:Suppress("unused")

package com.chrynan.kapi.server.core.annotation

/**
 * Represents HTTP headers that are appended to the response of an API function's HTTP response.
 *
 * @property [values] The [ResponseHeader]s that are appended to the response of the annotated API function.
 */
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
annotation class ResponseHeaders(vararg val values: ResponseHeader = [])

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
