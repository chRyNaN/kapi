package com.chrynan.kapi.server.core.annotation.parameter

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
annotation class Path(val value: String = "")
