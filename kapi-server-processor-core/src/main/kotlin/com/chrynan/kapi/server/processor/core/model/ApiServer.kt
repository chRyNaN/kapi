package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents server information that implements an API. This directly corresponds to the @Server annotation provided
 * to the @Api annotation.
 */
@Serializable
data class ApiServer(
    @SerialName(value = "url") val url: String,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "variables") val variables: List<Variable> = emptyList()
) {

    @Serializable
    data class Variable(
        @SerialName(value = "name") val name: String,
        @SerialName(value = "default_value") val defaultValue: String,
        @SerialName(value = "description") val description: String? = null,
        @SerialName(value = "allowable_values") val allowableValues: List<String> = emptyList()
    )
}
