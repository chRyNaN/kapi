package com.chrynan.kapi.server.ksp

import com.chrynan.kapi.server.processor.core.ApiProcessor
import com.chrynan.kapi.server.processor.core.model.ApiDefinition
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class JsonApiProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : ApiProcessor {

    override fun process(round: Int, currentApis: List<ApiDefinition>, allApis: List<ApiDefinition>) {
        if (currentApis.isNotEmpty()) {
            try {
                codeGenerator.createNewFileByPath(
                    dependencies = Dependencies(aggregating = false),
                    path = "apis",
                    extensionName = "json"
                ).bufferedWriter().use { writer ->
                    writer.write(Json.encodeToString(allApis))
                }
            } catch (e: FileAlreadyExistsException) {
                logger.warn(message = "Json File already exists. Cannot create a new one.")
            }
        }
    }
}
