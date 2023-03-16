@file:Suppress("unused")

package com.chrynan.kapi.core

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class HttpMethod(
    val serialName: String
) {

    @SerialName(value = "GET")
    GET(serialName = "GET"),

    @SerialName(value = "HEAD")
    HEAD(serialName = "HEAD"),

    @SerialName(value = "POST")
    POST(serialName = "POST"),

    @SerialName(value = "PUT")
    PUT(serialName = "PUT"),

    @SerialName(value = "DELETE")
    DELETE(serialName = "DELETE"),

    @SerialName(value = "CONNECT")
    CONNECT(serialName = "CONNECT"),

    @SerialName(value = "OPTIONS")
    OPTIONS(serialName = "OPTIONS"),

    @SerialName(value = "TRACE")
    TRACE(serialName = "TRACE"),

    @SerialName(value = "PATCH")
    PATCH(serialName = "PATCH");

    companion object {

        fun getByName(name: String, ignoreCase: Boolean = true): HttpMethod? =
            values().firstOrNull { it.serialName.equals(name, ignoreCase) }
    }
}

internal fun HttpMethod.toKtorHttpMethod(): io.ktor.http.HttpMethod =
    io.ktor.http.HttpMethod.parse(method = this.serialName)

internal fun io.ktor.http.HttpMethod.toHttpMethod(): HttpMethod = HttpMethod.getByName(name = this.value)!!
