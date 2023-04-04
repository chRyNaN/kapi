@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import com.chrynan.kapi.core.HttpMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiFunction(
    @SerialName(value = "kotlin_function") val kotlinFunction: KotlinFunctionDeclaration,
    @SerialName(value = "http_method") val method: HttpMethod,
    @SerialName(value = "path") val path: String,
    @SerialName(value = "request_content_type") val requestContentType: String? = null,
    @SerialName(value = "success_response") val successResponse: ApiResponse.Success? = null,
    @SerialName(value = "error_responses") val errorResponses: List<ApiResponse.Error> = emptyList(),
    @SerialName(value = "parameters") val parameters: List<ApiParameter> = emptyList(),
    @SerialName(value = "deprecated") val isDeprecated: Boolean = false,
    @SerialName(value = "tags") val tags: List<ApiTag> = emptyList(),
    @SerialName(value = "auths") val auths: List<ApiAuth> = emptyList()
)

val ApiFunction.pathParameters: List<PathParameter>
    get() = parameters.filterIsInstance<PathParameter>()

val ApiFunction.queryParameters: List<QueryParameter>
    get() = parameters.filterIsInstance<QueryParameter>()

val ApiFunction.fieldParameters: List<FieldParameter>
    get() = parameters.filterIsInstance<FieldParameter>()

val ApiFunction.partParameters: List<PartParameter>
    get() = parameters.filterIsInstance<PartParameter>()

val ApiFunction.headerParameters: List<HeaderParameter>
    get() = parameters.filterIsInstance<HeaderParameter>()

val ApiFunction.principalParameters: List<PrincipalParameter>
    get() = parameters.filterIsInstance<PrincipalParameter>()

fun ApiFunction.bodyParameter(): BodyParameter =
    parameters.filterIsInstance<BodyParameter>().first()

fun ApiFunction.bodyParameterOrNull(): BodyParameter? =
    parameters.filterIsInstance<BodyParameter>().firstOrNull()
