@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Represents characters that were ignored, around a non-ignored characters, during parsing of a GraphQL document. For
 * instance, space surrounding a field is considered ignored characters.
 */
@Serializable(with = IgnoredCharsSerializer::class)
class IgnoredChars(
    left: List<IgnoredChar> = emptyList(),
    right: List<IgnoredChar> = emptyList()
) {

    @SerialName(value = "left")
    val left: List<IgnoredChar>

    @SerialName(value = "right")
    val right: List<IgnoredChar>

    init {
        this.left = left.toList()
        this.right = right.toList()
    }

    fun copy(
        left: List<IgnoredChar> = this.left,
        right: List<IgnoredChar> = this.right
    ): IgnoredChars = IgnoredChars(
        left = left,
        right = right
    )

    operator fun component1(): List<IgnoredChar> = left

    operator fun component2(): List<IgnoredChar> = right

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IgnoredChars) return false

        if (left != other.left) return false

        return right == other.right
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + right.hashCode()
        return result
    }

    override fun toString(): String =
        "IgnoredChars(left=$left, right=$right)"

    companion object {

        val EMPTY = IgnoredChars()
    }
}

@OptIn(ExperimentalSerializationApi::class)
internal object IgnoredCharsSerializer : KSerializer<IgnoredChars> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(serialName = "IgnoredChars") {
        element<List<IgnoredChar>>(elementName = "left")
        element<List<IgnoredChar>>(elementName = "right")
    }

    override fun serialize(encoder: Encoder, value: IgnoredChars) {
        val compositeEncoder = encoder.beginStructure(descriptor)
        compositeEncoder.encodeSerializableElement(
            serializer = ListSerializer(IgnoredChar.serializer()),
            value = value.left,
            descriptor = descriptor.getElementDescriptor(0),
            index = 0
        )
        compositeEncoder.encodeSerializableElement(
            serializer = ListSerializer(IgnoredChar.serializer()),
            value = value.right,
            descriptor = descriptor.getElementDescriptor(1),
            index = 1
        )
        compositeEncoder.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): IgnoredChars {
        val compositeDecoder = decoder.beginStructure(descriptor)

        val left = compositeDecoder.decodeSerializableElement(
            descriptor = descriptor.getElementDescriptor(0),
            index = 0,
            deserializer = ListSerializer(IgnoredChar.serializer())
        )
        val right = compositeDecoder.decodeSerializableElement(
            descriptor = descriptor.getElementDescriptor(1),
            index = 1,
            deserializer = ListSerializer(IgnoredChar.serializer())
        )

        compositeDecoder.endStructure(descriptor)

        return IgnoredChars(
            left = left,
            right = right
        )
    }
}
