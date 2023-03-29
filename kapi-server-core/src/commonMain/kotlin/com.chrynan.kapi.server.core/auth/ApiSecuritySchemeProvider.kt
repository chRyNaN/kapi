package com.chrynan.kapi.server.core.auth

import com.chrynan.kapi.openapi.SecurityScheme
import com.chrynan.kapi.server.core.annotation.Api

/**
 * An interface for an API containing all its supported Open API [SecurityScheme]s. Implementations of this class are
 * generated for every [Api] component, where the data is extracted from the [Api.securitySchemes] property of the
 * [Api] annotation, and is made available via extensions on the [KapiSecurity] component, which itself can be accessed via
 * the [security] property.
 */
interface ApiSecuritySchemeProvider {

    /**
     * The name of the API component that this [ApiSecuritySchemeProvider] provides [SecurityScheme]s for. This should
     * match the [Api.name] value, or if that is blank, the name of the annotated [Api] component.
     */
    val apiName: String

    /**
     * The available Open API [SecurityScheme]s for the associated API component.
     */
    val schemes: List<SecurityScheme>
}
