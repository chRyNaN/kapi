package com.chrynan.kapi.server.ksp

import com.chrynan.kapi.server.ksp.processor.JsonApiProcessor
import com.chrynan.kapi.server.ksp.processor.KtorRoutingApiProcessor
import com.chrynan.kapi.server.ksp.processor.KtorValidatorApiProcessor
import com.chrynan.kapi.server.processor.core.ApiProcessor
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class KapiSymbolProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val apiProcessors = mutableListOf<ApiProcessor>()
        val config = environment.config

        if (config.jsonProcessorConfig.enabled) {
            apiProcessors.add(JsonApiProcessor(logger = environment.logger, codeGenerator = environment.codeGenerator))
        }

        if (config.ktorProcessorConfig.enabled) {
            apiProcessors.add(KtorValidatorApiProcessor(logger = environment.logger))
        }

        if (config.openApiProcessorConfig.enabled) {
            apiProcessors.add(
                KtorRoutingApiProcessor(
                    codeGenerator = environment.codeGenerator,
                    logger = environment.logger
                )
            )
        }

        return KapiSymbolProcessor(apiProcessors = apiProcessors)
    }
}
