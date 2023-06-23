package com.chrynan.kapi.server.graphql.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The result of executing a GraphQL Operation.
 *
 * @property [data] The resulting data from the GraphQL Operation. Note that the resulting data could be expected to be
 * null in some cases or if an error occurs, the data could be null.
 * @property [errors] The set of errors that occurred during the GraphQL Operation. If the [errors] is
 * [List.isNullOrEmpty], then there was no encountered errors.
 */
@Serializable
class GraphQLResponse<T : Any?>(
    @SerialName(value = "data") val data: T? = null,
    @SerialName(value = "errors") val errors: List<GraphQLError>? = null
) {

    fun copy(
        data: T? = this.data,
        errors: List<GraphQLError>? = this.errors
    ): GraphQLResponse<T> = GraphQLResponse(
        data = data,
        errors = errors
    )

    operator fun component1(): T? = data

    operator fun component2(): List<GraphQLError>? = errors

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GraphQLResponse<*>) return false

        if (data != other.data) return false

        return errors == other.errors
    }

    override fun hashCode(): Int {
        var result = data?.hashCode() ?: 0
        result = 31 * result + (errors?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        "GraphQLResponse(data=$data, errors=$errors)"
}
