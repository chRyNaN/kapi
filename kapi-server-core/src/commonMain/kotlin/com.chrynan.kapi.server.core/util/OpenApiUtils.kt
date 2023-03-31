@file:Suppress("unused")

package com.chrynan.kapi.server.core.util

import com.chrynan.kapi.openapi.OpenApi
import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.serialization.json.Json

/**
 * Registers the Open API and Swagger UI routes for the provided [openApi] document.
 *
 * @param [path] The base path for the Open API and Swagger UI routes.
 * @param [name] The name of the Open API JSON documentation to be created from the provided [openApi] document.
 * @param [openApi] The [OpenApi] document to create the documentation for.
 * @param [json] The [Json] instance to use when serializing the [openApi] documentation to a JSON file.
 * @param [registerOpenApiDocs] Whether the Open API documentation should be rendered and hosted for this [openApi].
 * @param [registerSwaggerUI] Whether the Swagger UI HTML should be rendered and hosted for this [openApi].
 */
@ExperimentalServerApi
expect fun Route.registerOpenApi(
    path: String,
    name: String,
    openApi: OpenApi,
    json: Json = Json,
    registerOpenApiDocs: Boolean = true,
    registerSwaggerUI: Boolean = true
)
