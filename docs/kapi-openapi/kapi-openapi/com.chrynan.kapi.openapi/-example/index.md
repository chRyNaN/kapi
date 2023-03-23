//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Example](index.md)

# Example

[common]\
@Serializable

data class [Example](index.md)(val summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val value: JsonElement? = null, val externalValue: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

In all cases, the example value is expected to be compatible with the type schema of its associated value. Tooling implementations MAY choose to validate compatibility automatically, and reject the example value(s) if incompatible.

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#example-object) |

## Constructors

| | |
|---|---|
| [Example](-example.md) | [common]<br>fun [Example](-example.md)(summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, value: JsonElement? = null, externalValue: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>Long description for the example. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation. |
| [externalValue](external-value.md) | [common]<br>val [externalValue](external-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A URI that points to the literal example. This provides the capability to reference examples that cannot easily be included in JSON or YAML documents. The value field and externalValue field are mutually exclusive. See the rules for resolving [Relative References](https://spec.openapis.org/oas/v3.1.0#relativeReferencesURI). |
| [summary](summary.md) | [common]<br>val [summary](summary.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>Short description for the example. |
| [value](value.md) | [common]<br>val [value](value.md): JsonElement? = null<br>Embedded literal example. The value field and externalValue field are mutually exclusive. To represent examples of media types that cannot naturally represented in JSON or YAML, use a string value to contain the example, escaping where necessary. |
