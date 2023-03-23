//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Server](index.md)

# Server

[common]\
@Serializable

data class [Server](index.md)(val url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val variables: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ServerVariable](../-server-variable/index.md)&gt;? = null)

An object representing a Server.

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

##  Server Object Example

```json
{
  "url": "https://development.gigantic-server.com/v1",
  "description": "Development server"
}
```

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#server-object) |

## Constructors

| | |
|---|---|
| [Server](-server.md) | [common]<br>fun [Server](-server.md)(url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, variables: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ServerVariable](../-server-variable/index.md)&gt;? = null) |

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>An optional string describing the host designated by the URL. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation. |
| [url](url.md) | [common]<br>val [url](url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>**REQUIRED**. A URL to the target host. This URL supports Server Variables and MAY be relative, to indicate that the host location is relative to the location where the OpenAPI document is being served. Variable substitutions will be made when a variable is named in {brackets}. |
| [variables](variables.md) | [common]<br>val [variables](variables.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ServerVariable](../-server-variable/index.md)&gt;? = null<br>A map between a variable name and its value. The value is used for substitution in the server’s URL template. |
