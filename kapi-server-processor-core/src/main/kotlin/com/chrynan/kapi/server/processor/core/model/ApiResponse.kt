package com.chrynan.kapi.server.processor.core.model

import com.chrynan.kapi.core.ApiError
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResponse private constructor() {

    abstract val statusCode: Int
    abstract val description: String?
    abstract val headers: List<ApiResponseHeader>
    abstract val contentType: String?

    @Serializable
    data class Success(
        @SerialName(value = "status_code") override val statusCode: Int,
        @SerialName(value = "description") override val description: String?,
        @SerialName(value = "headers") override val headers: List<ApiResponseHeader> = emptyList(),
        @SerialName(value = "content_type") override val contentType: String? = null
    ) : ApiResponse()

    @Serializable
    data class Error(
        @SerialName(value = "status_code") override val statusCode: Int,
        @SerialName(value = "description") override val description: String?,
        @SerialName(value = "headers") override val headers: List<ApiResponseHeader> = emptyList(),
        @SerialName(value = "content_type") override val contentType: String? = null,
        @SerialName(value = "exception") val exception: KotlinTypeUsage,
        @SerialName(value = "value") val value: ApiError
    ) : ApiResponse()
}
