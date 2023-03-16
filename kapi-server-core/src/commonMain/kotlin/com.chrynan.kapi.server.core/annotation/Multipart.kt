package com.chrynan.kapi.server.core.annotation

import com.chrynan.kapi.server.core.annotation.parameter.Part

/**
 * Indicates that the annotated API function's request body will use multipart form data encoding
 * (multipart/form-data). Fields should be declared as parameters and annotated with [Part].
 *
 * **Note:** That this annotation cannot be provided on an API function if the [FormUrlEncoded] or [ContentNegotiation]
 * annotations are present on that same function.
 */
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
annotation class Multipart
