package com.chrynan.kapi.server.core.annotation.parameter

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
annotation class Header(val value: String = "")
