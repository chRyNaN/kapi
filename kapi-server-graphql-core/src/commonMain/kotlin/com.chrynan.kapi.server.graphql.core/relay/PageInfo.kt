package com.chrynan.kapi.server.graphql.core.relay

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Represents pagination information in Relay about [Edge]s when used inside a [Connection].
 *
 * @property [startCursor] Cursor to the first edge, or null if this page is empty.
 * @property [endCursor] Cursor to the last edge, or null if this page is empty.
 * @property [hasPreviousPage] `true` if and only if this page is not the first page. This is only meaningful when
 * you gave the `last` argument.
 * @property [hasNextPage] `true` if and only if this page is not the last page. This is only meaningful when you
 * gave the `first` argument.
 *
 * @see [https://facebook.github.io/relay/graphql/connections.htm#sec-undefined.PageInfo](https://facebook.github.io/relay/graphql/connections.htm#sec-undefined.PageInfo)
 */
@Serializable(with = PageInfoSerializer::class)
interface PageInfo {

    /**
     * Cursor to the first edge, or null if this page is empty.
     */
    @SerialName(value = "startCursor")
    val startCursor: ConnectionCursor?

    /**
     * Cursor to the last edge, or null if this page is empty.
     */
    @SerialName(value = "endCursor")
    val endCursor: ConnectionCursor?

    /**
     * `true` if and only if this page is not the first page. This is only meaningful when you gave the `last` argument.
     */
    @SerialName(value = "hasPreviousPage")
    val hasPreviousPage: Boolean

    /**
     * `true` if and only if this page is not the last page. This is only meaningful when you gave the `first` argument.
     */
    @SerialName(value = "hasNextPage")
    val hasNextPage: Boolean

    /**
     * Creates a copy of this [PageInfo] instance overriding the provided values.
     */
    fun copy(
        startCursor: ConnectionCursor? = this.startCursor,
        endCursor: ConnectionCursor? = this.endCursor,
        hasPreviousPage: Boolean = this.hasPreviousPage,
        hasNextPage: Boolean = this.hasNextPage
    ): PageInfo

    companion object
}

/**
 * Creates an instance of the [PageInfo] interface using the provided values.
 */
fun PageInfo(
    startCursor: ConnectionCursor? = null,
    endCursor: ConnectionCursor? = null,
    hasPreviousPage: Boolean = false,
    hasNextPage: Boolean = false
): PageInfo = DefaultPageInfo(
    startCursor = startCursor,
    endCursor = endCursor,
    hasPreviousPage = hasPreviousPage,
    hasNextPage = hasNextPage
)

@OptIn(ExperimentalSerializationApi::class)
internal object PageInfoSerializer : KSerializer<PageInfo> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("PageInfo") {
        element<ConnectionCursor?>("startCursor")
        element<ConnectionCursor?>("endCursor")
        element<Boolean>("hasPreviousPage")
        element<Boolean>("hasNextPage")
    }

    override fun serialize(encoder: Encoder, value: PageInfo) {
        val compositeEncoder = encoder.beginStructure(descriptor)
        compositeEncoder.encodeNullableSerializableElement(
            descriptor = descriptor,
            index = 0,
            serializer = ConnectionCursor.serializer(),
            value = value.startCursor
        )
        compositeEncoder.encodeNullableSerializableElement(
            descriptor = descriptor,
            index = 1,
            serializer = ConnectionCursor.serializer(),
            value = value.endCursor
        )
        compositeEncoder.encodeBooleanElement(
            descriptor = descriptor,
            index = 2,
            value = value.hasPreviousPage
        )
        compositeEncoder.encodeBooleanElement(
            descriptor = descriptor,
            index = 3,
            value = value.hasNextPage
        )
        compositeEncoder.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): PageInfo {
        val compositeDecoder = decoder.beginStructure(descriptor)
        val startCursor = compositeDecoder.decodeNullableSerializableElement(
            descriptor = descriptor,
            index = 0,
            deserializer = ConnectionCursor.serializer()
        )
        val endCursor = compositeDecoder.decodeNullableSerializableElement(
            descriptor = descriptor,
            index = 1,
            deserializer = ConnectionCursor.serializer()
        )
        val hasPreviousPage = compositeDecoder.decodeBooleanElement(
            descriptor = descriptor,
            index = 2
        )
        val hasNextPage = compositeDecoder.decodeBooleanElement(
            descriptor = descriptor,
            index = 3
        )
        compositeDecoder.endStructure(descriptor)

        return PageInfo(
            startCursor = startCursor,
            endCursor = endCursor,
            hasPreviousPage = hasPreviousPage,
            hasNextPage = hasNextPage
        )
    }
}

/**
 * Default implementation of the [PageInfo] interface.
 */
@Serializable
private class DefaultPageInfo(
    @SerialName(value = "startCursor") override val startCursor: ConnectionCursor? = null,
    @SerialName(value = "endCursor") override val endCursor: ConnectionCursor? = null,
    @SerialName(value = "hasPreviousPage") override val hasPreviousPage: Boolean = false,
    @SerialName(value = "hasNextPage") override val hasNextPage: Boolean = false
) : PageInfo {

    override fun copy(
        startCursor: ConnectionCursor?,
        endCursor: ConnectionCursor?,
        hasPreviousPage: Boolean,
        hasNextPage: Boolean
    ): PageInfo = DefaultPageInfo(
        startCursor = startCursor,
        endCursor = endCursor,
        hasPreviousPage = hasPreviousPage,
        hasNextPage = hasNextPage
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DefaultPageInfo) return false

        if (startCursor != other.startCursor) return false
        if (endCursor != other.endCursor) return false
        if (hasPreviousPage != other.hasPreviousPage) return false

        return hasNextPage == other.hasNextPage
    }

    override fun hashCode(): Int {
        var result = startCursor?.hashCode() ?: 0
        result = 31 * result + (endCursor?.hashCode() ?: 0)
        result = 31 * result + hasPreviousPage.hashCode()
        result = 31 * result + hasNextPage.hashCode()
        return result
    }

    override fun toString(): String =
        "DefaultPageInfo(" +
                "startCursor=$startCursor, " +
                "endCursor=$endCursor, " +
                "isHasPreviousPage=$hasPreviousPage, " +
                "isHasNextPage=$hasNextPage)"
}
