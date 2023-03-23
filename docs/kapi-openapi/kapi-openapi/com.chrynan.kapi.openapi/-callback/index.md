//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Callback](index.md)

# Callback

[common]\
@Serializable(with = [CallbackSerializer::class](../../../../kapi-openapi/com.chrynan.kapi.openapi/-callback-serializer/index.md))

data class [Callback](index.md)(val paths: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[PathItem](../-path-item/index.md)&gt;&gt;? = null)

A map of possible out-of band callbacks related to the parent operation. Each value in the map is a Path Item Object that describes a set of requests that may be initiated by the API provider and the expected responses. The key value used to identify the path item object is an expression, evaluated at runtime, that identifies a URL to use for the callback operation.

To describe incoming requests from the API provider independent from another API call, use the webhooks field.

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#callback-object) |

## Constructors

| | |
|---|---|
| [Callback](-callback.md) | [common]<br>fun [Callback](-callback.md)(paths: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[PathItem](../-path-item/index.md)&gt;&gt;? = null) |

## Properties

| Name | Summary |
|---|---|
| [paths](paths.md) | [common]<br>val [paths](paths.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[PathItem](../-path-item/index.md)&gt;&gt;? = null |
