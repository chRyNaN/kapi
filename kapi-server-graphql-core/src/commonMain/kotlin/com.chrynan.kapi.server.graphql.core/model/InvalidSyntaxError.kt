@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.model

import com.chrynan.kapi.server.graphql.core.language.SourceLocation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
@SerialName(value = "InvalidSyntaxError")
class InvalidSyntaxError(
    @SerialName(value = "source_preview") val sourcePreview: String? = null,
    @SerialName(value = "offending_token") val offendingToken: String? = null,
    @SerialName(value = "message") override val message: String,
    @SerialName(value = "locations") override val locations: List<SourceLocation?>? = null,
    @SerialName(value = "path") override val path: List<PathSegment?>? = null,
    @SerialName(value = "extensions") override val extensions: JsonObject? = null
) : GraphQLError {

    constructor(
        sourcePreview: String? = null,
        offendingToken: String? = null,
        message: String,
        location: SourceLocation,
        path: List<PathSegment?>? = null,
        extensions: JsonObject? = null
    ) : this(
        sourcePreview = sourcePreview,
        offendingToken = offendingToken,
        message = message,
        locations = listOf(location),
        path = path,
        extensions = extensions
    )

    override fun copy(
        message: String,
        locations: List<SourceLocation?>?,
        path: List<PathSegment?>?,
        extensions: JsonObject?
    ): GraphQLError = InvalidSyntaxError(
        message = message,
        locations = locations,
        path = path,
        extensions = extensions
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InvalidSyntaxError) return false

        if (sourcePreview != other.sourcePreview) return false
        if (offendingToken != other.offendingToken) return false
        if (message != other.message) return false
        if (locations != other.locations) return false
        if (path != other.path) return false

        return extensions == other.extensions
    }

    override fun hashCode(): Int {
        var result = sourcePreview?.hashCode() ?: 0
        result = 31 * result + (offendingToken?.hashCode() ?: 0)
        result = 31 * result + message.hashCode()
        result = 31 * result + (locations?.hashCode() ?: 0)
        result = 31 * result + (path?.hashCode() ?: 0)
        result = 31 * result + (extensions?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        "InvalidSyntaxError(" +
                "sourcePreview=$sourcePreview, " +
                "offendingToken=$offendingToken, " +
                "message='$message', " +
                "locations=$locations, " +
                "path=$path, " +
                "extensions=$extensions)"
}
