@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core

/**
 * The base exception class for all GraphQL related exceptions.
 */
open class GraphQLException : RuntimeException {

    constructor()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}
