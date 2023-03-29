package com.chrynan.kapi.server.ksp.output

import com.chrynan.kapi.openapi.*
import com.chrynan.kapi.server.ksp.util.typeName
import com.chrynan.kapi.server.processor.core.model.*
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal class OpenApiFunctionConverter {

    operator fun invoke(function: ApiFunction) {
        function.path to PathItem(
            get = Operation(

            ),
            parameters = listOf(
                ReferenceOrType(
                    Parameter(
                        name = "",
                        inValue = Parameter.InValue.QUERY,
                        schema = Schema()
                    )
                )
            )
        )
    }

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

    private fun ApiFunction.requestBodyCodeBlock(): CodeBlock {
        val builder = CodeBlock.builder()

        val body = this.bodyParameterOrNull()
        val contentType = this.requestContentType ?: "*/*"
        val description = body?.kotlinType?.declaration?.documentation?.takeIf { it.isNotBlank() }
        val isRequired = body?.kotlinType?.usage?.isNullable != true

        if (body == null) {
            builder.add("null")
            return builder.build()
        }

        return builder.addStatement(
            "%T(",
            ClassName.bestGuess("com.chrynan.kapi.openapi.ReferenceOrType")
        ).indent()
            .addStatement(
                "%T(",
                ClassName.bestGuess("com.chrynan.kapi.openapi.RequestBody")
            )
            .indent()
            .addStatement("description = %L,", description?.let { "\"$it\"" })
            .addStatement("required = %L,", isRequired)
            .addStatement(
                "content = %M(",
                MemberName(packageName = "kotlin.collections", simpleName = "mapOf", isExtension = false)
            )
            .indent()
            .addStatement(
                "$contentType %M %T(schema = ${PROPERTY_NAME_SERIALIZERS_MODULE}.%M<%T>())",
                MemberName(packageName = "kotlin", simpleName = "to", isExtension = true),
                ClassName.bestGuess("com.chrynan.kapi.openapi.MediaType"),
                MemberName(packageName = "com.chrynan.kapi.openapi", simpleName = "openApiSchema", isExtension = true),
                body.kotlinType.usage.typeName
            )
            .unindent()
            .addStatement(")")
            .unindent()
            .addStatement(")")
            .unindent()
            .addStatement(")")
            .build()
    }

    private fun ApiResponse.toResponseCodeBlock(kotlinReturnType: KotlinTypeUsage?): CodeBlock {
        val builder = CodeBlock.builder()

        val description = this.description.takeIf { it.isNotBlank() } ?: this.statusCode.toString()
        val contentType = if (this is ApiResponse.Success) {
            this.contentType.takeIf { it.isNotBlank() } ?: "*/*"
        } else {
            "application/json"
        }
        val content = if (
            kotlinReturnType == null ||
            kotlinReturnType.isUnit ||
            kotlinReturnType.isNothing ||
            (kotlinReturnType.isResponse && kotlinReturnType.typeArguments.first().type?.isUnit == true) ||
            (kotlinReturnType.isResponse && kotlinReturnType.typeArguments.first().type?.isNothing == true)
        ) {
            null
        } else {
            mapOf(
                contentType to MediaType(
                    schema = if (this is ApiResponse.Error) {
                        Schema()
                    } else {
                        Schema()
                    }
                )
            )
        }

        return builder.build()
    }

    private fun ApiParameter.toParameterCodeBlock(): CodeBlock {
        val builder = CodeBlock.builder()

        val name = this.value?.takeIf { it.isNotBlank() } ?: this.declaration.name
        val required = this is PathParameter || !this.declaration.hasDefaultValue

        builder.addStatement(
            "%T(",
            ClassName.bestGuess("com.chrynan.kapi.openapi.ReferenceOrType")
        ).indent()
            .addStatement(
                "%T(",
                ClassName.bestGuess("com.chrynan.kapi.openapi.Parameter")
            )
            .indent()
            .addStatement("name = %S,", name)

        when (this) {
            is PathParameter -> builder.addStatement(
                "inValue = %M.PATH,",
                MemberName(
                    enclosingClassName = ClassName.bestGuess("com.chrynan.kapi.openapi.Parameter"),
                    simpleName = "InValue",
                    isExtension = false
                )
            )

            is HeaderParameter -> builder.addStatement(
                "inValue = %M.HEADER,",
                MemberName(
                    enclosingClassName = ClassName.bestGuess("com.chrynan.kapi.openapi.Parameter"),
                    simpleName = "InValue",
                    isExtension = false
                )
            )

            else -> builder.addStatement(
                "inValue = %M.QUERY,",
                MemberName(
                    enclosingClassName = ClassName.bestGuess("com.chrynan.kapi.openapi.Parameter"),
                    simpleName = "InValue",
                    isExtension = false
                )
            )
        }

        builder.addStatement("required = %L,", required)
            .addStatement(
                "schema = ${PROPERTY_NAME_SERIALIZERS_MODULE}.%M { %M<%T>() }",
                MemberName(packageName = "kotlin", simpleName = "apply", isExtension = true),
                MemberName(packageName = "com.chrynan.kapi.openapi", simpleName = "openApiSchema", isExtension = true),
                this.declaration.type.typeName
            )

        builder.unindent()
            .addStatement(")")
            .unindent()
            .addStatement(")")

        return builder.build()
    }

    companion object {

        private const val PROPERTY_NAME_SERIALIZERS_MODULE = "serializersModule"
    }
}
