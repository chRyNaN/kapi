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
            }
        }

        val parameterProperties = this.parameters
            .filter { it !is DefaultValueParameter && it !is SupportedTypeParameter && it !is PartParameter && it !is BodyParameter }
            .map { parameter ->
                val type = parameter.declaration.type

                val propertyDeclaration = if (type.isNullable) {
                    "val ${parameter.declaration.name}: ${type.name.full}?"
                } else {
                    "val ${parameter.declaration.name}: ${type.name.full}"
                }

                val parametersFetcherObject = when (parameter) {
                    is PathParameter -> "this.call.parameters"
                    is QueryParameter -> "this.call.request.queryParameters"
                    is FieldParameter -> propertyNameParameters
                    is HeaderParameter -> "this.call.request.headers"
                    else -> logger.throwError(message = "Unexpected parameter type ${type.name} for API function ${this.name.full}.")
                }

                val parameterName = parameter.value ?: parameter.declaration.name

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

                "$propertyDeclaration = $propertyAssignment"
            }

        val partDataProperties = this.partParameters.map { parameter ->
            val type = parameter.declaration.type
            val partDataGetter = "$propertyNameMultipartDataMap[\"${parameter.declaration.name}\"]"
            val formItemGetter =
                "$partDataGetter as io.ktor.http.content.PartData.FormItem)"

            val propertyDeclaration = if (type.isNullable) {
                "val ${parameter.declaration.name}: ${type.name.full}?"
            } else {
                "val ${parameter.declaration.name}: ${type.name.full}"
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
                else -> logger.throwError(message = "Unexpected Part parameter type ${type.name.full} for API function ${this.name.full}.")
            }

            "$propertyDeclaration = $propertyAssignment"
        }

        val bodyProperty = this.parameters.filterIsInstance<BodyParameter>().firstOrNull()?.let { parameter ->
            val type = parameter.declaration.type

            val bodyDeclaration = "val ${parameter.declaration.name}: ${type.name.full} "

            val bodyAssignment = when {
                type.isString -> "this.call.receiveText()"
                type.isByteReadChannel -> "this.call.receiveChannel()"
                type.isInputStream -> "this.call.receiveStream()"
                type.isMultiPartData -> propertyNameMultipartData
                type.isParameters -> propertyNameParameters
                type.isNullable -> "this.call.receiveNullable()"
                else -> "this.call.receive()"
            }

            "$bodyDeclaration = $bodyAssignment"
        }

        extensionReceiver?.let { receiver ->

        }

        return builder.build()
    }

    companion object {

        private const val propertyNameMultipartData = "multipartData"
        private const val propertyNameMultipartDataMap = "multipartDataMap"
        private const val propertyNameParameters = "parameters"
    }
}
