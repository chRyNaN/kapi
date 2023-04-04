package com.chrynan.kapi.server.ksp.output

import com.chrynan.kapi.openapi.*
import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import com.chrynan.kapi.server.ksp.util.*
import com.chrynan.kapi.server.ksp.util.apiName
import com.chrynan.kapi.server.ksp.util.simpleName
import com.chrynan.kapi.server.processor.core.model.*
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.asTypeName

@ExperimentalServerApi
internal class OpenApiDefinitionConverter(
    private val functionConverter: OpenApiFunctionConverter
) {

    operator fun invoke(api: ApiDefinition) {


    }

    private fun ApiDefinition.toOpenApiInstantiationCodeBlock(): CodeBlock {
        val builder = CodeBlock.builder()

        builder.addStatement("%T(")
            .indent()



        return builder.unindent()
            .addStatement(")")
            .build()
    }

    private fun CodeBlock.Builder.appendInfoInstantiation(api: ApiDefinition) {
        val builder = this

        builder.add(
            """
            |%T(
            |  %L = %S,
            |  %L = %L,
            |  %L = %L,
            |  %L = %L,
            |  %L = %L,
            """.trimMargin(),
            Info::class.asTypeName(),
            Info::title.simpleName,
            api.info?.title ?: api.apiName,
            Info::summary.simpleName,
            api.info?.summary?.let { "\"$it\"" },
            Info::description.simpleName,
            api.documentation?.let { "\"$it\"" },
            Info::termsOfService.simpleName,
            api.info?.termsOfService?.let { "\"$it\"" },
            Info::version.simpleName,
            api.info?.version?.let { "\"$it\"" }
        )

        api.info?.contact?.let { contact ->
            builder.add("\n")
            builder.add(
                """
                |%T(
                |  %L = %L,
                |  %L = %L,
                |  %L = %L
                |)
                """.trimMargin(),
                Contact::class.asTypeName(),
                Contact::name.simpleName,
                contact.name?.let { "\"$it\"" },
                Contact::url.simpleName,
                contact.url?.let { "\"$it\"" },
                Contact::email.simpleName,
                contact.email?.let { "\"$it\"" }
            )
        }

        api.info?.license?.let { license ->
            builder.add("\n")
            builder.add(
                """
                |%T(
                |  %L = %S,
                |  %L = %L,
                |  %L = %L
                |)
                """.trimMargin(),
                License::class.asTypeName(),
                License::name.simpleName,
                license.name,
                License::url.simpleName,
                license.url?.let { "\"$it\"" },
                License::identifier.simpleName,
                license.identifier?.let { "\"$it\"" }
            )

            builder.add(")")
        }
    }

    private fun CodeBlock.Builder.appendTags(api: ApiDefinition) {
        val builder = this

        if (api.tags.isEmpty()) {
            builder.add(
                "%M()",
                MemberName(packageName = "kotlin.collections", simpleName = "emptyList", isExtension = false)
            )

            return
        }

        builder.add(
            "%M(\n",
            MemberName(packageName = "kotlin.collections", simpleName = "listOf", isExtension = false)
        )

        api.tags.forEachIndexed { index, tag ->
            builder.add(
                "%T(%L = %S, %L = %L)%L\n",
                Tag::class.asTypeName(),
                Tag::name.simpleName,
                tag.name,
                Tag::description.simpleName,
                tag.description?.let { "\"$it\"" },
                if (index == api.tags.lastIndex) "" else ","
            )
        }

        builder.add(")")
    }

    private fun CodeBlock.Builder.appendServers(api: ApiDefinition) {
        val builder = this

        if (api.servers.isEmpty()) {
            builder.add(
                "%M()",
                MemberName(packageName = "kotlin.collections", simpleName = "emptyList", isExtension = false)
            )

            return
        }

        builder.add(
            "%M(\n",
            MemberName(packageName = "kotlin.collections", simpleName = "listOf", isExtension = false)
        )

        api.servers.forEachIndexed { index, server ->
            builder.add(
                "%T(%L = %S, %L = %L,",
                Server::class.asTypeName(),
                Server::url.simpleName,
                server.url,
                Server::description.simpleName,
                server.description?.let { "\"$it\"" },
                if (index == api.servers.lastIndex) "" else ","
            )

            if (server.variables.isNotEmpty()) {
                builder.add(
                    "%L = %M(\n",
                    Server::variables.simpleName,
                    MemberName(packageName = "kotlin.collections", simpleName = "mapOf", isExtension = false)
                )

                server.variables.forEach { variable ->
                    builder.addStatement(
                        "%S %M %T(",
                        variable.name,
                        MemberName(packageName = "kotlin", simpleName = "to", isExtension = true),
                        ServerVariable::class.asTypeName()
                    ).indent()

                    builder.add("%L = %S,", ServerVariable::default.simpleName, variable.defaultValue)

                    if (variable.allowableValues.isNotEmpty()) {
                        builder.addStatement(
                            "%L = %M(%L),",
                            ServerVariable::enum.simpleName,
                            MemberName(packageName = "kotlin.collections", simpleName = "listOf", isExtension = false),
                            variable.allowableValues.joinToString { "\"$it\"" }
                        )
                    }

                    builder.add(
                        "%L = %L",
                        ServerVariable::description.simpleName,
                        variable.description?.let { "\"$it\"" })

                    builder.unindent()
                        .addStatement(")")
                }

                builder.add(")")
            }

            builder.add(")")
        }

        builder.add(")")
    }

    private fun CodeBlock.Builder.appendResponsesInstantiation(
        function: ApiFunction
    ) {
        val builder = this

        builder.add("%T(\n", Responses::class.asTypeName())
            .indent()
            .add(
                "%L = %M(\n",
                Responses::responses.simpleName,
                MemberName(packageName = "kotlin.collections", simpleName = "mapOf", isExtension = false)
            )
            .indent()

        val default = function.successResponse ?: ApiResponse.Success(statusCode = 200, description = null)

        builder.add(
            "%S %M %M(\n",
            default.contentType ?: "*/*",
            MemberName(packageName = "kotlin", simpleName = "to", isExtension = false),
            MemberName(packageName = "com.chrynan.kapi.openapi", simpleName = "ReferenceOrType", isExtension = false)
        ).appendResponseInstantiation(response = default, kotlinReturnType = function.kotlinFunction.returnType)
        builder.add("),\n")

        function.errorResponses.forEach { response ->
            builder.add(
                "%S %M %M(\n",
                response.contentType ?: "*/*",
                MemberName(packageName = "kotlin", simpleName = "to", isExtension = false),
                MemberName(
                    packageName = "com.chrynan.kapi.openapi",
                    simpleName = "ReferenceOrType",
                    isExtension = false
                )
            ).appendResponseInstantiation(response = default, kotlinReturnType = null)
            builder.add("),\n")
        }

        builder.unindent()
            .add(")\n")
            .unindent()
            .add(")\n")
    }

    private fun CodeBlock.Builder.appendResponseInstantiation(
        response: ApiResponse,
        kotlinReturnType: KotlinTypeUsage?
    ) {
        val builder = this

        if (
            kotlinReturnType == null ||
            kotlinReturnType.isUnit ||
            kotlinReturnType.isNothing ||
            (kotlinReturnType.isResponse && kotlinReturnType.typeArguments.first().type?.isUnit == true) ||
            (kotlinReturnType.isResponse && kotlinReturnType.typeArguments.first().type?.isNothing == true)
        ) {
            builder.add("null")
        } else {
            val description = response.description ?: response.statusCode.toString()
            val contentType = response.contentType ?: "*/*"

            builder.addStatement("%T(", Response::class.asTypeName())
                .indent()

            builder.addStatement("%L = %S,", Response::description.simpleName, description)

            builder.add(
                "%L = %M(%S %M %T(%L = ${PROPERTY_NAME_SERIALIZERS_MODULE}.%M<%T>())",
                Response::content.simpleName,
                MemberName(packageName = "kotlin.collections", simpleName = "mapOf", isExtension = false),
                contentType,
                MemberName(packageName = "kotlin", simpleName = "to", isExtension = false),
                MediaType::class.asTypeName(),
                MediaType::schema.simpleName,
                MemberName(packageName = "com.chrynan.kapi.openapi", simpleName = "openApiSchema", isExtension = false),
                kotlinReturnType.typeName
            )

            builder.unindent()
                .addStatement(")")
        }
    }

    companion object {

        private const val PROPERTY_NAME_SERIALIZERS_MODULE = "serializersModule"
    }
}
