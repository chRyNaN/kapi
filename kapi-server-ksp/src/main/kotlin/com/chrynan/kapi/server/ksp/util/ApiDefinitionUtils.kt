package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.core.annotation.Api
import com.chrynan.kapi.server.processor.core.model.ApiDefinition
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSClassDeclaration

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
