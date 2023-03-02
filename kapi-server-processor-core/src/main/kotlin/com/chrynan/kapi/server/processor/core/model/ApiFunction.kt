@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import com.chrynan.kapi.core.HttpMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiFunction(
    @SerialName(value = "name") val name: KotlinName,
    @SerialName(value = "documentation") val documentation: String? = null,
    @SerialName(value = "method") val method: HttpMethod,
    @SerialName(value = "path") val path: String,
    @SerialName(value = "request_body_type") val requestBodyType: ApiRequestBodyType = ApiRequestBodyType.NONE,
    @SerialName(value = "response_body") val responseBody: ApiResponseBody? = null,
    @SerialName(value = "parameters") val parameters: List<ApiParameter> = emptyList(),
    @SerialName(value = "headers") val headers: List<String> = emptyList(),
    @SerialName(value = "extension_receiver") val extensionReceiver: KotlinTypeUsage? = null,
    @SerialName(value = "is_suspending") val isSuspending: Boolean = false,
    @SerialName(value = "annotations") val annotations: List<KotlinAnnotationUsage> = emptyList(),
    @SerialName(value = "errors") val errors: List<ApiError> = emptyList()
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
