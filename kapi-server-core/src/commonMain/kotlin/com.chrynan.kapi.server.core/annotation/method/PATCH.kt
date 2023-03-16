package com.chrynan.kapi.server.core.annotation.method

import com.chrynan.kapi.server.core.annotation.Api
import com.chrynan.kapi.server.core.annotation.parameter.Path

/**
 * This annotation is provided on a function that represents an API function that responds to HTTP PATCH requests. An
 * API function can only be annotated with one HTTP method annotation.
 *
 * ## Example
 *
 * ```kotlin
 * @PATCH("/user")
 * suspend fun updateUser(@Body user: User)
 * ```
 *
 * @property [path] The relative or absolute URL path of the API endpoint that the annotated function responds to. The
 * [Api.basePath] value from the containing component with the [Api] annotation will be prepended to this value to make
 * the full path, if it is not blank. A value for this property must be provided, but it may be blank, which indicates
 * that the path is the [Api.basePath] value or the root path of the server. The code generation tools should
 * appropriately handle leading and trailing forward slash characters ("/") when creating the endpoints, so a preceding
 * "/" character in the [path] value should be equivalent to no preceding slash character, and likewise for trailing
 * slash characters. A blank value and a value containing only a forward slash character are considered equivalent.
 * Path values may contain variables which can be extracted and provided to the API function as a parameter using the
 * [Path] annotation. A path variable is surrounded by a leading "{" character and a trailing "}" character. For
 * example, the following path value contains a path parameter with a name of "id": "/user/{id}".
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PATCH(val path: String)
