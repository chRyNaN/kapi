package com.chrynan.kapi.server.ksp

import com.chrynan.kapi.server.ksp.util.throwError
import com.chrynan.kapi.server.processor.core.ApiProcessor
import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier

class KtorBindingsApiProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : ApiProcessor {

    override fun process(round: Int, currentApis: List<ApiDefinition>, allApis: List<ApiDefinition>) {
        if (currentApis.isNotEmpty()) {
            try {

            } catch (e: FileAlreadyExistsException) {
                logger.warn(message = "Ktor bindings file already exists; cannot create a new one.")
            }
        }
    }

    private fun ApiFunction.toFunSpec(): FunSpec {
        val builder = FunSpec.builder(this.name.short)
            .addModifiers(KModifier.PRIVATE)
            .receiver(ClassName.bestGuess("io.ktor.server.routing.Route"))

        var declareMultipartData = false
        var declareMultipartDataMap = false
        var declareParameters = false

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

                parameter is BodyParameter && !parameter.declaration.type.isAnySupportedParameterType -> {
                    logger.throwError(
                        message = "Unsupported parameter type ${parameter.declaration.type} for API function ${this.name.full}. The only supported types are " +
                                anySupportedParameterTypeNames.joinToString(separator = ", ") + "."
                    )
                }
            }
        }

        val parameterProperties = this.parameters
            .filter { it !is DefaultValueParameter && it !is SupportedTypeParameter && it !is PartParameter && it !is BodyParameter }
            .map { parameter ->
                val type = parameter.declaration.type

                if (!type.isBasicSupportedParameterType) {
                    logger.throwError(
                        message = "Unsupported path, query, field, or header parameter type $type for API function ${this.name.full}. The only supported types are " +
                                basicParameterTypeNames.joinToString(separator = ", ") + "."
                    )
                }

                val propertyDeclaration = if (type.isNullable) {
                    "val ${parameter.declaration.name}: ${type.name.full}?"
                } else {
                    "val ${parameter.declaration.name}: ${type.name.full}"
                }

                val parametersFetcherObject = when (parameter) {
                    is PathParameter -> "this.call.parameters"
                    is QueryParameter -> "this.call.request.queryParameters"
                    is FieldParameter -> "parameters"
                    is HeaderParameter -> "this.call.request.headers"
                    else -> logger.throwError(message = "Unexpected parameter type ${type.name} for API function ${this.name.full}.")
                }

                val parameterName = parameter.value ?: parameter.declaration.name

                val parametersFetcherFunction =
                    when {
                        type.isList -> "getAll(name = $parameterName)"
                        type.isCollection -> "getAll(name = $parameterName).toList()"
                        type.isArray -> "getAll(name = $parameterName).toList()"
                        else -> "getOrNull(name = $parameterName)"
                    }

                val propertyAssignment = if (type.isNullable) {
                    "$parametersFetcherObject.$parametersFetcherFunction"
                } else {
                    "$parametersFetcherObject.$parametersFetcherFunction ?: error(\"$parameterName parameter value must be present and not null.\")"
                }

                "$propertyDeclaration = $propertyAssignment"
            }

        val partDataProperties = this.partParameters.map { parameter ->
            val type = parameter.declaration.type

            if (!type.isPartParameterSupportedType) {
                logger.throwError(
                    message = "Unsupported part data parameter type $type for API function ${this.name.full}. The only supported types are " +
                            partParameterTypeNames.joinToString(separator = ", ") + "."
                )
            }
        }

        val allBodyParameters = this.parameters.filterIsInstance<BodyParameter>()

        if (allBodyParameters.size > 1) {
            logger.throwError(message = "Only one body parameter is supported for an API function. API function ${this.name.full} contained ${allBodyParameters.size} body parameters.")
        }

        val bodyProperty = allBodyParameters.firstOrNull()?.let { parameter ->
            val type = parameter.declaration.type

            val bodyDeclaration = "val ${parameter.declaration.name}: ${type.name.full} "

            val bodyAssignment = when {
                type.isString -> "this.call.receiveText()"
                type.isByteReadChannel -> "this.call.receiveChannel()"
                type.isInputStream -> "this.call.receiveStream()"
                type.isMultiPartData -> "multipartData"
                type.isParameters -> "parameters"
                type.isNullable -> "this.call.receiveNullable()"
                else -> "this.call.receive()"
            }

            "$bodyDeclaration = $bodyAssignment"
        }

        extensionReceiver?.let { receiver ->
            if (!receiver.isSupportedExtensionReceiverType) {
                logger.throwError(
                    message = "Unsupported extension receiver type ${receiver.name} for API function ${this.name.full}. The only supported types are " +
                            extensionReceiverTypeNames.joinToString(separator = ", ") + "."
                )
            }
        }

        return builder.build()
    }

    private val KotlinTypeUsage.isAnySupportedParameterType: Boolean
        get() = isBasicSupportedParameterType || this.isPartParameterSupportedType || this.isSupportedTypeParameterType

    private val KotlinTypeUsage.isBasicSupportedParameterType: Boolean
        get() = this.isConvertibleByDefaultConversionService ||
                this.isByte ||
                ((this.isList || this.isCollection || this.isArray) && (this.typeArguments.first().type?.isString
                    ?: false))

    private val KotlinTypeUsage.isSupportedTypeParameterType: Boolean
        get() = this.isUnit || this.isApplicationCall || this.isRoute

    private val KotlinTypeUsage.isPartParameterSupportedType: Boolean
        get() = isBasicSupportedParameterType || this.isPartData || this.isMultiPartData || this.isInput || this.isInputStream || this.isByteReadChannel || this.isByteArray

    private val KotlinTypeUsage.isSupportedExtensionReceiverType: Boolean
        get() = this.isApplicationCall || this.isRoute

    private val supportedTypeParameterTypeNames: List<String>
        get() = listOf("kotlin.Unit", "io.ktor.server.application.ApplicationCall", "io.ktor.server.routing.Route")

    private val basicParameterTypeNames: List<String>
        get() = listOf(
            "Kotlin primitives",
            "kotlin.collections.List<String>",
            "kotlin.collections.Collection<String>",
            "kotlin.Array<String>"
        )

    private val partParameterTypeNames: List<String>
        get() = listOf(
            "io.ktor.http.content.PartData",
            "io.ktor.http.content.MultiPartData",
            "io.ktor.utils.io.core.Input",
            "java.io.InputStream",
            "io.ktor.utils.io.ByteReadChannel",
            "kotlin.ByteArray"
        )

    private val anySupportedParameterTypeNames: List<String>
        get() = supportedTypeParameterTypeNames + basicParameterTypeNames + partParameterTypeNames

    private val extensionReceiverTypeNames: List<String>
        get() = listOf("io.ktor.server.application.ApplicationCall", "io.ktor.server.routing.Route")
}
