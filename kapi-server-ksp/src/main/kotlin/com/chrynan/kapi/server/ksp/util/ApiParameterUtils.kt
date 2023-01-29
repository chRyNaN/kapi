package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.server.core.annotation.*
import com.chrynan.kapi.server.processor.core.*
import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.Nullability

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

    val type = this.type.resolve()

    val name = this.name?.asString() ?: ""
    val typeName = type.declaration.kotlinName
    val isNullable = type.nullability != Nullability.NOT_NULL
    val annotations = this.annotations.map { it.toKotlinAnnotation() }.toList()

    return when {
        path != null -> PathParameter(
            name = name,
            typeName = typeName,
            isNullable = isNullable,
            hasDefaultValue = this.hasDefault,
            isVararg = this.isVararg,
            annotations = annotations,
            value = path.value,
            encoded = path.encoded
        )

        query != null -> QueryParameter(
            name = name,
            typeName = typeName,
            isNullable = isNullable,
            hasDefaultValue = this.hasDefault,
            isVararg = this.isVararg,
            annotations = annotations,
            value = query.value,
            encoded = query.encoded
        )

        field != null -> FieldParameter(
            name = name,
            typeName = typeName,
            isNullable = isNullable,
            hasDefaultValue = this.hasDefault,
            isVararg = this.isVararg,
            annotations = annotations,
            value = field.value,
            encoded = field.encoded
        )

        part != null -> PartParameter(
            name = name,
            typeName = typeName,
            isNullable = isNullable,
            hasDefaultValue = this.hasDefault,
            isVararg = this.isVararg,
            annotations = annotations,
            value = part.value,
            encoding = part.encoding
        )

        header != null -> HeaderParameter(
            name = name,
            typeName = typeName,
            isNullable = isNullable,
            hasDefaultValue = this.hasDefault,
            isVararg = this.isVararg,
            annotations = annotations,
            value = header.value
        )

        body != null -> BodyParameter(
            name = name,
            typeName = typeName,
            isNullable = isNullable,
            hasDefaultValue = this.hasDefault,
            isVararg = this.isVararg,
            annotations = annotations,
            documentation = type.declaration.docString
        )

        this.hasDefault -> DefaultParameter(
            name = name,
            typeName = typeName,
            isNullable = isNullable,
            isVararg = this.isVararg,
            annotations = annotations
        )

        else -> error("Unsupported API function parameter type.")
    }
}
