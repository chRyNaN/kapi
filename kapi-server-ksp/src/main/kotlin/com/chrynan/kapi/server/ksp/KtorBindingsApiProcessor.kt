package com.chrynan.kapi.server.ksp

import com.chrynan.kapi.core.HttpMethod
import com.chrynan.kapi.server.ksp.util.addPropertyDeclaration
import com.chrynan.kapi.server.ksp.util.throwError
import com.chrynan.kapi.server.ksp.util.typeName
import com.chrynan.kapi.server.processor.core.ApiProcessor
import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.squareup.kotlinpoet.*

class KtorBindingsApiProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : ApiProcessor {

    override fun process(round: Int, currentApis: List<ApiDefinition>, allApis: List<ApiDefinition>) {
        if (currentApis.isNotEmpty()) {
            try {
                currentApis.forEach { api ->
                    val packageName = api.typeName.packageName!!
                    val apiName = api.name.takeIf { it.isNotBlank() } ?: api.typeName.short
                    val bindingClassName = "$apiName$suffixKtorBindingClass"

                    val fileString = api.toFileString(packageName = packageName, bindingClassName = bindingClassName)

                    codeGenerator.createNewFile(
                        dependencies = Dependencies(aggregating = false),
                        packageName = packageName,
                        fileName = bindingClassName,
                        extensionName = "kt"
                    ).bufferedWriter().use { writer ->
                        writer.write(fileString)
                    }
                }
            } catch (e: FileAlreadyExistsException) {
                logger.warn(message = "Ktor bindings file already exists; cannot create a new one.")
            }
        }
    }

    private fun ApiDefinition.toFileString(packageName: String, bindingClassName: String): String = """
    |package $packageName
    |
    |import com.chrynan.kapi.server.core.*
    |import io.ktor.server.routing.*
    |
    |${this.toClassString(className = bindingClassName)}
    """.trimMargin()

    private fun ApiDefinition.toClassString(className: String): String = """
    |private class $className(private val $classPropertyNameApi: ${this.typeName.full}) {
    |
    |   ${this.functions.joinToString(separator = "\n\n|    ") { it.toFunString() }}
    |}
    """.trimMargin()

    private fun ApiFunction.toFunString(): String {
        val function = this
        val builder = FunSpec.builder(this.name.short)

        var declareMultipartData = false
        var declareMultipartDataMap = false
        var declareParameters = false
        val surroundInTryCatch = this.errors.isNotEmpty()

        function.create {
            catchAnyErrors(function) {
                httpMethod(function) {

                }
            }
        }

        this.parameters.forEach { parameter ->
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
            }
        }

        val parameterProperties = this.parameters
            .filter { it !is DefaultValueParameter && it !is SupportedTypeParameter && it !is PartParameter && it !is BodyParameter }
            .map { parameter -> parameter.toAssignmentDeclaration(function = this) }

        val partDataProperties =
            this.partParameters.map { parameter -> parameter.toAssignmentDeclaration(function = this) }

        val bodyProperty =
            this.parameters.filterIsInstance<BodyParameter>().firstOrNull()?.toAssignmentDeclaration(function = this)

        val errorCatchBlocks = this.errors.map { error -> error.toCatchAndRespondError() }

        val codeBlock =
            """
            |
            |${if (declareMultipartData) "val $propertyNameMultipartData = ${this.thisRoute}.call.receiveMultipart()" else ""}
            |${if (declareMultipartDataMap) "val $propertyNameMultipartDataMap = $propertyNameMultipartData.readAllParts().associateBy { it.name }" else ""}
            |${if (declareParameters) "val $propertyNameParameters = ${this.thisRoute}.call.receiveParameters()" else ""}
            |
            |${parameterProperties.joinToString(separator = "\n|")}
            |${partDataProperties.joinToString(separator = "\n|")}
            |${bodyProperty ?: ""}
            |    
            |${this.invokeApiFunction()}
        """.trimMargin()

        val formattedCodeBlock = if (surroundInTryCatch) {
            surroundInTryCatch(
                code = codeBlock,
                errorBlocks = errorCatchBlocks
            )
        } else {
            codeBlock
        }

        return this.invoke {
            formattedCodeBlock
        }
    }

    private fun surroundInTryCatch(code: String, errorBlocks: List<String>): String =
        """
        |try {
        |    $code
        |} ${errorBlocks.joinToString(separator = " ")}
        """.trimMargin()

    private fun ApiFunction.invoke(block: () -> String): String =
        """
        |private fun io.ktor.server.routing.Route.${this.name.short}() {
        |    ${block.invoke()}
        |}
        """.trimMargin()

    private fun ApiFunction.create(block: CodeBlock.Builder.(function: ApiFunction) -> Unit): FunSpec {
        val builder = FunSpec.builder(name = this.name.short)
            .addModifiers(KModifier.PRIVATE)
            .receiver(ClassName.bestGuess("io.ktor.server.routing.Route"))

        val codeBlockBuilder = CodeBlock.builder()

        codeBlockBuilder.block(this)

        builder.addCode(codeBlockBuilder.build())

        return builder.build()
    }

    private fun CodeBlock.Builder.httpMethod(
        function: ApiFunction,
        block: CodeBlock.Builder.() -> Unit
    ): CodeBlock.Builder {
        val builder = this

        when (function.method) {
            HttpMethod.GET -> builder.beginControlFlow("get(path = %S)", function.path)
            HttpMethod.POST -> builder.beginControlFlow("post(path = %S)", function.path)
            HttpMethod.PUT -> builder.beginControlFlow("put(path = %S)", function.path)
            HttpMethod.PATCH -> builder.beginControlFlow("patch(path = %S)", function.path)
            HttpMethod.DELETE -> builder.beginControlFlow("delete(path = %S)", function.path)
            HttpMethod.HEAD -> builder.beginControlFlow("head(path = %S)", function.path)
            else -> logger.throwError(message = "Unsupported HTTP method ${function.method} for API function ${function.name.full}")
        }

        builder.block()

        return builder
    }

    private fun ApiFunction.invokeApiFunction(): String {
        val functionInvocationPrefix = extensionReceiver?.let { receiver ->
            when {
                receiver.isApplicationCall -> "${this.thisRoute}.call"
                receiver.isRoute -> this.thisRoute
                else -> logger.throwError(message = "Unexpected Part extension receiver type ${receiver.name.full} for API function ${this.name.full}.")
            }
        }

        val parameterAssignments = this.parameters.filter { it !is DefaultValueParameter }
            .map { parameter -> parameter.toParameterAssignment(function = this) }

        return if (functionInvocationPrefix != null) {
            """
            |${classPropertyNameApi}.apply {
            |   $functionInvocationPrefix.${this.name.short}(
            |       ${parameterAssignments.joinToString(",\n|")})
            |}
            """.trimMargin()
        } else {
            """
            |${classPropertyNameApi}.${this.name.short}(
            |       ${parameterAssignments.joinToString(",\n|")})
            """.trimMargin()
        }
    }

    private fun ApiParameter.toAssignmentDeclaration(function: ApiFunction): CodeBlock {
        val builder = CodeBlock.builder()

        val type = this.declaration.type
        val parameterName = this.value?.takeIf { it.isNotBlank() } ?: this.declaration.name

        val parametersFetcherObject = when (this) {
            is PathParameter -> "${function.thisRoute}.call.parameters"
            is QueryParameter -> "${function.thisRoute}.call.request.queryParameters"
            is FieldParameter -> propertyNameParameters
            is HeaderParameter -> "${function.thisRoute}.call.request.headers"
            else -> logger.throwError(message = "Unexpected parameter type ${type.name} for API function ${function.name.full}.")
        }

        val parametersFetcherFunction =
            when {
                type.isList -> "getAll(name = %S)"
                type.isCollection -> "getAll(name = %S)"
                type.isArray -> "getAll(name = %S).toTypedArray()"
                else -> "getOrNull(name = %S)"
            }

        val propertyAssignment = if (type.isNullable) {
            "$parametersFetcherObject.$parametersFetcherFunction"
        } else {
            "$parametersFetcherObject.$parametersFetcherFunction ?: error(\"%S parameter value must be present and not null.\")"
        }

        builder.addPropertyDeclaration(
            propertyName = this.declaration.name,
            propertyTypeName = type.typeName,
            isNullable = type.isNullable,
            assignment = propertyAssignment,
            assignmentArgs = arrayOf(
                parameterName,
                parameterName
            )
        )

        return builder.build()
    }

    private fun PartParameter.toAssignmentDeclaration(function: ApiFunction): CodeBlock {
        val builder = CodeBlock.builder()

        val type = this.declaration.type
        val parameterName = this.value.takeIf { it.isNotBlank() } ?: this.declaration.name
        val partDataGetter = "$propertyNameMultipartDataMap[\"${this.declaration.name}\"]"
        val formItemGetter =
            "$partDataGetter as io.ktor.http.content.PartData.FormItem)"

        val propertyAssignment = when {
            type.isBoolean -> "$formItemGetter.value.toBoolean()"
            type.isByte -> "$formItemGetter.value.toByte()"
            type.isShort -> "$formItemGetter.value.toShort()"
            type.isInt -> "$formItemGetter.value.toInt()"
            type.isLong -> "$formItemGetter.value.toLong()"
            type.isUByte -> "$formItemGetter.value.toUByte()"
            type.isUShort -> "$formItemGetter.value.toUShort()"
            type.isUInt -> "$formItemGetter.value.toUInt()"
            type.isULong -> "$formItemGetter.value.toULong()"
            type.isFloat -> "$formItemGetter.value.toFloat()"
            type.isDouble -> "$formItemGetter.value.toDouble()"
            type.isChar -> "$formItemGetter.value.toCharArray()[0]"
            type.isString -> "$formItemGetter.value"
            type.isPartData && type.name.full.endsWith("FormItem") -> formItemGetter
            type.isPartData && type.name.full.endsWith("FileItem") -> "($partDataGetter as io.ktor.http.content.PartData.FileItem)"
            type.isPartData && type.name.full.endsWith("BinaryItem") -> "($partDataGetter as io.ktor.http.content.PartData.BinaryItem)"
            type.isPartData && type.name.full.endsWith("BinaryChannelItem") -> "($partDataGetter as io.ktor.http.content.PartData.BinaryChannelItem)"
            type.isPartData -> partDataGetter
            type.isMultiPartData -> propertyNameMultipartData
            type.isInputStream -> "$partDataGetter.asInputStream()"
            type.isInput -> "$partDataGetter.asInput()"
            type.isByteReadChannel -> "$partDataGetter.asByteReadChannel()"
            type.isByteArray -> "$partDataGetter.asByteArray()"
            else -> logger.throwError(message = "Unexpected Part parameter type ${type.name.full} for API function ${function.name.full}.")
        }

        builder.addPropertyDeclaration(
            propertyName = this.declaration.name,
            propertyTypeName = type.typeName,
            isNullable = type.isNullable,
            assignment = propertyAssignment,
            assignmentArgs = arrayOf(
                parameterName,
                parameterName
            )
        )

        return builder.build()
    }

    private fun BodyParameter.toAssignmentDeclaration(function: ApiFunction): String {
        val type = this.declaration.type

        val bodyDeclaration = "val ${this.declaration.name}: ${type.name.full} "

        val bodyAssignment = when {
            type.isNullable -> "${function.thisRoute}.call.receiveNullable()"
            type.isString -> "${function.thisRoute}.call.receiveText()"
            type.isByteReadChannel -> "${function.thisRoute}.call.receiveChannel()"
            type.isInputStream -> "${function.thisRoute}.call.receiveStream()"
            type.isMultiPartData -> propertyNameMultipartData
            type.isParameters -> propertyNameParameters
            else -> "${function.thisRoute}.call.receive()"
        }

        return "$bodyDeclaration = $bodyAssignment"
    }

    private fun ApiParameter.toParameterAssignment(function: ApiFunction): String {
        val parameterType = this.declaration.type
        val parameterAndPropertyName = this.declaration.name

        return when (this) {
            is SupportedTypeParameter -> when {
                parameterType.isUnit -> "$parameterAndPropertyName = Unit"
                parameterType.isApplicationCall -> "$parameterAndPropertyName = this.call"
                parameterType.isRoute -> "$parameterAndPropertyName = ${function.thisRoute}"
                else -> logger.throwError(message = "Unexpected supported parameter type ${parameterType.name.full} for API function ${function.name.full}.")
            }

            else -> "$parameterAndPropertyName = $parameterAndPropertyName"
        }
    }

    private fun ApiError.toCatchAndRespondError(): String =
        """
            |catch(${this.exceptionType.name.full}) {
            |    this.call.respondError(
            |        error = com.chrynan.kapi.core.Error(
            |            type = ${this.type},
            |            title = ${this.title},
            |            details = ${this.details}.takeIf { it.isNotBlank() },
            |            status = ${this.statusCode},
            |            instance = ${this.instance},
            |            timestamp = kotlinx.datetime.Clock.System.now(),
            |            help = ${this.help}))
            |}
            """.trimMargin()

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
        this.nextControlFlow("_: %T", error.exceptionType.typeName)
            .addStatement(
                """
            |this.call.respondError(
            |    error = %T(
            |        type = ${error.type},
            |        title = ${error.title},
            |        details = $error.details}.takeIf { it.isNotBlank() },
            |        status = ${error.statusCode},
            |        instance = ${error.instance},
            |        timestamp = %T.now(),
            |        help = ${error.help}))
            """.trimMargin(),
                ClassName.bestGuess("com.chrynan.kapi.core.Error"),
                ClassName.bestGuess("kotlinx.datetime.Clock.System")
            )
            .endControlFlow()

    private val ApiFunction.thisRoute: String
        get() = "this@${name.short}"

    companion object {

        private const val propertyNameMultipartData = "multipartData"
        private const val propertyNameMultipartDataMap = "multipartDataMap"
        private const val propertyNameParameters = "parameters"
        private const val classPropertyNameApi = "api"
        private const val suffixKtorBindingClass = "KtorBinding"
    }
}
