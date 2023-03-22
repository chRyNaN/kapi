@file:Suppress("unused")

package com.chrynan.kapi.server.core.util

import com.chrynan.kapi.openapi.OpenApi
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.serialization.json.Json
import java.io.File

actual fun Route.registerOpenApi(
    path: String,
    name: String,
    openApi: OpenApi,
    json: Json,
    registerOpenApiDocs: Boolean,
    registerSwaggerUI: Boolean
) {
    val formattedName = getFormattedNameWithSuffix(name = name, suffix = "json")
    val openApiJsonText = json.encodeToString(serializer = OpenApi.serializer(), value = openApi)

    if (registerOpenApiDocs) {
        registerOpenApiDocs(path = path, openApiDocumentString = openApiJsonText)
    }

    if (registerSwaggerUI) {
        registerSwaggerUIAndOpenApi(path = path, apiUrl = formattedName, openApiDocumentString = openApiJsonText)
    } else {
        // Since the above function also handles registering the open api json doc, if we don't register the swagger UI,
        // we have to manually register the open api json doc.
        route(path) {
            get(formattedName) {
                call.respondText(openApiJsonText, ContentType.fromFilePath(formattedName).firstOrNull())
            }
        }
    }
}

private fun Route.registerSwaggerUIAndOpenApi(
    path: String,
    apiUrl: String,
    openApiDocumentString: String
) {
    val config = SwaggerConfig()

    route(path) {
        get(apiUrl) {
            call.respondText(openApiDocumentString, ContentType.fromFilePath(apiUrl).firstOrNull())
        }
        get {
            val fullPath = call.request.path()
            call.respondHtml {
                head {
                    title { +"Swagger UI" }
                    link(
                        href = "${config.packageLocation}@${config.version}/swagger-ui.css",
                        rel = "stylesheet"
                    )
                }
                body {
                    div { id = "swagger-ui" }
                    script(src = "${config.packageLocation}@${config.version}/swagger-ui-bundle.js") {
                        attributes["crossorigin"] = "anonymous"
                    }

                    val src = "${config.packageLocation}@${config.version}/swagger-ui-standalone-preset.js"
                    script(src = src) {
                        attributes["crossorigin"] = "anonymous"
                    }

                    script {
                        unsafe {
                            +"""
                                |window.onload = function() {
                                |    window.ui = SwaggerUIBundle({
                                |        url: '$fullPath/$apiUrl',
                                |        dom_id: '#swagger-ui',
                                |        presets: [
                                |            SwaggerUIBundle.presets.apis,
                                |            SwaggerUIStandalonePreset
                                |        ],
                                |        layout: 'StandaloneLayout'
                                |    });
                                |}
                            """.trimMargin()
                        }
                    }
                }
            }
        }
    }
}

private fun Route.registerOpenApiDocs(
    path: String,
    openApiDocumentString: String
) {
    val config = OpenAPIConfig()
    with(config) {
        val swagger = parser.readContents(openApiDocumentString, null, options)
        File("docs").mkdirs()

        opts.apply {
            config(codegen)
            openAPI(swagger.openAPI)
        }

        generator.opts(opts)
        generator.generate()

        static(path) {
            staticRootFolder = File("docs")
            files(".")
            default("index.html")
        }
    }
}

private fun getFormattedNameWithSuffix(
    name: String,
    @Suppress("SameParameterValue") suffix: String = "json"
): String {
    val i = name.lastIndexOf('.')

    val formattedName = if (i != -1) {
        name.substring(0, i)
    } else {
        name
    }

    val formattedSuffix = suffix.removePrefix(".")

    return "${formattedName}.${formattedSuffix}"
}
