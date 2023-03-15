package com.chrynan.kapi.server.ksp

import com.chrynan.kapi.server.ksp.util.throwError
import com.chrynan.kapi.server.processor.core.ApiProcessor
import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.processing.KSPLogger

class KtorValidatorApiProcessor(
    private val logger: KSPLogger
) : ApiProcessor {

    override fun process(round: Int, currentApis: List<ApiDefinition>, allApis: List<ApiDefinition>) {
        if (currentApis.isNotEmpty()) {
            currentApis.forEach { api ->
                api.functions.forEach { function ->
                    function.validate()
                }
            }
        }
    }

    private fun ApiFunction.validate() {
        this.extensionReceiver?.let { receiver ->
            if (!receiver.isSupportedExtensionReceiverType) {
                logger.throwUnsupportedExtensionReceiverTypeError(
                    function = this,
                    extensionReceiver = receiver,
                    supportedTypes = extensionReceiverTypeNames
                )
            }
        }

        var bodyParameterCount = 0
        var hasPartParameter = false
        var hasFieldParameter = false

        this.parameters.forEach { parameter ->
            val parameterType = parameter.declaration.type

            when (parameter) {
                is BodyParameter -> {
                    bodyParameterCount += 1
                }

                is PartParameter -> {
                    hasPartParameter = true
                }

                is FieldParameter -> {
                    hasFieldParameter = true
                }

                else -> {}
            }

            when (parameter) {
                is PathParameter, is QueryParameter, is FieldParameter, is HeaderParameter -> {
                    if (!parameterType.isBasicSupportedParameterType) {
                        logger.throwUnsupportedParameterTypeError(
                            function = this,
                            parameter = parameter,
                            supportedTypes = basicParameterTypeNames
                        )
                    }
                }

                is PartParameter -> {
                    if (!parameterType.isPartParameterSupportedType) {
                        logger.throwUnsupportedParameterTypeError(
                            function = this,
                            parameter = parameter,
                            supportedTypes = partParameterTypeNames
                        )
                    }
                }

                is SupportedTypeParameter -> {
                    if (!parameterType.isSupportedTypeParameterType) {
                        logger.throwUnsupportedParameterTypeError(
                            function = this,
                            parameter = parameter,
                            supportedTypes = supportedTypeParameterTypeNames
                        )
                    }
                }

                is BodyParameter -> {}
                is DefaultValueParameter -> {}
            }
        }

        if (bodyParameterCount > 1) {
            logger.throwError(message = "Cannot have more than one Body parameter for API function ${this.kotlinFunction.name.full}.")
        }

        if (hasPartParameter && hasFieldParameter) {
            logger.throwError(message = "Cannot have both Part and Field parameters for API function ${this.kotlinFunction.name.full}.")
        }
    }

    private fun KSPLogger.throwUnsupportedExtensionReceiverTypeError(
        function: ApiFunction,
        extensionReceiver: KotlinTypeUsage,
        supportedTypes: List<String>
    ): Nothing =
        this.throwError(
            message = "Unsupported extension receiver type $extensionReceiver for API function ${function.kotlinFunction.name.full}. The only supported types are " +
                    supportedTypes.joinToString(separator = ", ", postfix = ".")
        )

    private fun KSPLogger.throwUnsupportedParameterTypeError(
        function: ApiFunction,
        parameter: ApiParameter,
        supportedTypes: List<String>
    ): Nothing =
        this.throwError(
            message = "Unsupported parameter type ${parameter.declaration.type} for API function ${function.kotlinFunction.name.full}. The only supported types are " +
                    supportedTypes.joinToString(separator = ", ", postfix = ".")
        )

    private val KotlinTypeUsage.isBasicSupportedParameterType: Boolean
        get() = this.isConvertibleByDefaultConversionService ||
                this.isByte ||
                ((this.isList || this.isCollection || this.isArray) && (this.typeArguments.first().type?.isString
                    ?: false))

    private val KotlinTypeUsage.isSupportedTypeParameterType: Boolean
        get() = this.isUnit || this.isApplicationCall || this.isRoute || this.isParameters || this.isMultiPartData

    private val KotlinTypeUsage.isPartParameterSupportedType: Boolean
        get() = isBasicSupportedParameterType || this.isPartData || this.isMultiPartData || this.isInput || this.isInputStream || this.isByteReadChannel || this.isByteArray

    private val KotlinTypeUsage.isSupportedExtensionReceiverType: Boolean
        get() = this.isApplicationCall || this.isRoute

    private val supportedTypeParameterTypeNames: List<String>
        get() = listOf(
            "kotlin.Unit",
            "io.ktor.server.application.ApplicationCall",
            "io.ktor.server.routing.Route",
            "io.ktor.http.content.PartData",
            "io.ktor.http.content.MultiPartData"
        )

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

    private val extensionReceiverTypeNames: List<String>
        get() = listOf("io.ktor.server.application.ApplicationCall", "io.ktor.server.routing.Route")
}
