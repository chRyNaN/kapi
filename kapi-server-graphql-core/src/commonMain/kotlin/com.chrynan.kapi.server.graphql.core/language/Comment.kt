package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a GraphQL comment.
 */
@Serializable
class Comment(
    @SerialName(value = "content") val content: String,
    @SerialName(value = "source_location") val sourceLocation: SourceLocation? = null
) {

    fun copy(
        content: String = this.content,
        sourceLocation: SourceLocation? = this.sourceLocation
    ): Comment = Comment(
        content = content,
        sourceLocation = sourceLocation
    )

    operator fun component1(): String = content

    operator fun component2(): SourceLocation? = sourceLocation

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Comment) return false

        if (content != other.content) return false

        return sourceLocation == other.sourceLocation
    }

    override fun hashCode(): Int {
        var result = content.hashCode()
        result = 31 * result + sourceLocation.hashCode()
        return result
    }

    override fun toString(): String =
        "Comment(content='$content', sourceLocation=$sourceLocation)"
}
