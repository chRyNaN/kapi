package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseBody(
    @SerialName(value = "type") val type: KotlinTypeUsage,
    @SerialName(value = "documentation") val documentation: String? = null
)
