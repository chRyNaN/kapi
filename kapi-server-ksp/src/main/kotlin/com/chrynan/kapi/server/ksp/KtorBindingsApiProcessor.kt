package com.chrynan.kapi.server.ksp

import com.chrynan.kapi.server.ksp.output.KtorBindingApiFunctionConverter
import com.chrynan.kapi.server.ksp.output.KtorBindingsApiDefinitionConverter
import com.chrynan.kapi.server.ksp.util.apiName
import com.chrynan.kapi.server.processor.core.ApiProcessor
import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger

class KtorBindingsApiProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : ApiProcessor {

    private val functionConverter =
        KtorBindingApiFunctionConverter(classPropertyNameApi = classPropertyNameApi, logger = logger)
    private val apiDefinitionConverter =
        KtorBindingsApiDefinitionConverter(
            classPropertyNameApi = classPropertyNameApi,
            functionConverter = functionConverter
        )

    override fun process(round: Int, currentApis: List<ApiDefinition>, allApis: List<ApiDefinition>) {
        if (currentApis.isNotEmpty()) {
            try {
                currentApis.forEach { api ->
                    val apiTypeSpec = apiDefinitionConverter.invoke(definition = api)

                    codeGenerator.createNewFile(
                        dependencies = Dependencies(aggregating = false),
                        packageName = api.type.name.packageName!!,
                        fileName = "${api.apiName}Routing",
                        extensionName = "kt"
                    ).bufferedWriter()
                        .use { writer ->
                            apiTypeSpec.writeTo(writer)
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
