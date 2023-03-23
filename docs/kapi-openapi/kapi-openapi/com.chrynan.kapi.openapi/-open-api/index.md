//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[OpenApi](index.md)

# OpenApi

[common]\
@Serializable

data class [OpenApi](index.md)(val openApiVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val info: [Info](../-info/index.md), val jsonSchemaDialect: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val servers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Server](../-server/index.md)&gt;? = null, val paths: [Paths](../-paths/index.md)? = null, val webhooks: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[PathItem](../-path-item/index.md)&gt;&gt;? = null, val components: [Components](../-components/index.md)? = null, val security: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;JsonElement&gt;? = null, val tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Tag](../-tag/index.md)&gt;? = null, val externalDocs: [ExternalDocumentation](../-external-documentation/index.md)? = null)

This is the root object of the OpenAPI document.

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#openapi-object) |

## Constructors

| | |
|---|---|
| [OpenApi](-open-api.md) | [common]<br>fun [OpenApi](-open-api.md)(openApiVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), info: [Info](../-info/index.md), jsonSchemaDialect: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, servers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Server](../-server/index.md)&gt;? = null, paths: [Paths](../-paths/index.md)? = null, webhooks: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[PathItem](../-path-item/index.md)&gt;&gt;? = null, components: [Components](../-components/index.md)? = null, security: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;JsonElement&gt;? = null, tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Tag](../-tag/index.md)&gt;? = null, externalDocs: [ExternalDocumentation](../-external-documentation/index.md)? = null) |

## Properties

| Name | Summary |
|---|---|
| [components](components.md) | [common]<br>val [components](components.md): [Components](../-components/index.md)? = null<br>An element to hold various schemas for the document. |
| [externalDocs](external-docs.md) | [common]<br>val [externalDocs](external-docs.md): [ExternalDocumentation](../-external-documentation/index.md)? = null<br>Additional external documentation. |
| [info](info.md) | [common]<br>val [info](info.md): [Info](../-info/index.md)<br>REQUIRED. Provides metadata about the API. The metadata MAY be used by tooling as required. |
| [jsonSchemaDialect](json-schema-dialect.md) | [common]<br>val [jsonSchemaDialect](json-schema-dialect.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>The default value for the $schema keyword within Schema Objects contained within this OAS document. This MUST be in the form of a URI. |
| [openApiVersion](open-api-version.md) | [common]<br>val [openApiVersion](open-api-version.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>REQUIRED. This string MUST be the version number of the OpenAPI Specification that the OpenAPI document uses. The openapi field SHOULD be used by tooling to interpret the OpenAPI document. This is not related to the API info.version string. |
| [paths](paths.md) | [common]<br>val [paths](paths.md): [Paths](../-paths/index.md)? = null<br>The available paths and operations for the API. |
| [security](security.md) | [common]<br>val [security](security.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;JsonElement&gt;? = null<br>A declaration of which security mechanisms can be used across the API. The list of values includes alternative security requirement objects that can be used. Only one of the security requirement objects need to be satisfied to authorize a request. Individual operations can override this definition. To make security optional, an empty security requirement ({}) can be included in the array. |
| [servers](servers.md) | [common]<br>val [servers](servers.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Server](../-server/index.md)&gt;? = null<br>An array of Server Objects, which provide connectivity information to a target server. If the servers property is not provided, or is an empty array, the default value would be a Server Object with a url value of /. |
| [tags](tags.md) | [common]<br>val [tags](tags.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Tag](../-tag/index.md)&gt;? = null<br>A list of tags used by the document with additional metadata. The order of the tags can be used to reflect on their order by the parsing tools. Not all tags that are used by the Operation Object must be declared. The tags that are not declared MAY be organized randomly or based on the tools’ logic. Each tag name in the list MUST be unique. |
| [webhooks](webhooks.md) | [common]<br>val [webhooks](webhooks.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[PathItem](../-path-item/index.md)&gt;&gt;? = null<br>The incoming webhooks that MAY be received as part of this API and that the API consumer MAY choose to implement. Closely related to the callbacks feature, this section describes requests initiated other than by an API call, for example by an out of band registration. The key name is a unique string to refer to each webhook, while the (optionally referenced) Path Item Object describes a request that may be initiated by the API provider and the expected responses. An example is available. |
