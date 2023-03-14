package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents tag metadata about an API. This directly corresponds to the @Tag annotation provided to the @Api
 * annotation.
 */
@Serializable
data class ApiTag(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "description") val description: String? = null
)
