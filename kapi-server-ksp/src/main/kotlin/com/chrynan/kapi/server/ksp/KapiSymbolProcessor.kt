package com.chrynan.kapi.server.ksp

import com.chrynan.kapi.core.annotation.Api
import com.chrynan.kapi.server.ksp.util.getSymbolsWithAnnotation
import com.chrynan.kapi.server.ksp.util.toApiDefinition
import com.chrynan.kapi.server.processor.core.model.ApiDefinition
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(KspExperimental::class)
class KapiSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    /**
     * The current list of [ApiDefinition]s that were processed. As new [ApiDefinition]s are processed for each
     * processing round that occurs for a compilation, they are added to this list. This assumes that this
     * [SymbolProcessor] instance will be reused for every round and a new one will not be created. The following KSP
     * example does something similar:
     * https://github.com/google/ksp/blob/main/examples/playground/test-processor/src/main/kotlin/TestProcessor.kt#L20
     */
    private val allApis = mutableListOf<ApiDefinition>()

    /**
     * The current round number. This value is increased for every processing round that occurs for a compilation. This
     * number is stored locally in this class and is increased every time the [process] function is invoked. This
     * assumes that this [SymbolProcessor] instance will be reused for every round and a new one will not be created.
     * The following KSP example does something similar:
     * https://github.com/google/ksp/blob/main/examples/playground/test-processor/src/main/kotlin/TestProcessor.kt#L20
     */
    private var roundCount = 1

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val apiAnnotatedElements = resolver.getSymbolsWithAnnotation(Api::class).filterIsInstance<KSClassDeclaration>()
        val apiDefinitions = apiAnnotatedElements.map { it.toApiDefinition() }.toList()

        allApis.addAll(apiDefinitions)

        val outputStream = codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = "",
            fileName = "Test",
            extensionName = "json"
        )

        val writer = outputStream.bufferedWriter()

        writer.write(Json.encodeToString(apiDefinitions))

        writer.close()
        outputStream.close()

        roundCount++

        return emptyList()
    }
}
