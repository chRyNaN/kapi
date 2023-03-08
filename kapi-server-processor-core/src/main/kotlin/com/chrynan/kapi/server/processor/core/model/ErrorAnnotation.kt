package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.chrynan.kapi.core.annotation.Error

/**
 * Represents information extracted from an [Error] annotation.
 */
@Serializable
data class ErrorAnnotation(
    @SerialName(value = "exception") val exceptionType: KotlinTypeUsage,
    @SerialName(value = "error") val error: com.chrynan.kapi.core.ApiError
)
