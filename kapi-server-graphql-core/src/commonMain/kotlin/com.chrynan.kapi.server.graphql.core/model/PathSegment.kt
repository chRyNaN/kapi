package com.chrynan.kapi.server.graphql.core.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonPrimitive

/**
 * Represents a path segment in a [GraphQLError] indicating where an error occurred in the GraphQL Response. These path
 * segments can either be a [String] representing a field name (or alias), or an [Int] representing an index in a list.
 * Note that the raw values should be returned so this has to be properly serialized.
 */
@Serializable(with = GraphQLErrorPathSegmentSerializer::class)
sealed interface PathSegment {

    /**
     * Represents a field name indicating the path segment where a [GraphQLError] occurred.
     */
    @Serializable
    @JvmInline
    value class Field(val value: String) : PathSegment

    /**
     * Represents an index value indicating the path segment where a [GraphQLError] occurred.
     */
    @Serializable
    @JvmInline
    value class Index(val value: Int) : PathSegment
}

internal object GraphQLErrorPathSegmentSerializer : KSerializer<PathSegment> {

    override val descriptor: SerialDescriptor
        get() = JsonPrimitive.serializer().descriptor

    override fun serialize(encoder: Encoder, value: PathSegment) {
        when (value) {
            is PathSegment.Field -> encoder.encodeString(value.value)
            is PathSegment.Index -> encoder.encodeInt(value.value)
        }
    }

    override fun deserialize(decoder: Decoder): PathSegment =
        try {
            PathSegment.Index(value = decoder.decodeInt())
        } catch (_: Exception) {
            PathSegment.Field(value = decoder.decodeString())
        }
}
