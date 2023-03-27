package com.chrynan.kapi.server.ksp

import com.chrynan.kapi.server.core.annotation.Api
import com.chrynan.kapi.server.core.annotation.Auth
import com.chrynan.kapi.server.core.annotation.Consumes
import com.chrynan.kapi.server.ksp.util.getSymbolsWithAnnotation
import com.chrynan.kapi.server.ksp.util.kotlinName
import com.chrynan.kapi.server.ksp.util.toApiAuth
import com.chrynan.kapi.server.ksp.util.toApiDefinition
import com.chrynan.kapi.server.processor.core.ApiProcessor
import com.chrynan.kapi.server.processor.core.model.ApiDefinition
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration

class KapiSymbolProcessor(
    private val apiProcessors: List<ApiProcessor>
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

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val contentTypesByAnnotationName = resolver.getSymbolsWithAnnotation(Consumes::class)
            .filterIsInstance<KSClassDeclaration>()
            .associate { annotationDeclaration ->
                annotationDeclaration.kotlinName.full to annotationDeclaration.getAnnotationsByType(Consumes::class)
                    .first()
                    .contentType
                    .takeIf { it.isNotBlank() }
            }
        val authAnnotationsByName = resolver.getSymbolsWithAnnotation(Auth::class)
            .filterIsInstance<KSClassDeclaration>()
            .associate { annotationDeclaration ->
                annotationDeclaration.kotlinName.full to annotationDeclaration.getAnnotationsByType(Auth::class)
                    .map { it.toApiAuth() }
                    .toList()
            }
        val apiAnnotatedElements = resolver.getSymbolsWithAnnotation(Api::class).filterIsInstance<KSClassDeclaration>()
        val apiDefinitions = apiAnnotatedElements.map {
            it.toApiDefinition(
                contentTypesByAnnotationName = contentTypesByAnnotationName,
                authAnnotationsByName = authAnnotationsByName
            )
        }.toList()

        allApis.addAll(apiDefinitions)

        apiProcessors.forEach { processor ->
            processor.process(round = roundCount, currentApis = apiDefinitions, allApis = allApis)
        }

        roundCount++

        return emptyList()
    }
}
