@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import com.chrynan.kapi.core.HttpMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiFunction(
    @SerialName(value = "kotlin_function") val kotlinFunction: KotlinFunctionDeclaration,
    @SerialName(value = "method") val method: HttpMethod,
    @SerialName(value = "path") val path: String,
    @SerialName(value = "request_body_type") val requestBodyType: ApiRequestBodyType = ApiRequestBodyType.NONE,
    @SerialName(value = "response_body") val responseBody: KotlinTypeUsageWithDeclaration? = null,
    @SerialName(value = "parameters") val parameters: List<ApiParameter> = emptyList(),
    @SerialName(value = "response_headers") val responseHeaders: List<ApiResponseHeader> = emptyList(),
    @SerialName(value = "extension_receiver") val extensionReceiver: KotlinTypeUsage? = null,
    @SerialName(value = "errors") val errors: List<ErrorAnnotation> = emptyList()
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

fun ApiFunction.bodyParameter(): BodyParameter =
    parameters.filterIsInstance<BodyParameter>().first()

fun ApiFunction.bodyParameterOrNull(): BodyParameter? =
    parameters.filterIsInstance<BodyParameter>().firstOrNull()
