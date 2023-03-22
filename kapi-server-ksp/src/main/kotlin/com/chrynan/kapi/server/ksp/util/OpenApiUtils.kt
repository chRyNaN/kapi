package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.openapi.Operation
import com.chrynan.kapi.openapi.Parameter
import com.chrynan.kapi.openapi.ReferenceOrType
import com.chrynan.kapi.openapi.Schema
import com.chrynan.kapi.server.processor.core.model.*

private fun ApiFunction.toOperation(
    schemaResolver: (type: KotlinTyped) -> Schema?
): Operation {
    val operationParameters =
        this.parameters.filter { it is PathParameter || it is HeaderParameter || it is QueryParameter }
            .map { parameter ->
                Parameter(
                    name = parameter.value?.takeIf { it.isNotBlank() } ?: parameter.declaration.name,
                    inValue = when (parameter) {
                        is PathParameter -> Parameter.InValue.PATH
                        is HeaderParameter -> Parameter.InValue.HEADER
                        is QueryParameter -> Parameter.InValue.QUERY
                        else -> Parameter.InValue.QUERY
                    },
                    required = parameter is PathParameter || !parameter.declaration.hasDefaultValue,
                    schema = schemaResolver.invoke(parameter.declaration.type)
                )
            }.map { ReferenceOrType(it) }

    return Operation(
        operationId = this.kotlinFunction.name.short,
        description = this.kotlinFunction.documentation,
        deprecated = this.isDeprecated,
        parameters = operationParameters
    )
}

