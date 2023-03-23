package com.chrynan.kapi.server.example

import com.chrynan.kapi.core.Response
import com.chrynan.kapi.server.core.annotation.*
import com.chrynan.kapi.server.core.annotation.GET
import com.chrynan.kapi.server.core.annotation.POST
import com.chrynan.kapi.server.core.annotation.Body
import com.chrynan.kapi.server.core.annotation.Header
import com.chrynan.kapi.server.core.annotation.Path
import com.chrynan.kapi.server.core.annotation.Query
import com.chrynan.kapi.server.core.util.registerOpenApi
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.routing.*

@Api(basePath = "base")
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
