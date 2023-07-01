package com.chrynan.kapi.server.graphql.core.model

import com.chrynan.kapi.server.graphql.core.language.Arguments

class GraphQLEnvironment(
    val arguments: Arguments,
    val context: GraphQLContext,
    val source: Any? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GraphQLEnvironment) return false

        if (arguments != other.arguments) return false
        if (context != other.context) return false

        return source == other.source
    }

    override fun hashCode(): Int {
        var result = arguments.hashCode()
        result = 31 * result + context.hashCode()
        result = 31 * result + (source?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        "Environment(arguments=$arguments, context=$context, source=$source)"
}
