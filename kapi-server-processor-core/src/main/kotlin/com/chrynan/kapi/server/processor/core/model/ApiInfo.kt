package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents information about an API and directly corresponds to the @Info annotation provided to the @Api annotation.
 */
@Serializable
data class ApiInfo(
    @SerialName(value = "title") val title: String,
    @SerialName(value = "summary") val summary: String? = null,
    @SerialName(value = "terms_of_service") val termsOfService: String? = null,
    @SerialName(value = "contact") val contact: Contact? = null,
    @SerialName(value = "license") val license: License? = null,
    @SerialName(value = "version") val version: String? = null
) {

    @Serializable
    data class Contact(
        @SerialName(value = "name") val name: String? = null,
        @SerialName(value = "url") val url: String? = null,
        @SerialName(value = "email") val email: String? = null
    )

    @Serializable
    data class License(
        @SerialName(value = "name") val name: String,
        @SerialName(value = "identifier") val identifier: String? = null,
        @SerialName(value = "url") val url: String? = null
    )
}
