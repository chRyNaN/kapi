@file:Suppress("unused")

package com.chrynan.kapi.server.core.annotation

import io.ktor.http.*

/**
 * Defines the content types that an API function can accept for the request body. This annotation can be applied
 * directly to an API function to indicate which content types that function accepts for the request body, like in the
 * following example:
 *
 * ```kotlin
 * @Consumes("application/x-www-form-urlencoded")
 * suspend fun getItem(@Field id: String): Item
 * ```
 *
 * Or, this annotation can be applied to another annotation class definition to indicate that any function with that
 * annotation accepts those specified types for the request body. For example:
 *
 * ```kotlin
 * @Consumes("application/x-www-form-urlencoded")
 * @Target(AnnotationTarget.FUNCTION)
 * annotation class FormUrlEncoded
 *
 * @Api
 * interface ItemApi {
 *
 *     @FormUrlEncoded
 *     suspend fun getItem(@Field id: String): Item
 * }
 * ```
 *
 * Both of the approaches illustrated above are functionally equivalent.
 *
 * **Note:** That only a single [Consumes] annotation, or an annotation annotated with [Consumes], is allowed on an API
 * function. Multiple of these annotations for a single API function will result in a compile-time error.
 *
 * **Note:** That a [Body] parameter for an API function must be a supported type that works with the specified
 * [contentType].
 *
 * @property [contentType] The supported content type accepted by an API function. The content type specified must be
 * a single non-blank valid value. A blank content type provided results in a default value of `&#42;/&#42;`, which
 * means it relies on the server framework's content negotiation support (which may allow any type or specific types).
 * However, since the server framework's content negotiation support is separate from this library, it will be
 * registered in any generated documentation as supporting any type.
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@MustBeDocumented
annotation class Consumes(val contentType: String)

/**
 * Indicates that the annotated API function's request body will use content negotiation that should already be set up
 * with the server framework. Request body fields should be a single parameter annotated with [Body]  of a serializable
 * type.
 */
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
@Consumes(contentType = "")
annotation class ContentNegotiation

/**
 * Indicates that the annotated API function's request body will use form URL encoding
 * (application/x-www-form-urlencoded). Fields should be declared as parameters and annotated with [Field].
 */
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
@Consumes(contentType = "application/x-www-form-urlencoded")
annotation class ApplicationFormUrlEncoded

/**
 * Indicates that the annotated API function's request body will use multipart form data encoding
 * (multipart/form-data). Fields should be declared as parameters and annotated with [Part].
 */
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
@Consumes(contentType = "multipart/form-data")
annotation class MultipartFormData

/**
 * Indicates that the annotated API function's request body will use JSON encoding (application/json). Request body
 * fields should be a single parameter annotated with [Body] of a serializable type.
 */
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
@Consumes(contentType = "application/json")
annotation class ApplicationJson
