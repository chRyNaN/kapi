package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.core.HttpMethod
import com.chrynan.kapi.server.core.annotation.*
import com.chrynan.kapi.server.processor.core.model.ApiFunction
import com.chrynan.kapi.server.processor.core.model.ApiRequestBodyType
import com.chrynan.kapi.server.processor.core.model.ApiResponseBody
import com.chrynan.kapi.server.processor.core.model.BodyParameter
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.symbol.Nullability

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
            annotation.isOfType(com.chrynan.kapi.core.annotation.DELETE::class) -> delete = annotation.toAnnotation(com.chrynan.kapi.core.annotation.DELETE::class)
            annotation.isOfType(com.chrynan.kapi.core.annotation.GET::class) -> get = annotation.toAnnotation(com.chrynan.kapi.core.annotation.GET::class)
            annotation.isOfType(com.chrynan.kapi.core.annotation.HEAD::class) -> head = annotation.toAnnotation(com.chrynan.kapi.core.annotation.HEAD::class)
            annotation.isOfType(com.chrynan.kapi.core.annotation.OPTIONS::class) -> options = annotation.toAnnotation(
                com.chrynan.kapi.core.annotation.OPTIONS::class)
            annotation.isOfType(com.chrynan.kapi.core.annotation.PATCH::class) -> patch = annotation.toAnnotation(com.chrynan.kapi.core.annotation.PATCH::class)
            annotation.isOfType(com.chrynan.kapi.core.annotation.POST::class) -> post = annotation.toAnnotation(com.chrynan.kapi.core.annotation.POST::class)
            annotation.isOfType(com.chrynan.kapi.core.annotation.PUT::class) -> put = annotation.toAnnotation(com.chrynan.kapi.core.annotation.PUT::class)
            annotation.isOfType(com.chrynan.kapi.core.annotation.FormUrlEncoded::class) -> formUrlEncoded =
                annotation.toAnnotation(com.chrynan.kapi.core.annotation.FormUrlEncoded::class)

            annotation.isOfType(com.chrynan.kapi.core.annotation.Multipart::class) -> multipart = annotation.toAnnotation(
                com.chrynan.kapi.core.annotation.Multipart::class)
            annotation.isOfType(com.chrynan.kapi.core.annotation.Headers::class) -> headers = annotation.toAnnotation(
                com.chrynan.kapi.core.annotation.Headers::class)
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
    val responseBody = this.returnType?.resolve()?.let {
        ApiResponseBody(
            typeName = it.declaration.kotlinName,
            isNullable = it.nullability != Nullability.NOT_NULL,
            documentation = it.declaration.docString
        )
    }
    val isApplicationCallExtension =
        this.extensionReceiver?.resolve()?.declaration?.kotlinName?.full == "io.ktor.server.application.ApplicationCall"

    return ApiFunction(
        name = functionName,
        documentation = this.docString,
        method = httpMethod,
        path = path,
        requestBodyType = requestBodyType,
        responseBody = responseBody,
        parameters = parameters,
        headers = headers?.values?.toList() ?: emptyList(),
        isApplicationCallExtension = isApplicationCallExtension,
        isSuspending = isSuspending,
        annotations = annotations
    )
}
