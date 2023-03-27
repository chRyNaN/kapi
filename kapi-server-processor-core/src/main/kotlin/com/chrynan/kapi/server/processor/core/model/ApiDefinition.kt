package com.chrynan.kapi.server.processor.core.model

import com.chrynan.kapi.openapi.SecurityScheme
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiDefinition(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "base_url") val basePath: String? = null,
    @SerialName(value = "info") val info: ApiInfo? = null,
    @SerialName(value = "servers") val servers: List<ApiServer> = emptyList(),
    @SerialName(value = "tags") val tags: List<ApiTag> = emptyList(),
    @SerialName(value = "type") val type: KotlinTypeDeclaration,
    @SerialName(value = "documentation") val documentation: String? = null,
    @SerialName(value = "functions") val functions: List<ApiFunction> = emptyList(),
    @SerialName(value = "annotations") val annotations: List<KotlinAnnotationUsage> = emptyList(),
    @SerialName(value = "security_schemes") val securitySchemes: Map<String, SecurityScheme> = emptyMap(),
    @SerialName(value = "auths") val auths: List<ApiAuth> = emptyList()
)
