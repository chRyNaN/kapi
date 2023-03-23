//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[ExternalDocumentation](index.md)

# ExternalDocumentation

[common]\
@Serializable

data class [ExternalDocumentation](index.md)(val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Allows referencing an external resource for extended documentation.

##  External Documentation Object Example

```json
{
  "description": "Find more info here",
  "url": "https://example.com"
}
```

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#external-documentation-object) |

## Constructors

| | |
|---|---|
| [ExternalDocumentation](-external-documentation.md) | [common]<br>fun [ExternalDocumentation](-external-documentation.md)(description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A description of the target documentation. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation. |
| [url](url.md) | [common]<br>val [url](url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>**REQUIRED**. The URL for the target documentation. This MUST be in the form of a URL. |
