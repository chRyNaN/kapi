package com.chrynan.kapi.server.core.annotation

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
 * [ContentNegotiation] or must not contain the [ApplicationFormUrlEncoded] or [MultipartFormData] annotations.
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
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@ExperimentalServerApi
annotation class Body

/**
 * The parameter of an API function annotated with [Field] represents a form url-encoded field extracted from the
 * request body of the HTTP endpoint. A [Field] parameter can be one of the following types:
 *
 * - Kotlin primitive
 * - List, Collection, or Array of type String
 *
 * **Note:** To use this annotation on a parameter in an API function, the API function has to be annotated with
 * [ApplicationFormUrlEncoded].
 *
 * **Note:** That this annotation cannot be provided on a parameter of an API function if a [Body] or [Part]
 * annotation is present on a parameter of that same API function.
 *
 * @property [value] The name of the field. If this value is blank, then the name of the parameter will be used
 * instead.
 */
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@ExperimentalServerApi
annotation class Field(val value: String = "")

/**
 * The parameter of an API function annotated with [Header] represents a header value from the HTTP request. A [Header]
 * parameter can be one of the following types:
 *
 * - Kotlin primitive
 * - List, Collection, or Array of type String
 *
 * @property [value] The name of the header. If this value is blank, then the name of the parameter will be used
 * instead.
 */
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@ExperimentalServerApi
annotation class Header(val value: String = "")

/**
 * The parameter of an API function annotated with [Part] represents a single part, or all parts, of a multipart
 * request. A [Field] parameter can be one of the following types:
 *
 * - Kotlin Primitive
 * - io.ktor.http.content.PartData(.*) (Ktor)
 * - io.ktor.http.content.MultiPartData (Ktor)
 * - java.io.InputStream
 * - io.ktor.utils.io.core.Input (Ktor)
 * - io.ktor.utils.io.ByteReadChannel (Ktor)
 * - kotlin.ByteArray
 *
 * **Note:** To use this annotation on a parameter in an API function, the API function has to be annotated with
 * [MultipartFormData].
 *
 * **Note:** That this annotation cannot be provided on a parameter of an API function if a [Field] or [Body]
 * annotation is present on a parameter of that same API function.
 *
 * @property [value] The name of the part. If this value is blank, then the name of the parameter will be used instead.
 * In the case that this represents all the parts of a multipart request, then this value can be blank as a name is not
 * needed.
 * @property [encoding] The MIME type or Content-Transfer-Encoding of this part.
 */
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@ExperimentalServerApi
annotation class Part(
    val value: String = "",
    val encoding: String = ""
)

/**
 * The parameter of an API function annotated with [Path] represents a path parameter from the URL path of the HTTP
 * endpoint. A [Path] parameter can be one of the following types:
 *
 * - Kotlin primitive
 * - List, Collection, or Array of type String
 *
 * @property [value] The name of the path. If this value is blank, then the name of the parameter will be used instead.
 */
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@ExperimentalServerApi
annotation class Path(val value: String = "")

/**
 * The parameter of an API function annotated with [Query] represents a query parameter from the URL of the HTTP
 * endpoint. A [Query] parameter can be one of the following types:
 *
 * - Kotlin primitive
 * - List, Collection, or Array of type String
 *
 * @property [value] The name of the query. If this value is blank, then the name of the parameter will be used
 * instead.
 */
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@ExperimentalServerApi
annotation class Query(val value: String = "")

/**
 * The parameter of an API function annotated with [Principal] represents an auth principal that is retrieved from the
 * HTTP request. A principal value can only be retrieved from a request if the auth has succeeded. The supported types
 * for a principal value depend on the server framework, for example, Ktor requires the type to be a subtype of
 * `io.ktor.server.auth.Principal` (which requires the Ktor plugin dependency).
 *
 * @property [value] The name of the auth provider to retrieve the principal from. If this value is blank, then the
 * name of the parameter will be used instead.
 */
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@ExperimentalServerApi
annotation class Principal(val value: String = "")
