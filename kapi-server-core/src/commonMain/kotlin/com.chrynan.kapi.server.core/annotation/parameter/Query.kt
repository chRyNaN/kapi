package com.chrynan.kapi.server.core.annotation.parameter

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
annotation class Query(val value: String = "")
