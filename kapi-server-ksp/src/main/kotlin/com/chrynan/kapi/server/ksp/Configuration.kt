package com.chrynan.kapi.server.ksp

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Configuration(
    @SerialName(value = "generate_json") val generateJson: Boolean = false,
    @SerialName(value = "generate_ktor_bindings") val generateKtorBindings: Boolean = false,
    @SerialName(value = "generate_open_api_spec") val generateOpenApiSpec: Boolean = false
)
