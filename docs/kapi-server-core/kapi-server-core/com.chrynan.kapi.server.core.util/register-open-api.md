//[kapi-server-core](../../index.md)/[com.chrynan.kapi.server.core.util](index.md)/[registerOpenApi](register-open-api.md)

# registerOpenApi

[common]\
expect fun Route.[registerOpenApi](register-open-api.md)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), openApi: [OpenApi](../../../kapi-openapi/kapi-openapi/com.chrynan.kapi.openapi/-open-api/index.md), json: Json = Json, registerOpenApiDocs: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, registerSwaggerUI: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true)

Registers the Open API and Swagger UI routes for the provided [openApi](register-open-api.md) document.

#### Parameters

common

| | |
|---|---|
| path | The base path for the Open API and Swagger UI routes. |
| name | The name of the Open API JSON documentation to be created from the provided [openApi](register-open-api.md) document. |
| openApi | The [OpenApi](../../../kapi-openapi/kapi-openapi/com.chrynan.kapi.openapi/-open-api/index.md) document to create the documentation for. |
| json | The Json instance to use when serializing the [openApi](register-open-api.md) documentation to a JSON file. |
| registerOpenApiDocs | Whether the Open API documentation should be rendered and hosted for this [openApi](register-open-api.md). |
| registerSwaggerUI | Whether the Swagger UI HTML should be rendered and hosted for this [openApi](register-open-api.md). |

[jvm]\
actual fun Route.[registerOpenApi](register-open-api.md)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), openApi: [OpenApi](../../../kapi-openapi/kapi-openapi/com.chrynan.kapi.openapi/-open-api/index.md), json: Json, registerOpenApiDocs: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), registerSwaggerUI: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))
