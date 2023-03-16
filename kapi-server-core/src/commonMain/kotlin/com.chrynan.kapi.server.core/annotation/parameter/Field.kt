package com.chrynan.kapi.server.core.annotation.parameter

import com.chrynan.kapi.server.core.annotation.FormUrlEncoded

/**
 * The parameter of an API function annotated with [Field] represents a form url-encoded field extracted from the
 * request body of the HTTP endpoint. A [Field] parameter can be one of the following types:
 *
 * - Kotlin primitive
 * - List, Collection, or Array of type String
 *
 * **Note:** To use this annotation on a parameter in an API function, the API function has to be annotated with
 * [FormUrlEncoded].
 *
 * **Note:** That this annotation cannot be provided on a parameter of an API function if a [Body] or [Part]
 * annotation is present on a parameter of that same API function.
 *
 * @property [value] The name of the field. If this value is blank, then the name of the parameter will be used
 * instead.
 */
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Field(val value: String = "")
