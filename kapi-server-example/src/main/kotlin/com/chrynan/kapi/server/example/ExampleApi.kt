package com.chrynan.kapi.server.example

import com.chrynan.kapi.core.Response
import com.chrynan.kapi.core.annotation.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.routing.*

@Api(basePath = "base")
interface ExampleApi {

    @GET("/user/{id}")
    @Errors(
        Error(
            statusCode = 404,
            exception = NotFoundException::class,
            title = ""
        )
    )
    @ResponseHeaders(
        Header(name = "test", value = "", onlyIfAbsent = true)
    )
    @FormUrlEncoded
    suspend fun getUser(
        @Path("id") id: String,
        @Query("query") query: String,
        @Header("header") header: String
    ): Response<String>

    @POST("/message")
    suspend fun Route.postMessage(
        @Body message: String,
        isEncrypted: Boolean = false
    )

    @POST("/message/{id}")
    suspend fun ApplicationCall.editMessage(
        @Path("id") id: String,
        @Body message: String,
        unit: Unit,
        parameters: Parameters,
        multiPartData: MultiPartData
    )
}
