package com.chrynan.kapi.server.core.annotation

import com.chrynan.kapi.server.core.annotation.parameter.Body

/**
 * Indicates that the annotated API function's request body will use content negotiation that should already be set up
 * with the server framework. Request body fields should be a single parameter annotated with [Body].
 *
 * **Note:** That this annotation cannot be provided on an API function if the [Multipart] or [FormUrlEncoded]
 * annotations are present on that same function.
 *
 * @property [value] The content mime type. This value is optional as the content negotiation has to be established
 * with the server framework, for instance, with Ktor, the content negotiation plugin will have to be setup. If this
 * value is blank, it is considered `null`.
 */
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
annotation class ContentNegotiation(val value: String = "")
