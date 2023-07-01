package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Operation(val serialName: String) {

    @SerialName(value = "query")
    QUERY(serialName = "query"),

    @SerialName(value = "mutation")
    MUTATION(serialName = "mutation"),

    @SerialName(value = "subscription")
    SUBSCRIPTION(serialName = "subscription");

    companion object {

        fun getBySerialName(name: String, ignoreCase: Boolean = true): Operation? =
            values().firstOrNull { it.serialName.equals(name, ignoreCase) }
    }
}
