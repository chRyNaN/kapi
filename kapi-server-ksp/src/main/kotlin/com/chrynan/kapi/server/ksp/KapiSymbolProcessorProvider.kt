package com.chrynan.kapi.server.ksp

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class KapiSymbolProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        KapiSymbolProcessor(
            apiProcessors = listOf(
                JsonApiProcessor(
                    logger = environment.logger,
                    codeGenerator = environment.codeGenerator
                )
            )
        )
}
