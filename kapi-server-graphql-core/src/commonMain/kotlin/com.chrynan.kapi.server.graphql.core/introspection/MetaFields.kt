package com.chrynan.kapi.server.graphql.core.introspection

/**
 * Represents metadata fields used for introspection on a GraphQL Schema and Types. These fields should be accessible
 * from the root query object.
 *
 * @see [Specification](https://spec.graphql.org/June2018/#sec-Schema-Introspection)
 */
@Suppress("PropertyName", "FunctionName")
interface MetaFields {

    val __schema: __Schema

    fun __type(name: String): __Type?
}
