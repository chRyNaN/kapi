package com.chrynan.kapi.server.ksp.output

import com.chrynan.kapi.core.HttpMethod
import com.chrynan.kapi.server.ksp.util.addStatement
import com.chrynan.kapi.server.ksp.util.controlFlow
import com.chrynan.kapi.server.ksp.util.throwError
import com.chrynan.kapi.server.ksp.util.typeName
import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.processing.KSPLogger
import com.squareup.kotlinpoet.*

class KtorBindingApiFunctionConverter(
    private val classPropertyNameApi: String,
    private val logger: KSPLogger
) {

    private val applicationCallMemberName =
        MemberName(packageName = "io.ktor.server.application", simpleName = "call", isExtension = true)

    operator fun invoke(function: ApiFunction): FunSpec {
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
            catchAnyErrors(function) {
                httpMethod(function) {
                    addDefaultProperties(
                        declareMultipartData = declareMultipartData,
                        declareMultipartDataMap = declareMultipartDataMap,
                        declareParameters = declareParameters
                    )

                    function.parameters
                        .filter { it !is DefaultValueParameter && it !is SupportedTypeParameter && it !is PartParameter && it !is BodyParameter }
                        .map { parameter -> addStatement(parameter.toAssignmentDeclaration(function = function)) }

                    function.partParameters.map { parameter -> addStatement(parameter.toAssignmentDeclaration(function = function)) }

                    function.parameters.filterIsInstance<BodyParameter>().firstOrNull()
                        ?.toAssignmentDeclaration()?.let { addStatement(it) }

                    add("\n")

                    invokeApiFunction(function = function)
                }
            }
        }
    }

    private fun ApiFunction.create(block: CodeBlock.Builder.(function: ApiFunction) -> Unit): FunSpec {
        val builder = FunSpec.builder(name = this.name.short)
            .addModifiers(KModifier.PRIVATE)
            .receiver(ClassName.bestGuess("io.ktor.server.routing.Route"))

        val codeBlockBuilder = CodeBlock.builder()
            .addStatement("val $propertyNameRoute = this")
            .add("\n")

        codeBlockBuilder.block(this)

        builder.addCode(codeBlockBuilder.build())

        return builder.build()
    }

    private fun CodeBlock.Builder.httpMethod(
        function: ApiFunction,
        block: CodeBlock.Builder.() -> Unit
    ): CodeBlock.Builder {
        val builder = this

        val httpFunctionName = when (function.method) {
            HttpMethod.GET -> "get"
            HttpMethod.POST -> "post"
            HttpMethod.PUT -> "put"
            HttpMethod.PATCH -> "patch"
            HttpMethod.DELETE -> "delete"
            HttpMethod.HEAD -> "head"
            else -> logger.throwError(message = "Unsupported HTTP method ${function.method} for API function ${function.name.full}")
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

    private fun ApiParameter.toAssignmentDeclaration(function: ApiFunction): CodeBlock {
        val builder = CodeBlock.builder()

        val type = this.declaration.type
        val propertyName = this.declaration.name
        val parameterName = this.value?.takeIf { it.isNotBlank() } ?: this.declaration.name

        builder.add("val $propertyName: %T = ", type.typeName)

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

            else -> logger.throwError(message = "Unexpected parameter type ${type.name} for API function ${function.name.full}.")
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
                "%M(name = %S)",
                MemberName(
                    packageName = "com.chrynan.kapi.server.core",
                    simpleName = "getOrNull",
                    isExtension = true
                ),
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

        return builder.build()
    }

    private fun PartParameter.toAssignmentDeclaration(function: ApiFunction): CodeBlock {
        val builder = CodeBlock.builder()

        val type = this.declaration.type
        val propertyName = this.declaration.name
        val parameterName = this.value.takeIf { it.isNotBlank() } ?: this.declaration.name
        val partDataGetter = "$propertyNameMultipartDataMap[\"$parameterName\"]"
        val formItemClassName = ClassName.bestGuess("io.ktor.http.content.PartData.FormItem")

        builder.add("val $propertyName = ")

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
                    packageName = "com.chrynan.kapi.server.core",
                    simpleName = "asInputStream",
                    isExtension = true
                )
            )

            type.isInput -> builder.add(
                "$partDataGetter.%M()",
                MemberName(
                    packageName = "com.chrynan.kapi.server.core",
                    simpleName = "asInput",
                    isExtension = true
                )
            )

            type.isByteReadChannel -> builder.add(
                "$partDataGetter.%M()",
                MemberName(
                    packageName = "com.chrynan.kapi.server.core",
                    simpleName = "asByteReadChannel",
                    isExtension = true
                )
            )

            type.isByteArray -> builder.add(
                "$partDataGetter.%M()",
                MemberName(
                    packageName = "com.chrynan.kapi.server.core",
                    simpleName = "asByteArray",
                    isExtension = true
                )
            )

            else -> logger.throwError(message = "Unexpected Part parameter type ${type.name.full} for API function ${function.name.full}.")
        }

        return builder.build()
    }

    private fun BodyParameter.toAssignmentDeclaration(): CodeBlock {
        val builder = CodeBlock.builder()

        val type = this.declaration.type
        val propertyName = this.declaration.name

        builder.add("val $propertyName: %T = ", type.typeName)

        when {
            type.isNullable -> builder.add(
                "$propertyNamePipeline.%M.%M()",
                applicationCallMemberName,
                MemberName(packageName = "io.ktor.server.request", simpleName = "receiveNullable", isExtension = true)
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
                "$propertyNamePipeline.%M.%M()",
                applicationCallMemberName,
                MemberName(packageName = "io.ktor.server.request", simpleName = "receive", isExtension = true)
            )
        }

        return builder.build()
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
            when {
                receiver == null -> builder.addStatement("${classPropertyNameApi}.${function.name.short}(")
                receiver.isApplicationCall -> builder.addStatement(
                    "$propertyNamePipeline.%M.${function.name.short}(",
                    applicationCallMemberName
                )

                receiver.isRoute -> builder.addStatement("$propertyNameRoute.${function.name.short}(")
                else -> logger.throwError(message = "Unexpected Part extension receiver type ${receiver.name.full} for API function ${function.name.full}.")
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
        }
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
                else -> logger.throwError(message = "Unexpected supported parameter type ${parameterType.name.full} for API function ${function.name.full}.")
            }

            else -> builder.addStatement("$parameterAndPropertyName = $parameterAndPropertyName${if (isLast) "" else ","}")
        }

        return builder.build()
    }

    private fun CodeBlock.Builder.catchAnyErrors(
        function: ApiFunction,
        block: CodeBlock.Builder.() -> Unit
    ): CodeBlock.Builder {
        val builder = this

        if (function.errors.isNotEmpty()) {
            builder.beginControlFlow("try")

            builder.block()

            function.errors.forEach { error -> builder.catchError(error = error) }

            builder.endControlFlow()
        } else {
            builder.block()
        }

        return builder
    }

    private fun CodeBlock.Builder.catchError(error: ApiError): CodeBlock.Builder =
        this.nextControlFlow("catch(_: %T)", error.exceptionType.typeName)
            .addStatement(
                """
            |this.call.respondError(
            |    error = %T(
            |        type = ${error.type},
            |        title = ${error.title},
            |        details = ${error.details}.%M { it.%M() },
            |        status = ${error.statusCode},
            |        instance = ${error.instance},
            |        timestamp = %T.now(),
            |        help = ${error.help}))
            """.trimMargin(),
                ClassName.bestGuess("com.chrynan.kapi.core.Error"),
                MemberName(packageName = "kotlin", simpleName = "takeIf", isExtension = true),
                MemberName(packageName = "kotlin.text", simpleName = "isNotBlank", isExtension = true),
                ClassName.bestGuess("kotlinx.datetime.Clock.System")
            )
            .endControlFlow()

    companion object {

        private const val propertyNameMultipartData = "multipartData"
        private const val propertyNameMultipartDataMap = "multipartDataMap"
        private const val propertyNameParameters = "parameters"
        private const val propertyNameRoute = "route"
        private const val propertyNamePipeline = "pipeline"
    }
}
