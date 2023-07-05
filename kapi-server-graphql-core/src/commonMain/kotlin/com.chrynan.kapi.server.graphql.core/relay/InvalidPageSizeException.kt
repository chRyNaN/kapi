package com.chrynan.kapi.server.graphql.core.relay

import com.chrynan.kapi.server.graphql.core.language.SourceLocation
import com.chrynan.kapi.server.graphql.core.model.ErrorType
import com.chrynan.kapi.server.graphql.core.model.GraphQLError
import com.chrynan.kapi.server.graphql.core.model.PathSegment
import kotlinx.serialization.json.JsonObject

class InvalidPageSizeException internal constructor(
    override val message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause),
    GraphQLError {

    val errorType: ErrorType
        get() = ErrorType.DataFetchingException

    override fun copy(
        message: String,
        locations: List<SourceLocation?>?,
        path: List<PathSegment?>?,
        extensions: JsonObject?
    ): GraphQLError =
        InvalidPageSizeException(
            message = message,
            cause = cause
        )
}
