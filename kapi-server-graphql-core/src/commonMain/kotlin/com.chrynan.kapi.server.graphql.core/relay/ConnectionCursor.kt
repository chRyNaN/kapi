package com.chrynan.kapi.server.graphql.core.relay

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a [Connection] cursor in Relay which is an opaque [String] that the server understands. Often this is
 * base64 encoded but the spec only mandates that it be an opaque cursor so meaning it can't be inferred from it (to
 * prevent cheating like pre-calculating the next cursor on the client side).
 *
 * @property [value] An opaque [String] that represents this cursor.
 *
 * See [https://facebook.github.io/relay/graphql/connections.htm#sec-Cursor](https://facebook.github.io/relay/graphql/connections.htm#sec-Cursor)
 */
@JvmInline
@Serializable
value class ConnectionCursor(
    @SerialName(value = "value") val value: String
)
