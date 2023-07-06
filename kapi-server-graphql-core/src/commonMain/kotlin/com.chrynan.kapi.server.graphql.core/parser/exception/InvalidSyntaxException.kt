@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.parser.exception

import com.chrynan.kapi.server.graphql.core.GraphQLException
import com.chrynan.kapi.server.graphql.core.language.SourceLocation
import com.chrynan.kapi.server.graphql.core.model.InvalidSyntaxError

/**
 * This exception is thrown by the [Parser] if the graphql syntax is not valid
 */
open class InvalidSyntaxException(
    override val message: String,
    val location: SourceLocation? = null,
    val offendingToken: String? = null,
    val sourcePreview: String? = null,
    cause: Exception? = null
) : GraphQLException(cause) {

    fun toInvalidSyntaxError(): InvalidSyntaxError {
        val sourceLocations = if (location == null) null else listOf(location)

        return InvalidSyntaxError(
            locations = sourceLocations,
            message = message,
            sourcePreview = sourcePreview,
            offendingToken = offendingToken
        )
    }
}
