@file:Suppress("unused")

package com.chrynan.kapi.server.core.util

import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.converters.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer
import java.io.InputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * A component that is responsible for extracting parameter data from an HTTP request for an API function.
 */
@ExperimentalServerApi
interface HttpRequestParameterExtractor {

    val route: Route
    val call: ApplicationCall
    val requestBodyContentType: String?

    suspend fun <T : Any> extractParameter(
        name: String,
        index: Int,
        annotationType: ParameterAnnotationType,
        typeInfo: TypeInfo,
        isNullable: Boolean
    ): T?

    @ExperimentalServerApi
    fun interface Factory {

        operator fun invoke(
            route: Route,
            call: ApplicationCall,
            requestBodyContentType: String?
        ): HttpRequestParameterExtractor
    }
}

@ExperimentalServerApi
class DefaultHttpRequestParameterExtractor(
    override val route: Route,
    override val call: ApplicationCall,
    override val requestBodyContentType: String?,
    private val json: Json,
    private val conversionService: ConversionService
) : HttpRequestParameterExtractor {

    private var multiPartData: MultiPartData? = null
    private var multiPartDataMap: Map<String?, PartData>? = null
    private var fieldRequestBody: Any? = null
    private var body: Any? = null

    override suspend fun <T : Any> extractParameter(
        name: String,
        index: Int,
        annotationType: ParameterAnnotationType,
        typeInfo: TypeInfo,
        isNullable: Boolean
    ): T? = when (annotationType) {
        ParameterAnnotationType.BODY -> extractBody(typeInfo = typeInfo, isNullable = isNullable)
        ParameterAnnotationType.PART -> extractPart(name = name, typeInfo = typeInfo, isNullable = isNullable)
        ParameterAnnotationType.FIELD -> extractField(name = name, typeInfo = typeInfo, isNullable = isNullable)
        ParameterAnnotationType.PATH -> extractPath(name = name, typeInfo = typeInfo, isNullable = isNullable)
        ParameterAnnotationType.QUERY -> extractQuery(name = name, typeInfo = typeInfo, isNullable = isNullable)
        ParameterAnnotationType.HEADER -> extractHeader(name = name, typeInfo = typeInfo, isNullable = isNullable)
        ParameterAnnotationType.PRINCIPAL -> extractPrincipal(
            name = name,
            typeInfo = typeInfo,
            isNullable = isNullable
        )

        ParameterAnnotationType.SUPPORTED -> extractSupported(name = name, typeInfo = typeInfo)
    }

    private fun <T : Any> extractHeader(
        name: String,
        typeInfo: TypeInfo,
        isNullable: Boolean
    ): T? = if (isNullable) {
        call.request.headers.getOrNull(name = name, typeInfo = typeInfo, conversionService = conversionService)
    } else {
        call.request.headers.getOrFail(name = name, typeInfo = typeInfo, conversionService = conversionService)
    }

    private fun <T : Any> extractQuery(
        name: String,
        typeInfo: TypeInfo,
        isNullable: Boolean
    ): T? = if (isNullable) {
        call.request.queryParameters.getOrNull(name = name, typeInfo = typeInfo, conversionService = conversionService)
    } else {
        call.request.queryParameters.getOrFail(name = name, typeInfo = typeInfo, conversionService = conversionService)
    }

    private fun <T : Any> extractPath(
        name: String,
        typeInfo: TypeInfo,
        isNullable: Boolean
    ): T? = if (isNullable) {
        call.parameters.getOrNull(name = name, typeInfo = typeInfo, conversionService = conversionService)
    } else {
        call.parameters.getOrFail(name = name, typeInfo = typeInfo, conversionService = conversionService)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> extractPrincipal(
        name: String,
        typeInfo: TypeInfo,
        isNullable: Boolean
    ): T? {
        if (!typeInfo.type.isSubclassOf(Principal::class)) {
            throw ParameterConversionException(
                parameterName = name,
                type = typeInfo.type.simpleName ?: typeInfo.type.toString(),
                cause = IllegalArgumentException("Only `io.ktor.server.auth.Principal` types are allowed for API function parameters annotated with the `@Principal` annotation.")
            )
        }

        typeInfo.type

        // FIXME: Is this going to work as expected?
        return extractPrincipal(
            name = name,
            typeInfo = typeInfo,
            kClass = typeInfo.type as KClass<Principal>,
            isNullable = isNullable
        ) as T?
    }

    private fun <T : Principal> extractPrincipal(
        name: String,
        typeInfo: TypeInfo,
        @Suppress("UNUSED_PARAMETER") kClass: KClass<T>,
        isNullable: Boolean
    ): T? = if (isNullable) {
        try {
            call.principal<T>(provider = name, typeInfo = typeInfo)
        } catch (_: Exception) {
            null
        }
    } else {
        call.principal(provider = name, typeInfo = typeInfo)
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <T : Any> extractBody(
        typeInfo: TypeInfo,
        isNullable: Boolean
    ): T? {
        val requestBody = when {
            typeInfo.type == String::class && isNullable -> tryOrNullSuspending { call.receiveText() as T }
            typeInfo.type == ByteReadChannel::class && isNullable -> tryOrNullSuspending { call.receiveChannel() as T }
            typeInfo.type == InputStream::class && isNullable -> tryOrNullSuspending { call.receiveStream() as T }
            typeInfo.type == MultiPartData::class && isNullable -> tryOrNullSuspending { getRequestBodyMultiPartData() as T }
            typeInfo.type == Parameters::class && isNullable -> tryOrNullSuspending { getRequestBodyParameters() as T }
            typeInfo.type == String::class -> call.receiveText() as T
            typeInfo.type == ByteReadChannel::class -> call.receiveChannel() as T
            typeInfo.type == InputStream::class -> call.receiveStream() as T
            typeInfo.type == MultiPartData::class -> getRequestBodyMultiPartData() as T
            typeInfo.type == Parameters::class -> getRequestBodyParameters() as T
            this.body != null && this.body is JsonElement -> body?.let {
                json.decodeFromJsonElement(
                    deserializer = json.serializersModule.serializer(
                        typeInfo.reifiedType
                    ), element = it as JsonElement
                ) as T
            }

            this.body != null -> this.body as T
            isNullable -> call.receiveNullable<T>(typeInfo)
            else -> call.receive(typeInfo) as T
        }

        this.body = requestBody

        return requestBody
    }

    private suspend fun <T : Any> extractPart(
        name: String,
        typeInfo: TypeInfo,
        isNullable: Boolean
    ): T? {
        val map = getRequestBodyMultiPartDataMap()

        return map[name].extractPart(
            name = name,
            annotationName = "@Part",
            typeInfo = typeInfo,
            isNullable = isNullable
        )
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <T : Any> PartData?.extractPart(
        name: String,
        annotationName: String,
        typeInfo: TypeInfo,
        isNullable: Boolean
    ): T? = when (val part = this) {
        is PartData.FormItem -> {
            when (typeInfo.type) {
                PartData.FormItem::class -> part as T
                PartData::class -> part as T
                String::class -> part.value as T
                else -> DefaultConversionService.fromValues(values = listOf(part.value), type = typeInfo) as T
            }
        }

        is PartData.FileItem -> {
            when (typeInfo.type) {
                PartData.FileItem::class -> part as T
                PartData::class -> part as T
                InputStream::class -> part.asInputStream() as T
                Input::class -> part.asInput() as T
                ByteReadChannel::class -> part.asByteReadChannel() as T
                ByteArray::class -> part.asByteArray() as T
                else -> throw ParameterConversionException(
                    parameterName = name,
                    type = typeInfo.type.simpleName ?: typeInfo.type.toString(),
                    cause = IllegalArgumentException("Unsupported type for `$annotationName` annotated parameter.")
                )
            }
        }

        is PartData.BinaryItem -> {
            when (typeInfo.type) {
                PartData.BinaryItem::class -> part as T
                PartData::class -> part as T
                InputStream::class -> part.asInputStream() as T
                Input::class -> part.asInput() as T
                ByteReadChannel::class -> part.asByteReadChannel() as T
                ByteArray::class -> part.asByteArray() as T
                else -> throw ParameterConversionException(
                    parameterName = name,
                    type = typeInfo.type.simpleName ?: typeInfo.type.toString(),
                    cause = IllegalArgumentException("Unsupported type for `$annotationName` annotated parameter.")
                )
            }
        }

        is PartData.BinaryChannelItem -> {
            when (typeInfo.type) {
                PartData.BinaryChannelItem::class -> part as T
                PartData::class -> part as T
                InputStream::class -> part.asInputStream() as T
                Input::class -> part.asInput() as T
                ByteReadChannel::class -> part.asByteReadChannel() as T
                ByteArray::class -> part.asByteArray() as T
                else -> throw ParameterConversionException(
                    parameterName = name,
                    type = typeInfo.type.simpleName ?: typeInfo.type.toString(),
                    cause = IllegalArgumentException("Unsupported type for `$annotationName` annotated parameter.")
                )
            }
        }

        null -> if (isNullable) null else error("`$annotationName` annotated parameter was `null` when type was expected to be non-null.")
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <T : Any> extractField(
        name: String,
        typeInfo: TypeInfo,
        isNullable: Boolean
    ): T? = when (val body = getRequestFieldBody()) {
        is Parameters -> if (isNullable) {
            body.getOrNull(name = name, typeInfo = typeInfo)
        } else {
            body.getOrFail(name = name, typeInfo = typeInfo)
        }

        is MultiPartData -> body.readAllParts().first { it.name == name }.extractPart(
            name = name,
            annotationName = "@Field",
            typeInfo = typeInfo,
            isNullable = isNullable
        )

        is JsonObject -> {
            val value = body[name]

            if (value == null && !isNullable) {
                error("")
            }

            val serializer = json.serializersModule.serializer(typeInfo.reifiedType) as KSerializer<T>

            value?.let { json.decodeFromJsonElement(deserializer = serializer, element = it) }
        }

        else -> error("Cannot retrieve `@Field` annotated parameter from unsupported request body content type: $requestBodyContentType. Use the `@Body` annotation instead.")
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> extractSupported(
        name: String,
        typeInfo: TypeInfo
    ): T = when (typeInfo.type) {
        Route::class -> route as T
        ApplicationCall::class -> route as T
        Unit::class -> Unit as T
        else -> error("Unsupported type for API function parameter `$name`. Make sure the parameter is annotated, is a supported type, or has a default value.")
    }

    private suspend fun getRequestBodyMultiPartDataMap(): Map<String?, PartData> {
        var map = multiPartDataMap

        if (map == null) {
            if (body != null && body is MultiPartData) {
                val data = body as MultiPartData
                map = data.readAllParts().associateBy { it.name }
                multiPartData = data
                multiPartDataMap = map
            } else {
                val data = call.receiveMultipart()
                map = data.readAllParts().associateBy { it.name }
                multiPartData = data
                multiPartDataMap = map
            }
        }

        return map
    }

    private suspend fun getRequestBodyMultiPartData(): MultiPartData {
        var data = multiPartData

        if (data == null) {
            if (body != null && body is MultiPartData) {
                val multiPart = body as MultiPartData
                data = multiPart
                multiPartData = data
                multiPartDataMap = data.readAllParts().associateBy { it.name }
            } else {
                val multipart = call.receiveMultipart()
                data = multipart
                multiPartData = multipart
                multiPartDataMap = multipart.readAllParts().associateBy { it.name }
            }
        }

        return data
    }

    private suspend fun getRequestFieldBody(): Any {
        var fieldBody = fieldRequestBody

        if (fieldBody == null) {
            fieldBody =
                if (body != null && (requestBodyContentType == "application/x-www-form-urlencoded" && body is Parameters)) {
                    body as Parameters
                } else if (body != null && (requestBodyContentType == "multipart/form-data" && body is MultiPartData)) {
                    body as MultiPartData
                } else if (body != null && requestBodyContentType?.startsWith("application/json") == true) {
                    body!!
                } else {
                    when {
                        requestBodyContentType == null -> error("Cannot retrieve `@Field` annotated parameter from unsupported request body content type: `null`. Use the `@Body` annotation instead.")
                        requestBodyContentType == "application/x-www-form-urlencoded" -> call.receiveParameters()
                        requestBodyContentType == "multipart/form-data" -> call.receiveMultipart()
                        requestBodyContentType.startsWith("application/json") -> call.receive<JsonElement>()
                        else -> error("Cannot retrieve `@Field` annotated parameter from unsupported request body content type: $requestBodyContentType. Use the `@Body` annotation instead.")
                    }
                }

            fieldRequestBody = fieldBody
        }

        return fieldBody
    }

    private suspend fun getRequestBodyParameters(): Parameters {
        val body = getRequestFieldBody()

        if (body !is Parameters) {
            error("Cannot retrieve `io.ktor.http.Parameters` as request body for content type $requestBodyContentType.")
        }

        return body
    }
}

@ExperimentalServerApi
fun defaultHttpRequestParameterExtractorFactory(
    json: Json = Json.Default,
    conversionService: ConversionService = ExtendedDefaultConversionService
): HttpRequestParameterExtractor.Factory =
    HttpRequestParameterExtractor.Factory { route, call, requestBodyContentType ->
        DefaultHttpRequestParameterExtractor(
            route = route,
            call = call,
            requestBodyContentType = requestBodyContentType,
            json = json,
            conversionService = conversionService
        )
    }
