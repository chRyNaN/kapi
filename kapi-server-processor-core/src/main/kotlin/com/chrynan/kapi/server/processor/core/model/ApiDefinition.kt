package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiDefinition(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "base_url") val basePath: String? = null,
    @SerialName(value = "info") val info: ApiInfo? = null,
    @SerialName(value = "servers") val servers: List<ApiServer> = emptyList(),
    @SerialName(value = "tags") val tags: List<ApiTag> = emptyList(),
    @SerialName(value = "type") val type: KotlinTypeDefinition,
    @SerialName(value = "documentation") val documentation: String? = null,
    @SerialName(value = "functions") val functions: List<ApiFunction> = emptyList(),
    @SerialName(value = "annotations") val annotations: List<KotlinAnnotationUsage> = emptyList()
)
