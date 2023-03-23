//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Discriminator](index.md)

# Discriminator

[common]\
@Serializable

data class [Discriminator](index.md)(val propertyName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val mapping: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null)

When request bodies or response payloads may be one of a number of different schemas, a discriminator object can be used to aid in serialization, deserialization, and validation. The discriminator is a specific object in a schema which is used to inform the consumer of the document of an alternative schema based on the value associated with it.

When using the discriminator, inline schemas will not be considered.

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

The discriminator object is legal only when using one of the composite keywords oneOf, anyOf, allOf.

In OAS 3.0, a response payload MAY be described to be exactly one of any number of types:

```yaml
MyResponseType:
  oneOf:
  - $ref: '#/components/schemas/Cat'
  - $ref: '#/components/schemas/Dog'
  - $ref: '#/components/schemas/Lizard'
```

which means the payload MUST, by validation, match exactly one of the schemas described by Cat, Dog, or Lizard. In this case, a discriminator MAY act as a “hint” to shortcut validation and selection of the matching schema which may be a costly operation, depending on the complexity of the schema. We can then describe exactly which field tells us which schema to use:

```yaml
MyResponseType:
  oneOf:
  - $ref: '#/components/schemas/Cat'
  - $ref: '#/components/schemas/Dog'
  - $ref: '#/components/schemas/Lizard'
  discriminator:
    propertyName: petType
```

The expectation now is that a property with name petType MUST be present in the response payload, and the value will correspond to the name of a schema defined in the OAS document. Thus, the response payload:

```json
{
  "id": 12345,
  "petType": "Cat"
}
```

Will indicate that the Cat schema be used in conjunction with this payload.

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#discriminator-object) |

## Constructors

| | |
|---|---|
| [Discriminator](-discriminator.md) | [common]<br>fun [Discriminator](-discriminator.md)(propertyName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), mapping: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null) |

## Properties

| Name | Summary |
|---|---|
| [mapping](mapping.md) | [common]<br>val [mapping](mapping.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null<br>An object to hold mappings between payload values and schema names or references. |
| [propertyName](property-name.md) | [common]<br>val [propertyName](property-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>**REQUIRED**. The name of the property in the payload that will hold the discriminator value. |
