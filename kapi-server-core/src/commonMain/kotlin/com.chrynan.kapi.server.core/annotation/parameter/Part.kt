package com.chrynan.kapi.server.core.annotation.parameter

import com.chrynan.kapi.server.core.annotation.Multipart

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
 * [Multipart].
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
annotation class Part(
    val value: String = "",
    val encoding: String = ""
)
