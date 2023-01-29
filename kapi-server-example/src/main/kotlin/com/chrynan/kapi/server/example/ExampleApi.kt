package com.chrynan.kapi.server.example

import com.chrynan.kapi.core.annotation.Api
import com.chrynan.kapi.core.annotation.GET
import com.chrynan.kapi.core.annotation.Path

@com.chrynan.kapi.core.annotation.Api
interface ExampleApi {

    @com.chrynan.kapi.core.annotation.GET("/user/{id}")
    suspend fun getUser(@com.chrynan.kapi.core.annotation.Path("id") id: String): String
}
