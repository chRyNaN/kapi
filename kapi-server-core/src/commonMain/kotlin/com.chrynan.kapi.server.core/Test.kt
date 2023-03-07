package com.chrynan.kapi.server.core

import com.chrynan.kapi.core.Error
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import io.ktor.util.*
import io.ktor.utils.io.*
import kotlinx.datetime.Instant
import io.ktor.server.application.call

fun Route.test(test: Test) {
    get("") {
        this.call.parameters.get("")
        val name: String = this.call.request.queryParameters.getOrNull("user_name")
            ?: error("user_name parameter value must be present.")
        val other: Int? = this.call.request.queryParameters.getOrNull(name = "other")
        this.call.parameters.getAll(name = "")
        test.apply {
            this@test.getUser(name = name)
        }

        this.call.receiveParameters()
        this.call.receiveMultipart()
        val header: String? = this.call.request.headers.getOrNull(name = "header")
        val multiPartDataMap = this.call.receiveMultipart().readAllParts().associateBy { it.name }

        (multiPartDataMap[""] as PartData.FormItem).value

        val partData = multiPartDataMap.get("")

        when (partData) {
            is PartData.FormItem -> {
                partData.value
            }

            is PartData.FileItem -> {
                partData.streamProvider
            }

            is PartData.BinaryItem -> {
                partData.provider().asStream()
            }

            is PartData.BinaryChannelItem -> {}
            null -> {}
        }

        this.call.respondError(
            error = com.chrynan.kapi.core.Error(
                type = "",
                title = "",
                details = "",
                status = 0,
                instance = "",
                timestamp = kotlinx.datetime.Clock.System.now(),
                help = "",
                signature = null
            )
        )
    }
}

interface Test {
    fun Route.getUser(name: String, other: Int = 0): String = name
}
