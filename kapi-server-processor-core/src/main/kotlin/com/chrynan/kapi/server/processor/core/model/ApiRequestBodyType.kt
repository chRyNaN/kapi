@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ApiRequestBodyType private constructor() {

    @Serializable
    @SerialName(value = "form_url_encoded")
    object FormUrlEncoded : ApiRequestBodyType()

    @Serializable
    @SerialName(value = "multipart")
    object Multipart : ApiRequestBodyType()

    @Serializable
    @SerialName(value = "content_negotiation")
    data class ContentNegotiation(@SerialName(value = "value") val value: String? = null) : ApiRequestBodyType()

    @Serializable
    @SerialName(value = "none")
    object None : ApiRequestBodyType()
}
