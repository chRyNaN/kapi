package com.chrynan.kapi.server.core.auth

import com.chrynan.kapi.server.core.annotation.Api
import com.chrynan.kapi.openapi.SecurityScheme
import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a definition of a [SecurityScheme] within an [Api] via the [Api.securitySchemes] property. This class is
 * converted from the [com.chrynan.kapi.server.core.annotation.SecurityScheme] annotation class to this model so that
 * it can be serialized.
 */
@Serializable
@ExperimentalServerApi
data class SecurityDefinition(
    @SerialName(value = "provider") val provider: String,
    @SerialName(value = "scheme") val scheme: SecurityScheme
)
