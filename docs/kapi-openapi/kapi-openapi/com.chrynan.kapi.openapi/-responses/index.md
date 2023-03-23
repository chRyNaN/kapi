//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Responses](index.md)

# Responses

[common]\
@Serializable(with = [ResponsesSerializer::class](../../../../kapi-openapi/com.chrynan.kapi.openapi/-responses-serializer/index.md))

data class [Responses](index.md)(val default: [ReferenceOrType](../-reference-or-type/index.md)&lt;[Response](../-response/index.md)&gt;? = null, val responses: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Response](../-response/index.md)&gt;&gt;? = null)

A container for the expected responses of an operation. The container maps a HTTP response code to the expected response.

The documentation is not necessarily expected to cover all possible HTTP response codes because they may not be known in advance. However, documentation is expected to cover a successful operation response and any known errors.

The default MAY be used as a default response object for all HTTP codes that are not covered individually by the Responses Object.

The Responses Object MUST contain at least one response code, and if only one response code is provided it SHOULD be the response for a successful operation call.

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

##  Responses Object Example

```json
{
  "200": {
    "description": "a pet to be returned",
    "content": {
      "application/json": {
        "schema": {
          "$ref": "#/components/schemas/Pet"
        }
      }
    }
  },
  "default": {
    "description": "Unexpected error",
    "content": {
      "application/json": {
        "schema": {
          "$ref": "#/components/schemas/ErrorModel"
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
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#responses-object) |

## Constructors

| | |
|---|---|
| [Responses](-responses.md) | [common]<br>fun [Responses](-responses.md)(default: [ReferenceOrType](../-reference-or-type/index.md)&lt;[Response](../-response/index.md)&gt;? = null, responses: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Response](../-response/index.md)&gt;&gt;? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [default](default.md) | [common]<br>val [default](default.md): [ReferenceOrType](../-reference-or-type/index.md)&lt;[Response](../-response/index.md)&gt;? = null<br>The documentation of responses other than the ones declared for specific HTTP response codes. Use this field to cover undeclared responses. |
| [responses](responses.md) | [common]<br>val [responses](responses.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Response](../-response/index.md)&gt;&gt;? = null<br>Any HTTP status code can be used as the property name, but only one property per code, to describe the expected response for that HTTP status code. This field MUST be enclosed in quotation marks (for example, “200”) for compatibility between JSON and YAML. To define a range of response codes, this field MAY contain the uppercase wildcard character X. For example, 2XX represents all response codes between 200-299. Only the following range definitions are allowed: 1XX, 2XX, 3XX, 4XX, and 5XX. If a response is defined using an explicit code, the explicit code definition takes precedence over the range definition for that code. |
