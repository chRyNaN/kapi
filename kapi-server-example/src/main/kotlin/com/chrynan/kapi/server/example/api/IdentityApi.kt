package com.chrynan.kapi.server.example.api

import com.chrynan.kapi.server.core.annotation.*
import com.chrynan.kapi.server.core.annotation.GET
import com.chrynan.kapi.server.core.annotation.Path
import com.chrynan.kapi.server.example.model.GetIdentityPageBody
import com.chrynan.kapi.server.example.model.Identity
import com.chrynan.kapi.server.example.model.Page
import io.ktor.server.plugins.*

@Api(
    basePath = "identity",
    info = Info(
        title = "Identity API",
        summary = "Provides access to the identity related endpoints of the application."
    ),
    securitySchemes = [
        SecurityScheme(
            name = ApiSecurity.Provider.NAME,
            type = SecurityScheme.Type.OAUTH2,
            flows = [
                OAuthFlow(
                    type = OAuthFlow.Type.AUTHORIZATION_CODE,
                    authorizationUrl = "https://example.com/auth",
                    scopes = [
                        OAuthScope(name = ApiSecurity.Scopes.READ),
                        OAuthScope(name = ApiSecurity.Scopes.WRITE),
                        OAuthScope(name = ApiSecurity.Scopes.ADMIN)
                    ]
                )
            ]
        )
    ]
)
interface IdentityApi {

    @GET(path = "/{id}")
    @ApplicationFormUrlEncoded
    @RequiresReadScope
    @Produces(
        errors = [
            Error(
                statusCode = 404,
                exception = NotFoundException::class,
                title = "No Identity Found."
            )
        ]
    )
    suspend fun getIdentity(
        @Path("id") id: String
    ): Identity

    @POST("")
    @ApplicationJson
    @RequiresAdminScope
    suspend fun getIdentityPage(
        @Body body: GetIdentityPageBody
    ): Page<Identity>
}
