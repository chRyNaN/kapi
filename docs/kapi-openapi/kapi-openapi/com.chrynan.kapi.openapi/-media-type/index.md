//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[MediaType](index.md)

# MediaType

[common]\
@Serializable

data class [MediaType](index.md)(val schema: [Schema](../-schema/index.md)? = null, val example: JsonElement? = null, val examples: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Example](../-example/index.md)&gt;&gt;? = null, val encoding: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Encoding](../-encoding/index.md)&gt;? = null)

Each Media Type Object provides schema and examples for the media type identified by its key.

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

##  Media Type Object Example

```json
{
  "application/json": {
    "schema": {
         "$ref": "#/components/schemas/Pet"
    },
    "examples": {
      "cat" : {
        "summary": "An example of a cat",
        "value":
          {
            "name": "Fluffy",
            "petType": "Cat",
            "color": "White",
            "gender": "male",
            "breed": "Persian"
          }
      },
      "dog": {
        "summary": "An example of a dog with a cat's name",
        "value" :  {
          "name": "Puma",
          "petType": "Dog",
          "color": "Black",
          "gender": "Female",
          "breed": "Mixed"
        },
      "frog": {
          "$ref": "#/components/examples/frog-example"
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
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#media-type-object) |

## Constructors

| | |
|---|---|
| [MediaType](-media-type.md) | [common]<br>fun [MediaType](-media-type.md)(schema: [Schema](../-schema/index.md)? = null, example: JsonElement? = null, examples: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Example](../-example/index.md)&gt;&gt;? = null, encoding: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Encoding](../-encoding/index.md)&gt;? = null) |

## Properties

| Name | Summary |
|---|---|
| [encoding](encoding.md) | [common]<br>val [encoding](encoding.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Encoding](../-encoding/index.md)&gt;? = null<br>A map between a property name and its encoding information. The key, being the property name, MUST exist in the schema as a property. The encoding object SHALL only apply to requestBody objects when the media type is multipart or application/x-www-form-urlencoded. |
| [example](example.md) | [common]<br>val [example](example.md): JsonElement? = null<br>Example of the media type. The example object SHOULD be in the correct format as specified by the media type. The example field is mutually exclusive of the examples field. Furthermore, if referencing a schema which contains an example, the example value SHALL override the example provided by the schema. |
| [examples](examples.md) | [common]<br>val [examples](examples.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Example](../-example/index.md)&gt;&gt;? = null<br>Examples of the media type. Each example object SHOULD match the media type and specified schema if present. The examples field is mutually exclusive of the example field. Furthermore, if referencing a schema which contains an example, the examples value SHALL override the example provided by the schema. |
| [schema](schema.md) | [common]<br>val [schema](schema.md): [Schema](../-schema/index.md)? = null<br>The schema defining the content of the request, response, or parameter. |
