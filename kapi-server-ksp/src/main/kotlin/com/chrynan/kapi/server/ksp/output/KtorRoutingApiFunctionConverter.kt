package com.chrynan.kapi.server.ksp.output

import com.chrynan.kapi.core.HttpMethod
import com.chrynan.kapi.server.ksp.util.addStatement
import com.chrynan.kapi.server.ksp.util.controlFlow
import com.chrynan.kapi.server.ksp.util.throwError
import com.chrynan.kapi.server.ksp.util.typeName
import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.processing.KSPLogger
import com.squareup.kotlinpoet.*

internal class KtorRoutingApiFunctionConverter(
    private val classPropertyNameApi: String,
    private val logger: KSPLogger
) {

    private val applicationCallMemberName =
        MemberName(packageName = "io.ktor.server.application", simpleName = "call", isExtension = true)

    operator fun invoke(
        function: ApiFunction,
        basePath: String? = null,
        parentAuths: List<ApiAuth>
    ): FunSpec {
        var declareMultipartData = false
        var declareMultipartDataMap = false
        var declareParameters = false

        function.parameters.forEach { parameter ->
            when {
                parameter is BodyParameter && parameter.declaration.type.isMultiPartData -> {
                    declareMultipartData = true
                }

                parameter is PartParameter -> {
                    declareMultipartData = true
                    declareMultipartDataMap = true
                }

                (parameter is BodyParameter && parameter.declaration.type.isParameters) || (parameter is FieldParameter) -> {
                    declareParameters = true
                }

                (parameter is SupportedTypeParameter && parameter.declaration.type.isParameters) -> {
                    declareParameters = true
                }

                (parameter is SupportedTypeParameter && parameter.declaration.type.isMultiPartData) -> {
                    declareMultipartData = true
                }
            }
        }

        return function.create {
            baseRoute(basePath) {
                authentications(parentAuths + function.auths) {
                    httpMethod(function = function) {
                        catchAnyErrors(function) {
                            addDefaultProperties(
                                declareMultipartData = declareMultipartData,
                                declareMultipartDataMap = declareMultipartDataMap,
                                declareParameters = declareParameters
                            )

                            invokeApiFunction(function = function)
                        }
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

    private fun CodeBlock.Builder.addDefaultProperties(
        declareMultipartData: Boolean,
        declareMultipartDataMap: Boolean,
        declareParameters: Boolean
    ): CodeBlock.Builder {
        if (declareMultipartData) {
            this.addStatement(
                "val $propertyNameMultipartData = $propertyNamePipeline.%M.%M()",
                applicationCallMemberName,
                MemberName(packageName = "io.ktor.server.request", simpleName = "receiveMultipart", isExtension = true)
            )
        }

        if (declareMultipartDataMap) {
            this.addStatement(
                "val $propertyNameMultipartDataMap = $propertyNameMultipartData.%M().%M { it.name }",
                MemberName(packageName = "io.ktor.http.content", simpleName = "readAllParts", isExtension = true),
                MemberName(packageName = "kotlin.collections", simpleName = "associateBy", isExtension = true)
            )
        }

        if (declareParameters) {
            this.addStatement(
                "val $propertyNameParameters = $propertyNamePipeline.%M.%M()",
                applicationCallMemberName,
                MemberName(packageName = "io.ktor.server.request", simpleName = "receiveParameters", isExtension = true)
            )
        }

        if (declareParameters || declareMultipartData || declareMultipartDataMap) {
            this.add("\n")
        }

        return this
    }

    private fun ApiParameter.toAssignmentDeclaration(function: ApiFunction): CodeBlock.Builder {
        val builder = CodeBlock.builder()

        val type = this.declaration.type
        val propertyName = this.declaration.name
        val parameterName = this.value?.takeIf { it.isNotBlank() } ?: this.declaration.name

        builder.add("$propertyName = ")

        when (this) {
            is PathParameter -> builder.add(
                "$propertyNamePipeline.%M.parameters.",
                applicationCallMemberName
            )

            is QueryParameter -> builder.add(
                "$propertyNamePipeline.%M.request.queryParameters.",
                applicationCallMemberName
            )

            is FieldParameter -> builder.add("${propertyNameParameters}.")
            is HeaderParameter -> builder.add(
                "$propertyNamePipeline.%M.request.headers.",
                applicationCallMemberName
            )

            else -> logger.throwError(message = "Unexpected parameter type ${type.name} for API function ${function.kotlinFunction.name.full}.")
        }

        when {
            type.isList -> builder.add(
                "%M(name = %S)",
                MemberName(
                    enclosingClassName = ClassName.bestGuess("io.ktor.util.StringValues"),
                    simpleName = "getAll",
                    isExtension = false
                ),
                parameterName
            )

            type.isSet -> builder.add(
                "%M(name = %S).%M()",
                MemberName(
                    enclosingClassName = ClassName.bestGuess("io.ktor.util.StringValues"),
                    simpleName = "getAll",
                    isExtension = false
                ),
                parameterName,
                MemberName(packageName = "kotlin.collections", simpleName = "toSet", isExtension = true)
            )

            type.isCollection -> builder.add(
                "%M(name = %S)",
                MemberName(
                    enclosingClassName = ClassName.bestGuess("io.ktor.util.StringValues"),
                    simpleName = "getAll",
                    isExtension = false
                ),
                parameterName
            )

            type.isArray -> builder.add(
                "%M(name = %S).%M()",
                MemberName(
                    enclosingClassName = ClassName.bestGuess("io.ktor.util.StringValues"),
                    simpleName = "getAll",
                    isExtension = false
                ),
                parameterName,
                MemberName(packageName = "kotlin.collections", simpleName = "toTypedArray", isExtension = true)
            )

            else -> builder.add(
                "%M<%T>(name = %S)",
                MemberName(
                    packageName = "com.chrynan.kapi.server.core.util",
                    simpleName = "getOrNull",
                    isExtension = true
                ),
                type.typeName,
                parameterName
            )
        }

        if (!type.isNullable) {
            builder.add(
                " \n?: %M(\"%L parameter value must be present and not null.\")",
                MemberName(packageName = "kotlin", simpleName = "error", isExtension = false),
                parameterName
            )
        }

        return builder
    }

    private fun PartParameter.toAssignmentDeclaration(function: ApiFunction): CodeBlock.Builder {
        val builder = CodeBlock.builder()

        val type = this.declaration.type
        val propertyName = this.declaration.name
        val parameterName = this.value.takeIf { it.isNotBlank() } ?: this.declaration.name
        val partDataGetter = "$propertyNameMultipartDataMap[\"$parameterName\"]"
        val formItemClassName = ClassName.bestGuess("io.ktor.http.content.PartData.FormItem")

        builder.add("$propertyName = ")

        when {
            type.isBoolean -> builder.add(
                "($partDataGetter as %T).value.%M()",
                formItemClassName,
                MemberName(packageName = "kotlin.text", simpleName = "toBoolean", isExtension = true)
            )

            type.isByte -> builder.add(
                "($partDataGetter as %T).value.%M()",
                formItemClassName,
                MemberName(packageName = "kotlin.text", simpleName = "toByte", isExtension = true)
            )

            type.isShort -> builder.add(
                "($partDataGetter as %T).value.%M()",
                formItemClassName,
                MemberName(packageName = "kotlin.text", simpleName = "toShort", isExtension = true)
            )

            type.isInt -> builder.add(
                "($partDataGetter as %T).value.%M()",
                formItemClassName,
                MemberName(packageName = "kotlin.text", simpleName = "toInt", isExtension = true)
            )

            type.isLong -> builder.add(
                "($partDataGetter as %T).value.%M()",
                formItemClassName,
                MemberName(packageName = "kotlin.text", simpleName = "toLong", isExtension = true)
            )

            type.isUByte -> builder.add(
                "($partDataGetter as %T).value.%M()",
                formItemClassName,
                MemberName(packageName = "kotlin.text", simpleName = "toUByte", isExtension = true)
            )

            type.isUShort -> builder.add(
                "($partDataGetter as %T).value.%M()",
                formItemClassName,
                MemberName(packageName = "kotlin.text", simpleName = "toUShort", isExtension = true)
            )

            type.isUInt -> builder.add(
                "($partDataGetter as %T).value.%M()",
                formItemClassName,
                MemberName(packageName = "kotlin.text", simpleName = "toUInt", isExtension = true)
            )

            type.isULong -> builder.add(
                "($partDataGetter as %T).value.%M()",
                formItemClassName,
                MemberName(packageName = "kotlin.text", simpleName = "toULong", isExtension = true)
            )

            type.isFloat -> builder.add(
                "($partDataGetter as %T).value.%M()",
                formItemClassName,
                MemberName(packageName = "kotlin.text", simpleName = "toFloat", isExtension = true)
            )

            type.isDouble -> builder.add(
                "($partDataGetter as %T).value.%M()",
                formItemClassName,
                MemberName(packageName = "kotlin.text", simpleName = "toDouble", isExtension = true)
            )

            type.isChar -> builder.add(
                "($partDataGetter as %T).value.%M()[0]",
                formItemClassName,
                MemberName(packageName = "io.ktor.util", simpleName = "toCharArray", isExtension = true)
            )

            type.isString -> builder.add("($partDataGetter as %T).value")
            type.isPartData && type.name.full.endsWith("FormItem") -> builder.add(
                "($partDataGetter as %T)",
                formItemClassName
            )

            type.isPartData && type.name.full.endsWith("FileItem") -> builder.add(
                "($partDataGetter as %T)",
                ClassName.bestGuess("io.ktor.http.content.PartData.FileItem")
            )

            type.isPartData && type.name.full.endsWith("BinaryItem") -> builder.add(
                "($partDataGetter as %T)",
                ClassName.bestGuess("io.ktor.http.content.PartData.BinaryItem")
            )

            type.isPartData && type.name.full.endsWith("BinaryChannelItem") -> builder.add(
                "($partDataGetter as %T)",
                ClassName.bestGuess("io.ktor.http.content.PartData.BinaryChannelItem")
            )

            type.isPartData -> builder.add(partDataGetter)
            type.isMultiPartData -> builder.add(propertyNameMultipartData)
            type.isInputStream -> builder.add(
                "$partDataGetter.%M()",
                MemberName(
                    packageName = "com.chrynan.kapi.server.core.util",
                    simpleName = "asInputStream",
                    isExtension = true
                )
            )

            type.isInput -> builder.add(
                "$partDataGetter.%M()",
                MemberName(
                    packageName = "com.chrynan.kapi.server.core.util",
                    simpleName = "asInput",
                    isExtension = true
                )
            )

            type.isByteReadChannel -> builder.add(
                "$partDataGetter.%M()",
                MemberName(
                    packageName = "com.chrynan.kapi.server.core.util",
                    simpleName = "asByteReadChannel",
                    isExtension = true
                )
            )

            type.isByteArray -> builder.add(
                "$partDataGetter.%M()",
                MemberName(
                    packageName = "com.chrynan.kapi.server.core.util",
                    simpleName = "asByteArray",
                    isExtension = true
                )
            )

            else -> logger.throwError(message = "Unexpected Part parameter type ${type.name.full} for API function ${function.kotlinFunction.name.full}.")
        }

        return builder
    }

    private fun BodyParameter.toAssignmentDeclaration(): CodeBlock.Builder {
        val builder = CodeBlock.builder()

        val type = this.declaration.type
        val propertyName = this.declaration.name

        builder.add("$propertyName = ")

        when {
            type.isNullable -> builder.add(
                "$propertyNamePipeline.%M.%M<%T>()",
                applicationCallMemberName,
                MemberName(packageName = "io.ktor.server.request", simpleName = "receiveNullable", isExtension = true),
                type.typeName
            )

            type.isString -> builder.add(
                "$propertyNamePipeline.%M.%M()",
                applicationCallMemberName,
                MemberName(packageName = "io.ktor.server.request", simpleName = "receiveText", isExtension = true)
            )

            type.isByteReadChannel -> builder.add(
                "$propertyNamePipeline.%M.%M()",
                applicationCallMemberName,
                MemberName(packageName = "io.ktor.server.request", simpleName = "receiveChannel", isExtension = true)
            )

            type.isInputStream -> builder.add(
                "$propertyNamePipeline.%M.%M()",
                applicationCallMemberName,
                MemberName(packageName = "io.ktor.server.request", simpleName = "receiveStream", isExtension = true)
            )

            type.isMultiPartData -> builder.add(propertyNameMultipartData)
            type.isParameters -> builder.add(propertyNameParameters)
            else -> builder.add(
                "$propertyNamePipeline.%M.%M<%T>()",
                applicationCallMemberName,
                MemberName(packageName = "io.ktor.server.request", simpleName = "receive", isExtension = true),
                type.typeName
            )
        }

        return builder
    }

    private fun PrincipalParameter.toAssignmentDeclaration(): CodeBlock.Builder {
        val builder = CodeBlock.builder()

        val type = this.declaration.type
        val propertyName = this.declaration.name
        val parameterName = this.value.takeIf { it.isNotBlank() } ?: this.declaration.name

        builder.add(
            "$propertyName = $applicationCallMemberName.%M<%T>(%S)",
            MemberName(packageName = "io.ktor.server.auth", simpleName = "Principal", isExtension = true),
            type.typeName,
            parameterName
        )

        return builder
    }

    private fun CodeBlock.Builder.invokeApiFunction(function: ApiFunction): CodeBlock.Builder {
        val builder = this

        val receiver = function.extensionReceiver
        val assignableParameters = function.parameters.filter { it !is DefaultValueParameter }

        return builder.controlFlow(
            value = "$classPropertyNameApi.%M",
            blockWithoutControlFlow = function.extensionReceiver == null,
            args = arrayOf(MemberName(packageName = "kotlin", simpleName = "apply", isExtension = true))
        ) {
            function.successResponse?.let { success ->
                addStatement(success.toResponseHeadersCodeBlock())

                if (success.headers.isNotEmpty()) {
                    add("\n")
                }
            }

            function.kotlinFunction.returnType?.let { returnType ->
                if (!returnType.isUnit && !returnType.isNothing) {
                    add("val $propertyNameResponseBody: %T = ", returnType.typeName)
                }
            }

            when {
                receiver == null -> builder.add("${classPropertyNameApi}.${function.kotlinFunction.name.short}(\n")
                receiver.isApplicationCall -> builder.add(
                    "$propertyNamePipeline.%M.${function.kotlinFunction.name.short}(\n",
                    applicationCallMemberName
                )

                receiver.isRoute -> builder.add("$propertyNameRoute.${function.kotlinFunction.name.short}(\n")
                else -> logger.throwError(message = "Unexpected Part extension receiver type ${receiver.name.full} for API function ${function.kotlinFunction.name.full}.")
            }
            indent()
            assignableParameters
                .mapIndexed { index, parameter ->
                    addParameterAssignment(
                        function = function,
                        parameter = parameter,
                        isLast = index == assignableParameters.lastIndex
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
        function: ApiFunction,
        parameter: ApiParameter,
        isLast: Boolean
    ): CodeBlock {
        val builder = this

        val parameterType = parameter.declaration.type
        val parameterAndPropertyName = parameter.declaration.name

        when (parameter) {
            is SupportedTypeParameter -> when {
                parameterType.isUnit -> builder.addStatement(
                    "$parameterAndPropertyName = %T${if (isLast) "" else ","}",
                    ClassName.bestGuess("kotlin.Unit")
                )

                parameterType.isApplicationCall -> builder.addStatement(
                    "$parameterAndPropertyName = $propertyNamePipeline.%M${if (isLast) "" else ","}",
                    applicationCallMemberName
                )

                parameterType.isRoute -> builder.addStatement("$parameterAndPropertyName = $propertyNameRoute${if (isLast) "" else ","}")
                parameterType.isParameters -> builder.addStatement("$parameterAndPropertyName = $propertyNameParameters${if (isLast) "" else ","}")
                parameterType.isMultiPartData -> builder.addStatement("$parameterAndPropertyName = $propertyNameMultipartData${if (isLast) "" else ","}")
                else -> logger.throwError(message = "Unexpected supported parameter type ${parameterType.name.full} for API function ${function.kotlinFunction.name.full}.")
            }

            is PartParameter -> builder.addStatement(
                parameter.toAssignmentDeclaration(function = function).add(if (isLast) "" else ",").build()
            )

            is BodyParameter -> builder.addStatement(
                parameter.toAssignmentDeclaration().add(if (isLast) "" else ",").build()
            )

            is PrincipalParameter -> builder.addStatement(
                parameter.toAssignmentDeclaration().add(if (isLast) "" else ",").build()
            )

            else -> builder.addStatement(
                parameter.toAssignmentDeclaration(function = function).add(if (isLast) "" else ",").build()
            )
        }

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

        private const val propertyNameMultipartData = "multipartData"
        private const val propertyNameMultipartDataMap = "multipartDataMap"
        private const val propertyNameParameters = "parameters"
        private const val propertyNameRoute = "route"
        private const val propertyNamePipeline = "pipeline"
        private const val propertyNameResponseBody = "responseBody"
    }
}
