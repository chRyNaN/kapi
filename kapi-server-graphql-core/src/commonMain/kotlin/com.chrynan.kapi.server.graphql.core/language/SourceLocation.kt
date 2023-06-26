package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the location of a [GraphQLError] in a GraphQL Schema.
 */
@Serializable
class SourceLocation(
    @SerialName(value = "line") val line: Int,
    @SerialName(value = "column") val column: Int,
    @SerialName(value = "name") val name: String? = null
) {

    fun copy(
        line: Int = this.line,
        column: Int = this.column,
        name: String? = this.name
    ): SourceLocation = SourceLocation(
        line = line,
        column = column,
        name = name
    )

    operator fun component1(): Int = line

    operator fun component2(): Int = column

    operator fun component3(): String? = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SourceLocation) return false

        if (line != other.line) return false
        if (column != other.column) return false

        return name == other.name
    }

    override fun hashCode(): Int {
        var result = line
        result = 31 * result + column
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        "SourceLocation(line=$line, column=$column, name=$name)"
}
