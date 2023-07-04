package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Description(
    @SerialName(value = "content") val content: String,
    @SerialName(value = "source_location") val sourceLocation: SourceLocation? = null,
    @SerialName(value = "multiline") val isMultiline: Boolean = false
) {

    fun copy(
        content: String = this.content,
        sourceLocation: SourceLocation? = this.sourceLocation,
        isMultiline: Boolean = this.isMultiline
    ): Description = Description(
        content = content,
        sourceLocation = sourceLocation,
        isMultiline = isMultiline
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Description) return false

        if (content != other.content) return false
        if (sourceLocation != other.sourceLocation) return false

        return isMultiline == other.isMultiline
    }

    override fun hashCode(): Int {
        var result = content.hashCode()
        result = 31 * result + sourceLocation.hashCode()
        result = 31 * result + isMultiline.hashCode()
        return result
    }

    override fun toString(): String =
        "Description(content='$content', sourceLocation=$sourceLocation, isMultiline=$isMultiline)"
}
