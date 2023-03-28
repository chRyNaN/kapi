package com.chrynan.kapi.server.ksp

import com.chrynan.kapi.server.ksp.processor.JsonApiProcessor
import com.chrynan.kapi.server.ksp.processor.KtorRoutingApiProcessor
import com.chrynan.kapi.server.ksp.processor.KtorValidatorApiProcessor
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class KapiSymbolProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        KapiSymbolProcessor(
            apiProcessors = listOf(
                JsonApiProcessor(logger = environment.logger, codeGenerator = environment.codeGenerator),
                KtorValidatorApiProcessor(logger = environment.logger),
                KtorRoutingApiProcessor(codeGenerator = environment.codeGenerator, logger = environment.logger)
            )
        )
}
