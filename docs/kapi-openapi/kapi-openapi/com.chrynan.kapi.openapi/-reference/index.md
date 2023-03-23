//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Reference](index.md)

# Reference

[common]\
@Serializable

data class [Reference](index.md)(val reference: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

A simple object to allow referencing other components in the OpenAPI document, internally and externally.

The [reference](reference.md) string value contains a URI [RFC3986](https://spec.openapis.org/oas/v3.1.0#bib-RFC3986), which identifies the location of the value being referenced.

The following are the rules from the [resolving Relative References](https://spec.openapis.org/oas/v3.1.0#relative-references-in-uris) section of the specification:

Unless specified otherwise, all properties that are URIs MAY be relative references as defined by [RFC3986](https://spec.openapis.org/oas/v3.1.0#bib-RFC3986).

Relative references, including those in Reference Objects, PathItem Object $ref fields, Link Object operationRef fields and Example Object externalValue fields, are resolved using the referring document as the Base URI according to [RFC3986](https://spec.openapis.org/oas/v3.1.0#bib-RFC3986).

If a URI contains a fragment identifier, then the fragment should be resolved per the fragment resolution mechanism of the referenced document. If the representation of the referenced document is JSON or YAML, then the fragment identifier SHOULD be interpreted as a JSON-Pointer as per [RFC6901](https://spec.openapis.org/oas/v3.1.0#bib-RFC3986).

Relative references in Schema Objects, including any that appear as $id values, use the nearest parent $id as a Base URI, as described by [JSON Schema Specification Draft 2020-12](https://tools.ietf.org/html/draft-bhutton-json-schema-00#section-8.2). If no parent schema contains an $id, then the Base URI MUST be determined according to [RFC3986](https://spec.openapis.org/oas/v3.1.0#bib-RFC3986).

This object cannot be extended with additional properties and any properties added SHALL be ignored.

Note that this restriction on additional properties is a difference between Reference Objects and Schema Objects that contain a $ref keyword.

##  Reference Object Example

```json
{
	"$ref": "#/components/schemas/Pet"
}
```

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#reference-object) |

## Constructors

| | |
|---|---|
| [Reference](-reference.md) | [common]<br>fun [Reference](-reference.md)(reference: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A description which by default SHOULD override that of the referenced component. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation. If the referenced object-type does not allow a description field, then this field has no effect. |
| [reference](reference.md) | [common]<br>val [reference](reference.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>**REQUIRED**. The reference identifier. This MUST be in the form of a URI. |
| [summary](summary.md) | [common]<br>val [summary](summary.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A short summary which by default SHOULD override that of the referenced component. If the referenced object-type does not allow a summary field, then this field has no effect. |
