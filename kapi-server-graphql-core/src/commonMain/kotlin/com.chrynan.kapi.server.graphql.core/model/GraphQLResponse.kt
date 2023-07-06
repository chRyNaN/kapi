package com.chrynan.kapi.server.graphql.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * The result of executing a GraphQL Operation.
 *
 * @property [errors] The set of errors that occurred during the GraphQL Operation. If the [errors] is
 * [List.isNullOrEmpty], then there was no encountered errors. @see [Specification](https://spec.graphql.org/June2018/#sec-Errors)
 * @property [data] The resulting data from the GraphQL Operation. Note that the resulting data could be expected to be
 * `null` in some cases or if an error occurs. @see [Specification](https://spec.graphql.org/June2018/#sec-Data)
 * @property [extensions] Optional extensions provided by the GraphQL execution server.
 *
 * @see [GraphQL Specification](https://spec.graphql.org/June2018/#sec-Response)
 */
@Serializable
class GraphQLResponse<T : Any?>(
    @SerialName(value = "errors") val errors: List<GraphQLError>? = null, // First, because specification mentions it would be better for it to be specified first.
    @SerialName(value = "data") val data: T? = null,
    @SerialName(value = "extensions") val extensions: JsonObject? = null
) {

    fun copy(
        errors: List<GraphQLError>? = this.errors,
        data: T? = this.data,
        extensions: JsonObject? = this.extensions
    ): GraphQLResponse<T> = GraphQLResponse(
        data = data,
        errors = errors,
        extensions = extensions
    )

    operator fun component1(): List<GraphQLError>? = errors

    operator fun component2(): T? = data

    operator fun component3(): JsonObject? = extensions

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GraphQLResponse<*>) return false

        if (errors != other.errors) return false
        if (data != other.data) return false

        return extensions == other.extensions
    }

    override fun hashCode(): Int {
        var result = data?.hashCode() ?: 0
        result = 31 * result + (errors?.hashCode() ?: 0)
        result = 31 * result + (extensions?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        "GraphQLResponse(errors=$errors, data=$data, extensions=$extensions)"
}
