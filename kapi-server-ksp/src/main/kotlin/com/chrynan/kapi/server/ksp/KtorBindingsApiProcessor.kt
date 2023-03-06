package com.chrynan.kapi.server.ksp

import com.chrynan.kapi.core.HttpMethod
import com.chrynan.kapi.server.ksp.util.throwError
import com.chrynan.kapi.server.processor.core.ApiProcessor
import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger

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
        var declareMultipartData = false
        var declareMultipartDataMap = false
        var declareParameters = false
        val surroundInTryCatch = this.errors.isNotEmpty()

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

        val httpFunction = when (this.method) {
            HttpMethod.GET -> "get(path = ${this.path})"
            HttpMethod.POST -> "post(path = ${this.path})"
            HttpMethod.PUT -> "put(path = ${this.path})"
            HttpMethod.PATCH -> "patch(path = ${this.path}"
            HttpMethod.DELETE -> "delete(path = ${this.path})"
            HttpMethod.HEAD -> "head(path = ${this.path}"
            else -> logger.throwError(message = "Unsupported HTTP method ${this.method} for API function ${this.name.full}")
        }

        val parameterProperties = this.parameters
            .filter { it !is DefaultValueParameter && it !is SupportedTypeParameter && it !is PartParameter && it !is BodyParameter }
            .map { parameter -> parameter.toAssignmentDeclaration(function = this) }

        val partDataProperties =
            this.partParameters.map { parameter -> parameter.toAssignmentDeclaration(function = this) }

        val bodyProperty =
            this.parameters.filterIsInstance<BodyParameter>().firstOrNull()?.toAssignmentDeclaration(function = this)

        val functionInvocationPrefix = extensionReceiver?.let { receiver ->
            when {
                receiver.isApplicationCall -> "${this.thisRoute}.call"
                receiver.isRoute -> this.thisRoute
                else -> logger.throwError(message = "Unexpected Part extension receiver type ${receiver.name.full} for API function ${this.name.full}.")
            }
        }

        val parameterAssignments = this.parameters.filter { it !is DefaultValueParameter }
            .map { parameter -> parameter.toParameterAssignment(function = this) }

        val functionInvocation = if (functionInvocationPrefix != null) {
            """
            |${classPropertyNameApi}.apply {
            |   $functionInvocationPrefix.${this.name.short}(
            |       ${parameterAssignments.joinToString(",\n|")})
            |}
            """.trimMargin()
        } else {
            """
            |${this.name.short}(
            |       ${parameterAssignments.joinToString(",\n|")})
            """.trimMargin()
        }

        val errorCatchBlocks = this.errors.map { error -> error.toCatchAndRespondError() }

        val codeBlock = """
        |$httpFunction {
        |    ${if (declareMultipartData) "val $propertyNameMultipartData = ${this.thisRoute}.call.receiveMultipart()" else ""}
        |    ${if (declareMultipartDataMap) "val $propertyNameMultipartDataMap = $propertyNameMultipartData.readAllParts().associateBy { it.name }" else ""}
        |    ${if (declareParameters) "val $propertyNameParameters = ${this.thisRoute}.call.receiveParameters()" else ""}
        |
        |    ${parameterProperties.joinToString(separator = "\n|")}
        |    ${partDataProperties.joinToString(separator = "\n|")}
        |    ${bodyProperty ?: ""}
        |    
        |    $functionInvocation
        |}
        """.trimMargin()

        val formattedCodeBlock = if (surroundInTryCatch) {
            surroundInTryCatch(
                code = codeBlock,
                errorBlocks = errorCatchBlocks
            )
        } else {
            codeBlock
        }

        return """
        |private fun io.ktor.server.routing.Route.${this.name.short}() {
        |$formattedCodeBlock
        |}
        """.trimMargin()
    }

    private fun surroundInTryCatch(code: String, errorBlocks: List<String>): String =
        """
        |try {
        |    $code
        |} ${errorBlocks.joinToString(separator = " ")}
        """.trimMargin()

    private fun ApiParameter.toAssignmentDeclaration(function: ApiFunction): String {
        val type = this.declaration.type

        val propertyDeclaration = if (type.isNullable) {
            "val ${this.declaration.name}: ${type.name.full}?"
        } else {
            "val ${this.declaration.name}: ${type.name.full}"
        }

        val parametersFetcherObject = when (this) {
            is PathParameter -> "${function.thisRoute}.call.parameters"
            is QueryParameter -> "${function.thisRoute}.call.request.queryParameters"
            is FieldParameter -> propertyNameParameters
            is HeaderParameter -> "${function.thisRoute}.call.request.headers"
            else -> logger.throwError(message = "Unexpected parameter type ${type.name} for API function ${function.name.full}.")
        }

        val parameterName = this.value ?: this.declaration.name

        val parametersFetcherFunction =
            when {
                type.isList -> "getAll(name = $parameterName)"
                type.isCollection -> "getAll(name = $parameterName)"
                type.isArray -> "getAll(name = $parameterName).toTypedArray()"
                else -> "getOrNull(name = $parameterName)"
            }

        val propertyAssignment = if (type.isNullable) {
            "$parametersFetcherObject.$parametersFetcherFunction"
        } else {
            "$parametersFetcherObject.$parametersFetcherFunction ?: error(\"$parameterName parameter value must be present and not null.\")"
        }

        return "$propertyDeclaration = $propertyAssignment"
    }

    private fun PartParameter.toAssignmentDeclaration(function: ApiFunction): String {
        val type = this.declaration.type
        val partDataGetter = "$propertyNameMultipartDataMap[\"${this.declaration.name}\"]"
        val formItemGetter =
            "$partDataGetter as io.ktor.http.content.PartData.FormItem)"

        val propertyDeclaration = if (type.isNullable) {
            "val ${this.declaration.name}: ${type.name.full}?"
        } else {
            "val ${this.declaration.name}: ${type.name.full}"
        }

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

        return "$propertyDeclaration = $propertyAssignment"
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
