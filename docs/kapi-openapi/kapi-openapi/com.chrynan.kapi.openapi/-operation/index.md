//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Operation](index.md)

# Operation

[common]\
@Serializable

data class [Operation](index.md)(val tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null, val summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val externalDocs: [ExternalDocumentation](../-external-documentation/index.md)? = null, val operationId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ReferenceOrType](../-reference-or-type/index.md)&lt;[Parameter](../-parameter/index.md)&gt;&gt;? = null, val requestBody: [ReferenceOrType](../-reference-or-type/index.md)&lt;[RequestBody](../-request-body/index.md)&gt;? = null, val responses: [Responses](../-responses/index.md)? = null, val callbacks: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Callback](../-callback/index.md)&gt;&gt;? = null, val deprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val security: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;JsonElement&gt;? = null, val servers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Server](../-server/index.md)&gt;? = null)

Describes a single API operation on a path.

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

##  Operation Object Example

```json
{
  "tags": [
    "pet"
  ],
  "summary": "Updates a pet in the store with form data",
  "operationId": "updatePetWithForm",
  "parameters": [
    {
      "name": "petId",
      "in": "path",
      "description": "ID of pet that needs to be updated",
      "required": true,
      "schema": {
        "type": "string"
      }
    }
  ],
  "requestBody": {
    "content": {
      "application/x-www-form-urlencoded": {
        "schema": {
          "type": "object",
          "properties": {
            "name": {
              "description": "Updated name of the pet",
              "type": "string"
            },
            "status": {
              "description": "Updated status of the pet",
              "type": "string"
            }
          },
          "required": ["status"]
        }
      }
    }
  },
  "responses": {
    "200": {
      "description": "Pet updated.",
      "content": {
        "application/json": {},
        "application/xml": {}
      }
    },
    "405": {
      "description": "Method Not Allowed",
      "content": {
        "application/json": {},
        "application/xml": {}
      }
    }
  },
  "security": [
    {
      "petstore_auth": [
        "write:pets",
        "read:pets"
      ]
    }
  ]
}
```

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#operation-object) |

## Constructors

| | |
|---|---|
| [Operation](-operation.md) | [common]<br>fun [Operation](-operation.md)(tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null, summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, externalDocs: [ExternalDocumentation](../-external-documentation/index.md)? = null, operationId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ReferenceOrType](../-reference-or-type/index.md)&lt;[Parameter](../-parameter/index.md)&gt;&gt;? = null, requestBody: [ReferenceOrType](../-reference-or-type/index.md)&lt;[RequestBody](../-request-body/index.md)&gt;? = null, responses: [Responses](../-responses/index.md)? = null, callbacks: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Callback](../-callback/index.md)&gt;&gt;? = null, deprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, security: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;JsonElement&gt;? = null, servers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Server](../-server/index.md)&gt;? = null) |

## Properties

| Name | Summary |
|---|---|
| [callbacks](callbacks.md) | [common]<br>val [callbacks](callbacks.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [ReferenceOrType](../-reference-or-type/index.md)&lt;[Callback](../-callback/index.md)&gt;&gt;? = null<br>A map of possible out-of band callbacks related to the parent operation. The key is a unique identifier for the Callback Object. Each value in the map is a Callback Object that describes a request that may be initiated by the API provider and the expected responses. |
| [deprecated](deprecated.md) | [common]<br>val [deprecated](deprecated.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Declares this operation to be deprecated. Consumers SHOULD refrain from usage of the declared operation. Default value is false. |
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A verbose explanation of the operation behavior. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation. |
| [externalDocs](external-docs.md) | [common]<br>val [externalDocs](external-docs.md): [ExternalDocumentation](../-external-documentation/index.md)? = null<br>Additional external documentation for this operation. |
| [operationId](operation-id.md) | [common]<br>val [operationId](operation-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>Unique string used to identify the operation. The id MUST be unique among all operations described in the API. The operationId value is case-sensitive. Tools and libraries MAY use the operationId to uniquely identify an operation, therefore, it is RECOMMENDED to follow common programming naming conventions. |
| [parameters](parameters.md) | [common]<br>val [parameters](parameters.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ReferenceOrType](../-reference-or-type/index.md)&lt;[Parameter](../-parameter/index.md)&gt;&gt;? = null<br>A list of parameters that are applicable for this operation. If a parameter is already defined at the Path Item, the new definition will override it but can never remove it. The list MUST NOT include duplicated parameters. A unique parameter is defined by a combination of a name and location. The list can use the Reference Object to link to parameters that are defined at the OpenAPI Object’s components/parameters. |
| [requestBody](request-body.md) | [common]<br>val [requestBody](request-body.md): [ReferenceOrType](../-reference-or-type/index.md)&lt;[RequestBody](../-request-body/index.md)&gt;? = null<br>The request body applicable for this operation. The requestBody is fully supported in HTTP methods where the HTTP 1.1 specification RFC7231 has explicitly defined semantics for request bodies. In other cases where the HTTP spec is vague (such as GET, HEAD and DELETE), requestBody is permitted but does not have well-defined semantics and SHOULD be avoided if possible. |
| [responses](responses.md) | [common]<br>val [responses](responses.md): [Responses](../-responses/index.md)? = null<br>The list of possible responses as they are returned from executing this operation. |
| [security](security.md) | [common]<br>val [security](security.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;JsonElement&gt;? = null<br>A declaration of which security mechanisms can be used for this operation. The list of values includes alternative security requirement objects that can be used. Only one of the security requirement objects need to be satisfied to authorize a request. To make security optional, an empty security requirement ({}) can be included in the array. This definition overrides any declared top-level security. To remove a top-level security declaration, an empty array can be used. |
| [servers](servers.md) | [common]<br>val [servers](servers.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Server](../-server/index.md)&gt;? = null<br>An alternative server array to service this operation. If an alternative server object is specified at the Path Item Object or Root level, it will be overridden by this value. |
| [summary](summary.md) | [common]<br>val [summary](summary.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A short summary of what the operation does. |
| [tags](tags.md) | [common]<br>val [tags](tags.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null<br>A list of tags for API documentation control. Tags can be used for logical grouping of operations by resources or any other qualifier. |
