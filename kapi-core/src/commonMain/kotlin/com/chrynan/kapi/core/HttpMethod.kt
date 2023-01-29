@file:Suppress("unused")

package com.chrynan.kapi.core

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlin.reflect.KClass

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

val HttpMethod.annotationClass: KClass<*>?
    get() = when (this) {
        HttpMethod.GET -> com.chrynan.kapi.core.annotation.GET::class
        HttpMethod.HEAD -> com.chrynan.kapi.core.annotation.HEAD::class
        HttpMethod.POST -> com.chrynan.kapi.core.annotation.POST::class
        HttpMethod.PUT -> com.chrynan.kapi.core.annotation.PUT::class
        HttpMethod.DELETE -> com.chrynan.kapi.core.annotation.DELETE::class
        HttpMethod.OPTIONS -> com.chrynan.kapi.core.annotation.OPTIONS::class
        HttpMethod.PATCH -> com.chrynan.kapi.core.annotation.PATCH::class
        else -> null
    }

fun <A : Annotation> HttpMethod.Companion.getByAnnotationClass(kClass: KClass<A>): HttpMethod? =
    HttpMethod.values().firstOrNull { it.annotationClass == kClass }

internal fun HttpMethod.toKtorHttpMethod(): io.ktor.http.HttpMethod =
    io.ktor.http.HttpMethod.parse(method = this.serialName)

internal fun io.ktor.http.HttpMethod.toHttpMethod(): HttpMethod = HttpMethod.getByName(name = this.value)!!
