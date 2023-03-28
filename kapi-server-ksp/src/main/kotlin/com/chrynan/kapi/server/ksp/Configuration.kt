package com.chrynan.kapi.server.ksp

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
internal data class Configuration(
    @SerialName(value = JSON_PROCESSOR_CONFIG) val jsonProcessorConfig: JsonProcessorConfig = JsonProcessorConfig(),
    @SerialName(value = KTOR_PROCESSOR_CONFIG) val ktorProcessorConfig: KtorProcessorConfig = KtorProcessorConfig(),
    @SerialName(value = OPEN_API_PROCESSOR_CONFIG) val openApiProcessorConfig: OpenApiProcessorConfig = OpenApiProcessorConfig()
) {

    @Serializable
    internal data class JsonProcessorConfig(
        @SerialName(value = ENABLED) val enabled: Boolean = true
    ) {

        companion object SerialNames {

            const val ENABLED = "enabled"
        }
    }

    @Serializable
    internal data class KtorProcessorConfig(
        @SerialName(value = ENABLED) val enabled: Boolean = true
    ) {

        companion object SerialNames {

            const val ENABLED = "enabled"
        }
    }

    @Serializable
    internal data class OpenApiProcessorConfig(
        @SerialName(value = ENABLED) val enabled: Boolean = true
    ) {

        companion object SerialNames {

            const val ENABLED = "enabled"
        }
    }

    companion object SerialNames {

        const val JSON_PROCESSOR_CONFIG = "jsonProcessorConfig"
        const val KTOR_PROCESSOR_CONFIG = "ktorProcessorConfig"
        const val OPEN_API_PROCESSOR_CONFIG = "openApiProcessorConfig"
    }
}

internal const val NAME_KAPI_CONFIG = "kapi.config"

internal val SymbolProcessorEnvironment.config: Configuration
    get() {
        val jsonEnabled =
            this.options["${NAME_KAPI_CONFIG}.${Configuration.JSON_PROCESSOR_CONFIG}.${Configuration.JsonProcessorConfig.ENABLED}"]?.toBoolean()
                ?: true
        val ktorEnabled =
            this.options["${NAME_KAPI_CONFIG}.${Configuration.KTOR_PROCESSOR_CONFIG}.${Configuration.JsonProcessorConfig.ENABLED}"]?.toBoolean()
                ?: true
        val openApiEnabled =
            this.options["${NAME_KAPI_CONFIG}.${Configuration.OPEN_API_PROCESSOR_CONFIG}.${Configuration.JsonProcessorConfig.ENABLED}"]?.toBoolean()
                ?: true

        return Configuration(
            jsonProcessorConfig = Configuration.JsonProcessorConfig(enabled = jsonEnabled),
            ktorProcessorConfig = Configuration.KtorProcessorConfig(enabled = ktorEnabled),
            openApiProcessorConfig = Configuration.OpenApiProcessorConfig(enabled = openApiEnabled)
        )
    }
