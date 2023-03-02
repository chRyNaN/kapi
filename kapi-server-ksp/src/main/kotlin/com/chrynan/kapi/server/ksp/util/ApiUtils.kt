package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.core.HttpMethod
import com.chrynan.kapi.core.annotation.Api
import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.Modifier

@OptIn(KspExperimental::class)
internal fun KSClassDeclaration.toApiDefinition(): ApiDefinition {
    val api = this.getAnnotationsByType(Api::class).firstOrNull()
        ?: error("API definition must be annotated with the Api annotation.")

    return ApiDefinition(
        name = api.name,
        basePath = api.basePath.takeIf { it.isNotBlank() },
        version = api.version.takeIf { it.isNotBlank() },
        typeName = this.kotlinName,
        documentation = this.docString,
        functions = this.getAllFunctions().map { it.toApiFunction() }.filterNotNull().toList(),
        annotations = this.annotations.map { it.toKotlinAnnotation() }.toList()
    )
}

internal fun KSFunctionDeclaration.toApiFunction(): ApiFunction? {
    val functionName = this.kotlinName.full

    var delete: com.chrynan.kapi.core.annotation.DELETE? = null
    var get: com.chrynan.kapi.core.annotation.GET? = null
    var head: com.chrynan.kapi.core.annotation.HEAD? = null
    var options: com.chrynan.kapi.core.annotation.OPTIONS? = null
    var patch: com.chrynan.kapi.core.annotation.PATCH? = null
    var post: com.chrynan.kapi.core.annotation.POST? = null
    var put: com.chrynan.kapi.core.annotation.PUT? = null

    var formUrlEncoded: com.chrynan.kapi.core.annotation.FormUrlEncoded? = null
    var multipart: com.chrynan.kapi.core.annotation.Multipart? = null

    var headers: com.chrynan.kapi.core.annotation.Headers? = null

    this.annotations.forEach { annotation ->
        when {
            annotation.isOfType(com.chrynan.kapi.core.annotation.DELETE::class) -> delete =
                annotation.toAnnotation(com.chrynan.kapi.core.annotation.DELETE::class)

            annotation.isOfType(com.chrynan.kapi.core.annotation.GET::class) -> get =
                annotation.toAnnotation(com.chrynan.kapi.core.annotation.GET::class)

            annotation.isOfType(com.chrynan.kapi.core.annotation.HEAD::class) -> head =
                annotation.toAnnotation(com.chrynan.kapi.core.annotation.HEAD::class)

            annotation.isOfType(com.chrynan.kapi.core.annotation.OPTIONS::class) -> options = annotation.toAnnotation(
                com.chrynan.kapi.core.annotation.OPTIONS::class
            )

            annotation.isOfType(com.chrynan.kapi.core.annotation.PATCH::class) -> patch =
                annotation.toAnnotation(com.chrynan.kapi.core.annotation.PATCH::class)

            annotation.isOfType(com.chrynan.kapi.core.annotation.POST::class) -> post =
                annotation.toAnnotation(com.chrynan.kapi.core.annotation.POST::class)

            annotation.isOfType(com.chrynan.kapi.core.annotation.PUT::class) -> put =
                annotation.toAnnotation(com.chrynan.kapi.core.annotation.PUT::class)

            annotation.isOfType(com.chrynan.kapi.core.annotation.FormUrlEncoded::class) -> formUrlEncoded =
                annotation.toAnnotation(com.chrynan.kapi.core.annotation.FormUrlEncoded::class)

            annotation.isOfType(com.chrynan.kapi.core.annotation.Multipart::class) -> multipart =
                annotation.toAnnotation(
                    com.chrynan.kapi.core.annotation.Multipart::class
                )

            annotation.isOfType(com.chrynan.kapi.core.annotation.Headers::class) -> headers = annotation.toAnnotation(
                com.chrynan.kapi.core.annotation.Headers::class
            )
        }
    }

    check(listOfNotNull(delete, get, head, options, patch, post, put).size <= 1) {
        "Only one of the following annotations is allowed for each API function: DELETE, GET, HEAD, OPTIONS, PATCH, POST, PUT. Function: $functionName"
    }

    val httpMethod = when {
        delete != null -> HttpMethod.DELETE
        get != null -> HttpMethod.GET
        head != null -> HttpMethod.HEAD
        options != null -> HttpMethod.OPTIONS
        patch != null -> HttpMethod.PATCH
        post != null -> HttpMethod.POST
        put != null -> HttpMethod.PUT
        else -> null
    } ?: return null
    val path =
        (delete?.value ?: get?.value ?: head?.value ?: options?.value ?: patch?.value ?: post?.value ?: put?.value)
            ?: return null
    val parameters = this.parameters.map { it.toApiParameter(functionName = functionName) }
    val annotations = this.annotations.map { it.toKotlinAnnotation() }.toList()
    val isSuspending = this.modifiers.contains(Modifier.SUSPEND)
    val requestBodyType = when {
        formUrlEncoded != null -> ApiRequestBodyType.FORM_URL_ENCODED
        multipart != null -> ApiRequestBodyType.MULTIPART
        parameters.any { it is BodyParameter } -> ApiRequestBodyType.CONTENT_NEGOTIATION
        else -> ApiRequestBodyType.NONE
    }
    val responseBody = this.returnType?.toKotlinTypeUsage()?.let { ApiResponseBody(type = it) }

    return ApiFunction(
        name = functionName,
        documentation = this.docString,
        method = httpMethod,
        path = path,
        requestBodyType = requestBodyType,
        responseBody = responseBody,
        extensionReceiver = this.extensionReceiver?.toKotlinTypeUsage(),
        parameters = parameters,
        headers = headers?.values?.toList() ?: emptyList(),
        isSuspending = isSuspending,
        annotations = annotations
    )
}

/**
 * Converts this [KSValueParameter] to an [ApiParameter] model.
 */
@OptIn(KspExperimental::class)
internal fun KSValueParameter.toApiParameter(functionName: String): ApiParameter {
    val path = this.getAnnotationsByType(com.chrynan.kapi.core.annotation.Path::class).firstOrNull()
    val query = this.getAnnotationsByType(com.chrynan.kapi.core.annotation.Query::class).firstOrNull()
    val field = this.getAnnotationsByType(com.chrynan.kapi.core.annotation.Field::class).firstOrNull()
    val part = this.getAnnotationsByType(com.chrynan.kapi.core.annotation.Part::class).firstOrNull()
    val header = this.getAnnotationsByType(com.chrynan.kapi.core.annotation.Header::class).firstOrNull()
    val body = this.getAnnotationsByType(com.chrynan.kapi.core.annotation.Body::class).firstOrNull()

    check(listOfNotNull(path, query, field, part, header, body).size <= 1) {
        "Only one of the following annotations is allowed for each API function parameter: 'Path', 'Query', 'Field', 'Part', 'Header', and 'Body'. Function: $functionName; Parameter: ${this.name?.asString()}"
    }

    return when {
        path != null -> PathParameter(
            declaration = this.toKotlinParameterDeclaration(),
            value = path.value,
            encoded = path.encoded
        )

        query != null -> QueryParameter(
            declaration = this.toKotlinParameterDeclaration(),
            value = query.value,
            encoded = query.encoded
        )

        field != null -> FieldParameter(
            declaration = this.toKotlinParameterDeclaration(),
            value = field.value,
            encoded = field.encoded
        )

        part != null -> PartParameter(
            declaration = this.toKotlinParameterDeclaration(),
            value = part.value,
            encoding = part.encoding
        )

        header != null -> HeaderParameter(
            declaration = this.toKotlinParameterDeclaration(),
            value = header.value
        )

        body != null -> BodyParameter(declaration = this.toKotlinParameterDeclaration())

        this.hasDefault -> DefaultParameter(declaration = this.toKotlinParameterDeclaration())

        else -> error("Unsupported API function parameter type.")
    }
}
