@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Special traversal control values
 */
@Serializable
enum class TraversalControl(val serialName: String) {

    /**
     * When returned the traversal will continue as planned.
     */
    @SerialName(value = "continue")
    CONTINUE(serialName = "continue"),

    /**
     * When returned from a Visitor's method, indicates exiting the traversal
     */
    @SerialName(value = "quit")
    QUIT(serialName = "quit"),

    /**
     * When returned from a Visitor's method, indicates skipping traversal of a subtree.
     *
     * Not allowed to be returned from 'leave' or 'backRef' because it doesn't make sense.
     */
    @SerialName(value = "abort")
    ABORT(serialName = "abort");

    companion object {

        fun getBySerialName(name: String, ignoreCase: Boolean = true): TraversalControl? =
            values().firstOrNull { it.serialName.equals(name, ignoreCase) }
    }
}
