//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[RequestBody](index.md)

# RequestBody

[common]\
@Serializable

data class [RequestBody](index.md)(val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val content: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [MediaType](../-media-type/index.md)&gt;, val required: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

Describes a single request body.

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#request-body-object) |

## Constructors

| | |
|---|---|
| [RequestBody](-request-body.md) | [common]<br>fun [RequestBody](-request-body.md)(description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, content: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [MediaType](../-media-type/index.md)&gt;, required: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |

## Properties

| Name | Summary |
|---|---|
| [content](content.md) | [common]<br>val [content](content.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [MediaType](../-media-type/index.md)&gt;<br>**REQUIRED**. The content of the request body. The key is a media type or media type range and the value describes it. For requests that match multiple keys, only the most specific key is applicable. e.g. text/plain overrides text/\* |
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A brief description of the request body. This could contain examples of use. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation. |
| [required](required.md) | [common]<br>val [required](required.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Determines if the request body is required in the request. Defaults to false. |
