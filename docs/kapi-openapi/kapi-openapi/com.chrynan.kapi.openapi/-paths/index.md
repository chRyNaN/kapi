//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Paths](index.md)

# Paths

[common]\
@Serializable(with = [PathsSerializer::class](../../../../kapi-openapi/com.chrynan.kapi.openapi/-paths-serializer/index.md))

data class [Paths](index.md)(val paths: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [PathItem](../-path-item/index.md)&gt;? = null)

Holds the relative paths to the individual endpoints and their operations. The path is appended to the URL from the Server Object in order to construct the full URL. The Paths MAY be empty, due to [Access Control List (ACL)

- 
   constraints](https://spec.openapis.org/oas/v3.1.0#securityFiltering).

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

Assuming the following paths, the concrete definition, /pets/mine, will be matched first if used:

```kotlin
/pets/{petId}
/pets/mine
```

The following paths are considered identical and invalid:

```kotlin
/pets/{petId}
/pets/{name}
```

The following may lead to ambiguous resolution:

```kotlin
/{entity}/me
/books/{id}
```

##  Paths Object Example

```json
{
  "/pets": {
    "get": {
      "description": "Returns all pets from the system that the user has access to",
      "responses": {
        "200": {
          "description": "A list of pets.",
          "content": {
            "application/json": {
              "schema": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/pet"
                }
              }
            }
          }
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
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#paths-object) |

## Constructors

| | |
|---|---|
| [Paths](-paths.md) | [common]<br>fun [Paths](-paths.md)(paths: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [PathItem](../-path-item/index.md)&gt;? = null) |

## Properties

| Name | Summary |
|---|---|
| [paths](paths.md) | [common]<br>val [paths](paths.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [PathItem](../-path-item/index.md)&gt;? = null |
