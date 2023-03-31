@file:OptIn(ExperimentalServerApi::class)

package com.chrynan.kapi.server.example.api

import com.chrynan.kapi.server.core.annotation.Auth
import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import com.chrynan.kapi.server.core.annotation.SecurityRequirement

internal object ApiSecurity {

    object Provider {

        const val NAME = "IdentityOAuthProvider"
    }

    object Scopes {

        const val READ = "read"
        const val WRITE = "write"
        const val ADMIN = "admin"
    }
}

@Auth(
    SecurityRequirement(name = ApiSecurity.Provider.NAME, scopes = [ApiSecurity.Scopes.READ])
)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class RequiresReadScope

@Auth(
    SecurityRequirement(name = ApiSecurity.Provider.NAME, scopes = [ApiSecurity.Scopes.WRITE])
)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class RequiresWriteScope

@Auth(
    SecurityRequirement(name = ApiSecurity.Provider.NAME, scopes = [ApiSecurity.Scopes.ADMIN])
)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class RequiresAdminScope

@Auth(
    SecurityRequirement(
        name = ApiSecurity.Provider.NAME,
        scopes = [ApiSecurity.Scopes.WRITE, ApiSecurity.Scopes.ADMIN],
        scopeRequirementType = Auth.RequirementType.ANY
    )
)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class RequiresWriteOrAdminScope
