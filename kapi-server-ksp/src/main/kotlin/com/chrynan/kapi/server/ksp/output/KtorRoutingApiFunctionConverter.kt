@file:OptIn(ExperimentalServerApi::class, ExperimentalServerApi::class)

package com.chrynan.kapi.server.ksp.output

import com.chrynan.kapi.core.HttpMethod
import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import com.chrynan.kapi.server.core.util.HttpRequestParameterExtractor
import com.chrynan.kapi.server.core.util.ParameterAnnotationType
import com.chrynan.kapi.server.ksp.util.addStatement
import com.chrynan.kapi.server.ksp.util.controlFlow
import com.chrynan.kapi.server.ksp.util.throwError
import com.chrynan.kapi.server.ksp.util.typeName
import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.processing.KSPLogger
import com.squareup.kotlinpoet.*

internal class KtorRoutingApiFunctionConverter(
    private val propertyNameApi: String,
    private val propertyNameParameterExtractorFactory: String,
    private val logger: KSPLogger
) {

    private val applicationCallMemberName =
        MemberName(packageName = "io.ktor.server.application", simpleName = "call", isExtension = true)

    operator fun invoke(
        function: ApiFunction,
        basePath: String? = null,
        parentAuths: List<ApiAuth>
    ): FunSpec = function.create {
        baseRoute(basePath) {
            authentications(parentAuths + function.auths) {
                httpMethod(function = function) {
                    catchAnyErrors(function) {
                        invokeApiFunction(function = function)
                    }
                }
            }
        }
    }

    private fun ApiFunction.create(block: CodeBlock.Builder.(function: ApiFunction) -> Unit): FunSpec {
        val builder = FunSpec.builder(name = this.kotlinFunction.name.short)
            .addModifiers(KModifier.PRIVATE)
            .receiver(ClassName.bestGuess("io.ktor.server.routing.Route"))

        val codeBlockBuilder = CodeBlock.builder()
            .addStatement("val $propertyNameRoute = this")
            .add("\n")

        codeBlockBuilder.block(this)

        builder.addCode(codeBlockBuilder.build())

        return builder.build()
    }

    private fun CodeBlock.Builder.authentications(
        apiAuths: List<ApiAuth>,
        block: CodeBlock.Builder.() -> Unit
    ): CodeBlock.Builder {
        val builder = this

        if (apiAuths.isNotEmpty()) {
            val auth = apiAuths.first()
            val updatedAuths = apiAuths.drop(1)

            authenticate(auth) {
                authentications(updatedAuths, block)
            }
        } else {
            builder.block()
        }

        return builder
    }

    private fun CodeBlock.Builder.baseRoute(
        basePath: String?,
        block: CodeBlock.Builder.() -> Unit
    ): CodeBlock.Builder {
        val builder = this

        if (basePath.isNullOrBlank()) {
            builder.block()
        } else {
            builder.beginControlFlow(
                "$propertyNameRoute.%M(%S)",
                MemberName(packageName = "io.ktor.server.routing", simpleName = "route", isExtension = true),
                basePath
            )

            builder.block()

            builder.endControlFlow()
        }

        return builder
    }

    private fun CodeBlock.Builder.httpMethod(
        function: ApiFunction,
        block: CodeBlock.Builder.() -> Unit
    ): CodeBlock.Builder {
        val builder = this
        val acceptContentType = function.requestContentType
        val surroundInAccept = !acceptContentType.isNullOrBlank() && acceptContentType != "*/*"

        if (surroundInAccept) {
            builder.beginControlFlow(
                "$propertyNameRoute.%M(%T.parse(%L))",
                MemberName(packageName = "io.ktor.server.routing", simpleName = "accept", isExtension = true),
                ClassName.bestGuess("io.ktor.http.ContentType"),
                acceptContentType?.let { "\"$it\"" }
            )
        }

        val httpFunctionName = when (function.method) {
            HttpMethod.GET -> "get"
            HttpMethod.POST -> "post"
            HttpMethod.PUT -> "put"
            HttpMethod.PATCH -> "patch"
            HttpMethod.DELETE -> "delete"
            HttpMethod.HEAD -> "head"
            else -> logger.throwError(message = "Unsupported HTTP method ${function.method} for API function ${function.kotlinFunction.name.full}")
        }

        builder.beginControlFlow(
            "%M(path = %S)",
            MemberName(packageName = "io.ktor.server.routing", simpleName = httpFunctionName, isExtension = true),
            function.path
        )

        builder.addStatement("val $propertyNamePipeline = this")
            .add("\n")

        builder.block()

        builder.endControlFlow()

        if (surroundInAccept) {
            builder.endControlFlow()
        }

        return builder
    }

    private fun CodeBlock.Builder.invokeApiFunction(function: ApiFunction): CodeBlock.Builder {
        val builder = this

        val receiver = function.extensionReceiver
        val assignableParameters = function.parameters.filter { it !is DefaultValueParameter }

        return builder.controlFlow(
            value = "$propertyNameApi.%M",
            blockWithoutControlFlow = function.extensionReceiver == null,
            args = arrayOf(MemberName(packageName = "kotlin", simpleName = "apply", isExtension = true))
        ) {
            function.successResponse?.let { success ->
                addStatement(success.toResponseHeadersCodeBlock())

                if (success.headers.isNotEmpty()) {
                    add("\n")
                }
            }

            addStatement(
                """
                |val $propertyNameParameterExtractor = $propertyNameParameterExtractorFactory.invoke(
                |  route = $propertyNameRoute,
                |  call = $propertyNamePipeline.%M,
                |  requestBodyContentType = ${function.requestContentType?.let { "\"$it\"" }}
                |)
                """.trimMargin(),
                applicationCallMemberName
            )

            function.kotlinFunction.returnType?.let { returnType ->
                if (!returnType.isUnit && !returnType.isNothing) {
                    add("val $propertyNameResponseBody: %T = ", returnType.typeName)
                }
            }

            when {
                receiver == null -> builder.add("${propertyNameApi}.${function.kotlinFunction.name.short}(\n")
                receiver.isApplicationCall -> builder.add(
                    "$propertyNamePipeline.%M.${function.kotlinFunction.name.short}(\n",
                    applicationCallMemberName
                )

                receiver.isRoute -> builder.add("$propertyNameRoute.${function.kotlinFunction.name.short}(\n")
                else -> logger.throwError(message = "Unexpected extension receiver type ${receiver.name.full} for API function ${function.kotlinFunction.name.full}.")
            }
            indent()
            assignableParameters
                .mapIndexed { index, parameter ->
                    addParameterAssignment(
                        functionName = function.kotlinFunction.name.short,
                        parameter = parameter,
                        index = index,
                        lastIndex = assignableParameters.lastIndex
                    )
                }
            unindent()
            addStatement(")")

            function.kotlinFunction.returnType?.let { response ->
                if (!response.isUnit && !response.isNothing) {
                    add("\n")

                    if (response.isResponse) {
                        addStatement(
                            "$propertyNamePipeline.%M.%M(\nstatus = %T.fromValue(value = $propertyNameResponseBody.code()), \nmessage = $propertyNameResponseBody.body())",
                            applicationCallMemberName,
                            if (response.isNullable) {
                                MemberName(
                                    packageName = "io.ktor.server.response",
                                    simpleName = "respondNullable",
                                    isExtension = true
                                )
                            } else {
                                MemberName(
                                    packageName = "io.ktor.server.response",
                                    simpleName = "respond",
                                    isExtension = true
                                )
                            },
                            ClassName.bestGuess("io.ktor.http.HttpStatusCode")
                        )
                    } else {
                        addStatement(
                            "$propertyNamePipeline.%M.%M(\nstatus = %T.fromValue(value = %L), \nmessage = $propertyNameResponseBody)",
                            applicationCallMemberName,
                            if (response.isNullable) {
                                MemberName(
                                    packageName = "io.ktor.server.response",
                                    simpleName = "respondNullable",
                                    isExtension = true
                                )
                            } else {
                                MemberName(
                                    packageName = "io.ktor.server.response",
                                    simpleName = "respond",
                                    isExtension = true
                                )
                            },
                            ClassName.bestGuess("io.ktor.http.HttpStatusCode"),
                            function.successResponse?.statusCode ?: 200
                        )
                    }
                }
            }
        }
    }

    private fun ApiResponse.toResponseHeadersCodeBlock(): CodeBlock {
        val builder = CodeBlock.builder()

        this.headers.forEach { header ->
            if (header.onlyIfAbsent) {
                builder.addStatement(
                    "$propertyNamePipeline.%M.response.headers.%M(name = %S, value = %S, safeOnly = %L)",
                    applicationCallMemberName,
                    MemberName(
                        packageName = "io.ktor.server.response",
                        simpleName = "appendIfAbsent",
                        isExtension = true
                    ),
                    header.name,
                    header.value,
                    header.safeOnly
                )
            } else {
                builder.addStatement(
                    "$propertyNamePipeline.%M.response.headers.append(name = %S, value = %S, safeOnly = %L)",
                    applicationCallMemberName,
                    header.name,
                    header.value,
                    header.safeOnly
                )
            }
        }

        return builder.build()
    }

    private fun CodeBlock.Builder.addParameterAssignment(
        functionName: String,
        parameter: ApiParameter,
        index: Int,
        lastIndex: Int
    ): CodeBlock {
        val builder = this

        val isLast = index == lastIndex
        val parameterType = parameter.declaration.type
        val parameterName = parameter.declaration.name
        val parameterValue = parameter.value?.takeIf { it.isNotBlank() } ?: parameter.declaration.name

        add(
            """
                |$parameterName = $propertyNameParameterExtractor.extractParameter(
                |  name = %S,
                |  index = %L,
                |  annotationType = %T.%L,
                |  typeInfo = %M<%T>(),
                |  isNullable = %L
                |)
                """.trimMargin(),
            parameterValue,
            index,
            ParameterAnnotationType::class.asTypeName(),
            parameter.annotationType?.name ?: ParameterAnnotationType.SUPPORTED.name,
            MemberName(packageName = "io.ktor.util.reflect", simpleName = "typeInfo", isExtension = false),
            parameterType.typeName,
            parameterType.isNullable
        )

        if (!parameterType.isNullable) {
            add(
                " ?: %M(%S)",
                MemberName(packageName = "kotlin", simpleName = "error", isExtension = false),
                "Parameter `$parameterValue` for API function `$functionName` must not be `null`."
            )
        }

        if (!isLast) {
            add(",")
        }

        add("\n")

        return builder.build()
    }

    private fun CodeBlock.Builder.catchAnyErrors(
        function: ApiFunction,
        block: CodeBlock.Builder.() -> Unit
    ): CodeBlock.Builder {
        val builder = this

        if (function.errorResponses.isNotEmpty()) {
            builder.beginControlFlow("try")

            builder.block()

            function.errorResponses.forEach { error -> builder.catchError(error = error) }

            builder.endControlFlow()
        } else {
            builder.block()
        }

        return builder
    }

    private fun CodeBlock.Builder.catchError(error: ApiResponse.Error): CodeBlock.Builder {
        this.nextControlFlow("catch(e: %T)", error.exception.typeName)

        this.addStatement(error.toResponseHeadersCodeBlock())

        if (error.headers.isNotEmpty()) {
            add("\n")
        }

        return this.addStatement(
            """
            |$propertyNamePipeline.%M.%M(
            |    error = %T(
            |        type = "${error.value.type}",
            |        title = ${error.value.title.takeIf { it.isNotBlank() }?.let { "\"$it\"" } ?: "e.message ?: \"\""},
            |        details = ${error.value.details?.let { "\"$it\"" }},
            |        status = ${error.value.status},
            |        instance = ${error.value.instance?.let { "\"$it\"" }},
            |        timestamp = %T.now(),
            |        help = ${error.value.help?.let { "\"$it\"" }}))
            """.trimMargin(),
            applicationCallMemberName,
            MemberName(
                packageName = "com.chrynan.kapi.server.core.util",
                simpleName = "respondError",
                isExtension = true
            ),
            ClassName.bestGuess("com.chrynan.kapi.core.ApiError"),
            ClassName.bestGuess("kotlinx.datetime.Clock.System")
        )
    }

    private fun CodeBlock.Builder.authenticate(
        auth: ApiAuth?,
        block: CodeBlock.Builder.() -> Unit
    ): CodeBlock.Builder {
        val builder = this

        if (auth != null) {
            auth.requirements.forEach { requirement ->
                builder.beginControlFlow(
                    "%M(%S, strategy = %M)",
                    MemberName(packageName = "io.ktor.server.auth", simpleName = "authenticate", isExtension = true),
                    requirement.name,
                    MemberName(
                        enclosingClassName = ClassName.bestGuess("io.ktor.server.auth.AuthenticationStrategy"),
                        simpleName = if (auth.concatenation == ApiAuth.RequirementConcatenation.AND) "Required" else "FirstSuccessful",
                        isExtension = false
                    )
                )

                if (requirement.scopes.isNotEmpty()) {
                    builder.beginControlFlow(
                        """
                        |%M(
                        |  provider = %S,
                        |  scopes = %M<%T>(%L),
                        |  requirementPolicy = %M
                        |)
                        """.trimMargin(),
                        MemberName(
                            packageName = "com.chrynan.kapi.server.core.auth",
                            simpleName = "requireOauthScopes",
                            isExtension = true
                        ),
                        requirement.name,
                        MemberName(packageName = "kotlin.collections", simpleName = "setOf", isExtension = false),
                        String::class.asTypeName(),
                        requirement.scopes.joinToString { "\"$it\"" },
                        MemberName(
                            enclosingClassName = ClassName.bestGuess("com.chrynan.kapi.server.core.auth.ScopeRequirementPolicy"),
                            simpleName = if (requirement.scopeConcatenation == ApiAuth.RequirementConcatenation.AND) "ALL" else "ANY",
                            isExtension = false
                        )
                    )
                }
            }

            builder.block()

            auth.requirements.forEach { requirement ->
                builder.endControlFlow()

                if (requirement.scopes.isNotEmpty()) {
                    builder.endControlFlow()
                }
            }
        } else {
            builder.block()
        }

        return builder
    }

    companion object {

        private const val propertyNameRoute = "route"
        private const val propertyNamePipeline = "pipeline"
        private const val propertyNameResponseBody = "responseBody"
        private const val propertyNameParameterExtractor = "parameterExtractor"
    }
}
