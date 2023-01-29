package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.chrynan.kapi.core.annotation.Error

/**
 * Represents information extracted from an [Error] annotation.
 */
@Serializable
data class ApiError(
    @SerialName(value = "status_code") val statusCode: Int,
    @SerialName(value = "exception") val exceptionType: KotlinTypeUsage,
    @SerialName(value = "type") val type: String = "about:blank",
    @SerialName(value = "title") val title: String,
    @SerialName(value = "details") val details: String = "",
    @SerialName(value = "instance") val instance: String = "",
    @SerialName(value = "help") val help: String = ""
)
