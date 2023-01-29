package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiDefinition(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "base_url") val basePath: String? = null,
    @SerialName(value = "version") val version: String? = null,
    @SerialName(value = "type_name") val typeName: KotlinName,
    @SerialName(value = "documentation") val documentation: String? = null,
    @SerialName(value = "functions") val functions: List<ApiFunction> = emptyList(),
    @SerialName(value = "annotations") val annotations: List<KotlinAnnotationUsage> = emptyList()
)
