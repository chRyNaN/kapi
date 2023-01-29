@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ApiRequestBodyType(val serialName: String) {

    @SerialName(value = "form_url_encoded")
    FORM_URL_ENCODED(serialName = "form_url_encoded"),

    @SerialName(value = "multipart")
    MULTIPART(serialName = "multipart"),

    @SerialName(value = "content_negotiation")
    CONTENT_NEGOTIATION(serialName = "content_negotiation"),

    @SerialName(value = "none")
    NONE(serialName = "none");

    companion object {

        fun getByName(name: String, ignoreCase: Boolean = true): ApiRequestBodyType? =
            values().firstOrNull { it.serialName.equals(name, ignoreCase) }
    }
}
