@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ApiRequestBodyType private constructor() {

    abstract val contentType: String?

    @Serializable
    @SerialName(value = "form_url_encoded")
    object FormUrlEncoded : ApiRequestBodyType() {

        override val contentType: String = "application/x-www-form-urlencoded"
    }

    @Serializable
    @SerialName(value = "multipart")
    object Multipart : ApiRequestBodyType() {

        override val contentType: String = "multipart/form-data"
    }

    @Serializable
    @SerialName(value = "content_negotiation")
    data class ContentNegotiation(
        @SerialName(value = "value") override val contentType: String? = null
    ) : ApiRequestBodyType()

    @Serializable
    @SerialName(value = "none")
    object None : ApiRequestBodyType() {

        override val contentType: String? = null
    }
}
