package com.chrynan.kapi.server.ksp.processor

import com.chrynan.kapi.server.ksp.output.KtorRoutingApiFunctionConverter
import com.chrynan.kapi.server.ksp.output.KtorRoutingApiDefinitionConverter
import com.chrynan.kapi.server.ksp.output.KtorSecuritySchemeConverter
import com.chrynan.kapi.server.ksp.output.apiSecurityDefinitionProviderClassName
import com.chrynan.kapi.server.ksp.util.apiName
import com.chrynan.kapi.server.processor.core.ApiProcessor
import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger

internal class KtorApiProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : ApiProcessor {

    private val routingFunctionConverter =
        KtorRoutingApiFunctionConverter(classPropertyNameApi = classPropertyNameApi, logger = logger)
    private val routingApiDefinitionConverter =
        KtorRoutingApiDefinitionConverter(
            classPropertyNameApi = classPropertyNameApi,
            functionConverter = routingFunctionConverter
        )
    private val securitySchemeConverter = KtorSecuritySchemeConverter()

    override fun process(round: Int, currentApis: List<ApiDefinition>, allApis: List<ApiDefinition>) {
        if (currentApis.isNotEmpty()) {
            try {
                currentApis.forEach { api ->
                    val routingTypeSpec = routingApiDefinitionConverter.invoke(definition = api)
                    val securityTypeSpec = securitySchemeConverter.invoke(definition = api)

                    codeGenerator.createNewFile(
                        dependencies = Dependencies(aggregating = false),
                        packageName = api.type.name.packageName!!,
                        fileName = "${api.apiName}${KtorRoutingApiDefinitionConverter.suffixKtorBindingClass}",
                        extensionName = "kt"
                    ).bufferedWriter()
                        .use { writer ->
                            routingTypeSpec.writeTo(writer)
                        }

                    codeGenerator.createNewFile(
                        dependencies = Dependencies(aggregating = false),
                        packageName = api.type.name.packageName!!,
                        fileName = api.apiSecurityDefinitionProviderClassName,
                        extensionName = "kt"
                    ).bufferedWriter()
                        .use { writer ->
                            securityTypeSpec.writeTo(writer)
                        }
                }
            } catch (e: FileAlreadyExistsException) {
                logger.warn(message = "Ktor bindings file already exists; cannot create a new one.")
            }
        }
    }

    companion object {

        private const val classPropertyNameApi: String = "api"
    }
}
