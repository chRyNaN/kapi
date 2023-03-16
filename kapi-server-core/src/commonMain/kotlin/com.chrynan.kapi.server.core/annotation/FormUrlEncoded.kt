package com.chrynan.kapi.server.core.annotation

import com.chrynan.kapi.server.core.annotation.parameter.Field

/**
 * Indicates that the annotated API function's request body will use form URL encoding
 * (application/x-www-form-urlencoded). Fields should be declared as parameters and annotated with [Field].
 *
 * **Note:** That this annotation cannot be provided on an API function if the [Multipart] or [ContentNegotiation]
 * annotations are present on that same function.
 */
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
annotation class FormUrlEncoded
