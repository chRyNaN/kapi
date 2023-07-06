package com.chrynan.kapi.server.graphql.core.model

import com.chrynan.kapi.server.graphql.core.language.SourceLocation
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonObject

/**
 * Describes errors that can occur with a GraphQL Operation and is returned in the [GraphQLResponse].
 *
 * @see [GraphQL Specification - Errors](https://spec.graphql.org/draft/#sec-Errors)
 */
@Serializable(with = GraphQLErrorSerializer::class)
interface GraphQLError {

    /**
     * A required property of the [GraphQLError] interface that provides a description of the error intended for the
     * developer as a guide to understand and correct the error.
     */
    @SerialName(value = "message")
    val message: String?

    /**
     * An optional property representing the location(s) within the GraphQL document at which the error occurred. Each
     * [SourceLocation] describes the beginning of an associated syntax element.
     */
    @SerialName(value = "locations")
    val locations: List<SourceLocation?>?
        get() = null

    /**
     * An optional property that represents the path segments in the response to point to where the error occurred.
     */
    @SerialName(value = "path")
    val path: List<PathSegment?>?
        get() = null

    /**
     * An optional property that represents extra information about the error that is implementation specific. This
     * property must be a JSON Map according to the GraphQL Specification but that's essentially a JSON serialized
     * object in Kotlin. So assert that the object is properly serializable to JSON.
     */
    @SerialName(value = "extensions")
    val extensions: JsonObject?
        get() = null

    /**
     * Creates a copy of this [GraphQLError] overriding the provided values.
     */
    fun copy(
        message: String? = this.message,
        locations: List<SourceLocation?>? = this.locations,
        path: List<PathSegment?>? = this.path,
        extensions: JsonObject? = this.extensions
    ): GraphQLError
}

operator fun GraphQLError.component1(): String? = message

operator fun GraphQLError.component2(): List<SourceLocation?>? = locations

operator fun GraphQLError.component3(): List<PathSegment?>? = path

operator fun GraphQLError.component4(): JsonObject? = extensions

/**
 * Creates a [GraphQLError] instance with the provided values.
 *
 * @param [message] The [GraphQLError.message] value. Defaults to `null`.
 * @param [locations] The [GraphQLError.locations] value. Defaults to `null`.
 * @param [path] The [GraphQLError.path] value. Defaults to `null`.
 * @param [extensions] The [GraphQLError.extensions] value. Defaults to `null`.
 */
fun GraphQLError(
    message: String? = null,
    locations: List<SourceLocation?>? = null,
    path: List<PathSegment?>? = null,
    extensions: JsonObject? = null
): GraphQLError = DefaultGraphQLError(
    message = message,
    locations = locations,
    path = path,
    extensions = extensions
)

@OptIn(ExperimentalSerializationApi::class)
internal object GraphQLErrorSerializer : KSerializer<GraphQLError> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(serialName = "GraphQLError") {
        element<String>("message")
        element<List<SourceLocation?>?>("locations")
        element<List<PathSegment?>?>("path")
        element<JsonObject?>("extensions")
    }

    override fun serialize(encoder: Encoder, value: GraphQLError) {
        val compositeEncoder = encoder.beginStructure(descriptor)
        compositeEncoder.encodeNullableSerializableElement(
            descriptor = descriptor,
            index = 0,
            serializer = String.serializer(),
            value = value.message
        )
        compositeEncoder.encodeNullableSerializableElement(
            descriptor = descriptor,
            index = 1,
            serializer = ListSerializer(SourceLocation.serializer().nullable),
            value = value.locations
        )
        compositeEncoder.encodeNullableSerializableElement(
            descriptor = descriptor,
            index = 2,
            serializer = ListSerializer(PathSegment.serializer().nullable),
            value = value.path
        )
        compositeEncoder.encodeNullableSerializableElement(
            descriptor = descriptor,
            index = 3,
            serializer = JsonObject.serializer(),
            value = value.extensions
        )
        compositeEncoder.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): GraphQLError {
        val compositeDecoder = decoder.beginStructure(descriptor)
        val message = decoder.decodeNullableSerializableValue(deserializer = String.serializer())
        val locations =
            decoder.decodeNullableSerializableValue(deserializer = ListSerializer(SourceLocation.serializer().nullable))
        val path =
            decoder.decodeNullableSerializableValue(deserializer = ListSerializer(PathSegment.serializer().nullable))
        val extensions = decoder.decodeNullableSerializableValue(deserializer = JsonObject.serializer())
        compositeDecoder.endStructure(descriptor)

        return GraphQLError(
            message = message,
            locations = locations,
            path = path,
            extensions = extensions
        )
    }
}

@Serializable
private class DefaultGraphQLError(
    @SerialName(value = "message") override val message: String? = null,
    @SerialName(value = "locations") override val locations: List<SourceLocation?>? = null,
    @SerialName(value = "path") override val path: List<PathSegment?>? = null,
    @SerialName(value = "extensions") override val extensions: JsonObject? = null
) : GraphQLError {

    override fun copy(
        message: String?,
        locations: List<SourceLocation?>?,
        path: List<PathSegment?>?,
        extensions: JsonObject?
    ): GraphQLError =
        DefaultGraphQLError(
            message = message,
            locations = locations,
            path = path,
            extensions = extensions
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DefaultGraphQLError) return false

        if (message != other.message) return false
        if (locations != other.locations) return false
        if (path != other.path) return false

        return extensions == other.extensions
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + (locations?.hashCode() ?: 0)
        result = 31 * result + (path?.hashCode() ?: 0)
        result = 31 * result + (extensions?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        "DefaultGraphQLError(message='$message', locations=$locations, path=$path, extensions=$extensions)"
}
