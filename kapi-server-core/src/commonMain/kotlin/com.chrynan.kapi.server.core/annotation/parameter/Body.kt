package com.chrynan.kapi.server.core.annotation.parameter

import com.chrynan.kapi.server.core.annotation.*

/**
 * The parameter of an API function annotated with [Body] represents the request body of the HTTP endpoint. A [Body]
 * parameter can be of any type, but the deserialization approach must first be established with the server framework,
 * for instance, with Ktor, the content negotiation plugin will have to be applied. Otherwise, some types from the
 * server framework may be natively supported, for instance, with Ktor, the following types are natively supported:
 *
 * - String
 * - io.ktor.utils.io.ByteReadChannel
 * - java.io.InputStream
 * - io.ktor.http.content.MultiPartData
 * - io.ktor.http.Parameters
 *
 * **Note:** To use this annotation on a parameter in an API function, the API function has to be annotated with
 * [ContentNegotiation] or must not contain the [FormUrlEncoded] or [Multipart] annotations.
 *
 * **Note:** That only one [Body] parameter is allowed per API function.
 *
 * **Note:** That not all HTTP methods support request bodies; some HTTP methods explicitly forbid it, such as the
 * TRACE HTTP method, while others discourage its use, such as GET HTTP method. It is up to the server framework, like
 * Ktor, on whether a request body is permitted for a particular HTTP function. Consult the server framework's
 * documentation on whether an HTTP method supports a request body.
 *
 * **Note:** That this annotation cannot be provided on a parameter of an API function if a [Field] or [Part]
 * annotation is present on a parameter of that same API function.
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
annotation class Body
