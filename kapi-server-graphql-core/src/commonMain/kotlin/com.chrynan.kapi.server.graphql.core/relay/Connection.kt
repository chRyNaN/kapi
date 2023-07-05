package com.chrynan.kapi.server.graphql.core.relay

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * This represents a connection in Relay, which is a list of [Edge]s as well as a [PageInfo] that describes the
 * pagination of that list.
 *
 * See [https://facebook.github.io/relay/graphql/connections.htm](https://facebook.github.io/relay/graphql/connections.htm)
 */
@Serializable(with = ConnectionSerializer::class)
interface Connection<T> {

    /**
     * A list of [graphql.relay.Edge]s that are really a node of data and its cursor.
     */
    @SerialName(value = "edges")
    val edges: List<Edge<T>?>?

    /**
     * [PageInfo] pagination data about that list of edges.
     */
    @SerialName(value = "pageInfo")
    val pageInfo: PageInfo

    /**
     * Creates a copy of this [Connection] instance overriding the provided values.
     */
    fun copy(
        edges: List<Edge<T>?>? = this.edges,
        pageInfo: PageInfo = this.pageInfo
    ): Connection<T>

    companion object
}

/**
 * Creates a [Connection] interface instance using the provided values.
 */
fun <T> Connection(
    edges: List<Edge<T>?>? = null,
    pageInfo: PageInfo = PageInfo()
): Connection<T> = DefaultConnection(
    edges = edges,
    pageInfo = pageInfo
)

@OptIn(ExperimentalSerializationApi::class)
internal class ConnectionSerializer<T>(
    private val typeSerializer: KSerializer<T>
) : KSerializer<Connection<T>> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Connection") {
        element(
            elementName = "edges",
            descriptor = ListSerializer(EdgeSerializer(typeSerializer).nullable).nullable.descriptor
        )
        element<PageInfo>("pageInfo")
    }

    override fun serialize(encoder: Encoder, value: Connection<T>) {
        val compositeEncoder = encoder.beginStructure(descriptor)
        compositeEncoder.encodeNullableSerializableElement(
            descriptor = descriptor,
            index = 0,
            serializer = ListSerializer(EdgeSerializer(typeSerializer).nullable),
            value = value.edges
        )
        compositeEncoder.encodeSerializableElement(
            descriptor = descriptor,
            index = 1,
            serializer = PageInfoSerializer,
            value = value.pageInfo
        )
        compositeEncoder.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): Connection<T> {
        val compositeDecoder = decoder.beginStructure(descriptor)
        val edges = compositeDecoder.decodeNullableSerializableElement(
            descriptor = descriptor,
            index = 0,
            deserializer = ListSerializer(EdgeSerializer(typeSerializer).nullable)
        )
        val pageInfo = compositeDecoder.decodeSerializableElement(
            descriptor = descriptor,
            index = 1,
            deserializer = PageInfoSerializer
        )
        compositeDecoder.endStructure(descriptor)

        return Connection(
            edges = edges,
            pageInfo = pageInfo
        )
    }
}

@Serializable
private class DefaultConnection<T>(
    @SerialName(value = "edges") override val edges: List<Edge<T>?>? = null,
    @SerialName(value = "pageInfo") override val pageInfo: PageInfo = PageInfo()
) : Connection<T> {

    override fun copy(
        edges: List<Edge<T>?>?,
        pageInfo: PageInfo
    ): Connection<T> =
        DefaultConnection(
            edges = edges,
            pageInfo = pageInfo
        )
}
