package com.chrynan.kapi.server.example

import com.chrynan.kapi.core.Response
import com.chrynan.kapi.server.core.annotation.*
import com.chrynan.kapi.server.core.annotation.GET
import com.chrynan.kapi.server.core.annotation.POST
import com.chrynan.kapi.server.core.annotation.Body
import com.chrynan.kapi.server.core.annotation.Header
import com.chrynan.kapi.server.core.annotation.Path
import com.chrynan.kapi.server.core.annotation.Query
import com.chrynan.kapi.server.core.auth.ScopeRequirementPolicy
import com.chrynan.kapi.server.core.auth.requireOauthScopes
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.routing.*

@Auth(
    SecurityRequirement(name = "OAuthProvider", scopes = ["read"]),
    requirementType = Auth.RequirementType.ALL
)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class RequiresRead

@Auth(
    SecurityRequirement(name = "OAuthProvider", scopes = ["write"]),
    requirementType = Auth.RequirementType.ALL
)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class RequiresWrite

@Api(
    basePath = "base",
    securitySchemes = [
        SecurityScheme(
            name = "OAuthProvider",
            type = SecurityScheme.Type.OAUTH2,
            flows = [
                OAuthFlow(
                    type = OAuthFlow.Type.AUTHORIZATION_CODE,
                    authorizationUrl = "",
                    scopes = [
                        OAuthScope("read"),
                        OAuthScope("write")
                    ]
                )
            ]
        )
    ]
)
interface ExampleApi {

    @GET(path = "/user/{id}")
    @Produces(
        errors = [
            Error(
                statusCode = 404,
                exception = NotFoundException::class,
                title = ""
            )
        ]
    )
    @ApplicationFormUrlEncoded
    @RequiresRead
    suspend fun getUserResponse(
        @Path("id") id: String,
        @Query("query") query: String,
        @Header("header") header: String
    ): Response<String>

    @GET("/user/{id}")
    suspend fun getUser(
        @Path id: String
    ): String

    @POST(path = "/message")
    @RequiresRead
    @RequiresWrite
    suspend fun Route.postMessage(
        @Body message: String,
        isEncrypted: Boolean = false
    )

    @POST(path = "/message/{id}")
    suspend fun ApplicationCall.editMessage(
        @Path("id") id: String,
        @Body message: String,
        unit: Unit,
        parameters: Parameters,
        multiPartData: MultiPartData
    )
}
