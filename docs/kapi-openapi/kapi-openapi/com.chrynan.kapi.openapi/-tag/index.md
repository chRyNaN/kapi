//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Tag](index.md)

# Tag

[common]\
@Serializable

data class [Tag](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val externalDocs: [ExternalDocumentation](../-external-documentation/index.md)? = null)

Adds metadata to a single tag that is used by the Operation Object. It is not mandatory to have a Tag Object per tag defined in the Operation Object instances.

##  Tag Object Example

```json
{
	"name": "pet",
	"description": "Pets operations"
}
```

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#tag-object) |

## Constructors

| | |
|---|---|
| [Tag](-tag.md) | [common]<br>fun [Tag](-tag.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, externalDocs: [ExternalDocumentation](../-external-documentation/index.md)? = null) |

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A description for the tag. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation. |
| [externalDocs](external-docs.md) | [common]<br>val [externalDocs](external-docs.md): [ExternalDocumentation](../-external-documentation/index.md)? = null<br>Additional external documentation for this tag. |
| [name](name.md) | [common]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>**REQUIRED**. The name of the tag. |
