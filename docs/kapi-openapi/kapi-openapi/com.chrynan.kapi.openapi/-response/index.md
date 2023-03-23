//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Response](index.md)

# Response

[common]\
@Serializable

data class [Response](index.md)(val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val headers: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Header](../-header/index.md)&gt;&gt;? = null, val content: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [MediaType](../-media-type/index.md)&gt;? = null, val links: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Link](../-link/index.md)&gt;&gt;? = null)

Describes a single response from an API Operation, including design-time, static links to operations based on the response.

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

##  Response Object Example

```json
{
  "description": "A complex object array response",
  "content": {
    "application/json": {
      "schema": {
        "type": "array",
        "items": {
          "$ref": "#/components/schemas/VeryComplexType"
        }
      }
    }
  }
}
```

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#response-object) |

## Constructors

| | |
|---|---|
| [Response](-response.md) | [common]<br>fun [Response](-response.md)(description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), headers: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Header](../-header/index.md)&gt;&gt;? = null, content: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [MediaType](../-media-type/index.md)&gt;? = null, links: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Link](../-link/index.md)&gt;&gt;? = null) |

## Properties

| Name | Summary |
|---|---|
| [content](content.md) | [common]<br>val [content](content.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [MediaType](../-media-type/index.md)&gt;? = null<br>A map containing descriptions of potential response payloads. The key is a media type or media type range and the value describes it. For responses that match multiple keys, only the most specific key is applicable. e.g. text/plain overrides text/\* |
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>**REQUIRED**. A description of the response. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation. |
| [headers](headers.md) | [common]<br>val [headers](headers.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Header](../-header/index.md)&gt;&gt;? = null<br>Maps a header name to its definition. [RFC7230](https://spec.openapis.org/oas/v3.1.0#bib-RFC7230) states header names are case-insensitive. If a response header is defined with the name &quot;Content-Type&quot;, it SHALL be ignored. |
| [links](links.md) | [common]<br>val [links](links.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Link](../-link/index.md)&gt;&gt;? = null<br>A map of operations links that can be followed from the response. The key of the map is a short name for the link, following the naming constraints of the names for Component Objects. |
