package com.chrynan.kapi.server.graphql.core.relay

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Represents an edge in Relay which is essentially a node of data T and the cursor for that node.
 *
 * @property [node] The node of data that this edge represents.
 * @property [cursor] The [ConnectionCursor] for this edge node.
 *
 * See [https://facebook.github.io/relay/graphql/connections.htm#sec-Edge-Types](https://facebook.github.io/relay/graphql/connections.htm#sec-Edge-Types)
 */
@Serializable(with = EdgeSerializer::class)
interface Edge<T> {

    /**
     * The node of data that this edge represents.
     */
    @SerialName(value = "node")
    val node: T

    /**
     * The [ConnectionCursor] for this edge node.
     */
    @SerialName(value = "cursor")
    val cursor: ConnectionCursor

    /**
     * Creates a copy of this [Edge] instance overriding the provided values.
     */
    fun copy(
        node: T = this.node,
        cursor: ConnectionCursor = this.cursor
    ): Edge<T>

    companion object
}

/**
 * Creates an instance of the [Edge] interface using the provided values.
 */
fun <T> Edge(
    node: T,
    cursor: ConnectionCursor
): Edge<T> = DefaultEdge(
    node = node,
    cursor = cursor
)

internal class EdgeSerializer<T>(
    private val typeSerializer: KSerializer<T>
) : KSerializer<Edge<T>> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Edge") {
        element(elementName = "node", descriptor = typeSerializer.descriptor)
        element<ConnectionCursor?>("cursor")
    }

    override fun serialize(encoder: Encoder, value: Edge<T>) {
        val compositeEncoder = encoder.beginStructure(descriptor)
        compositeEncoder.encodeSerializableElement(
            descriptor = descriptor,
            index = 0,
            serializer = typeSerializer,
            value = value.node
        )
        compositeEncoder.encodeSerializableElement(
            descriptor = descriptor,
            index = 1,
            serializer = ConnectionCursor.serializer(),
            value = value.cursor
        )
        compositeEncoder.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): Edge<T> {
        val compositeDecoder = decoder.beginStructure(descriptor)
        val node = compositeDecoder.decodeSerializableElement(
            descriptor = descriptor,
            index = 0,
            deserializer = typeSerializer
        )
        val cursor = compositeDecoder.decodeSerializableElement(
            descriptor = descriptor,
            index = 1,
            deserializer = ConnectionCursor.serializer()
        )
        compositeDecoder.endStructure(descriptor)

        return Edge(
            node = node,
            cursor = cursor
        )
    }
}

@Serializable
private class DefaultEdge<T>(
    @SerialName(value = "node") override val node: T,
    @SerialName(value = "cursor") override val cursor: ConnectionCursor
) : Edge<T> {

    override fun copy(
        node: T,
        cursor: ConnectionCursor
    ): Edge<T> =
        DefaultEdge(
            node = node,
            cursor = cursor
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DefaultEdge<*>) return false

        if (node != other.node) return false

        return cursor == other.cursor
    }

    override fun hashCode(): Int {
        var result = node?.hashCode() ?: 0
        result = 31 * result + cursor.hashCode()
        return result
    }

    override fun toString(): String =
        "DefaultEdge(node=$node, cursor=$cursor)"
}
