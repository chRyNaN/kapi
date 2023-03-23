//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Link](index.md)

# Link

[common]\
@Serializable

data class [Link](index.md)(val operationRef: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val operationId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val parameters: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), JsonElement&gt;? = null, val requestBody: JsonElement? = null, val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val server: [Server](../-server/index.md)? = null)

The Link object represents a possible design-time link for a response. The presence of a link does not guarantee the caller’s ability to successfully invoke it, rather it provides a known relationship and traversal mechanism between responses and other operations.

Unlike dynamic links (i.e. links provided in the response payload), the OAS linking mechanism does not require link information in the runtime response.

For computing links, and providing instructions to execute them, a [runtime expression](https://spec.openapis.org/oas/v3.1.0#runtimeExpression) is used for accessing values in an operation and using them as parameters while invoking the linked operation.

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

A linked operation MUST be identified using either an operationRef or operationId. In the case of an operationId, it MUST be unique and resolved in the scope of the OAS document. Because of the potential for name clashes, the operationRef syntax is preferred for OpenAPI documents with external references.

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#link-object) |

## Constructors

| | |
|---|---|
| [Link](-link.md) | [common]<br>fun [Link](-link.md)(operationRef: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, operationId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, parameters: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), JsonElement&gt;? = null, requestBody: JsonElement? = null, description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, server: [Server](../-server/index.md)? = null) |

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A description of the link. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation. |
| [operationId](operation-id.md) | [common]<br>val [operationId](operation-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>The name of an existing, resolvable OAS operation, as defined with a unique operationId. This field is mutually exclusive of the operationRef field. |
| [operationRef](operation-ref.md) | [common]<br>val [operationRef](operation-ref.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A relative or absolute URI reference to an OAS operation. This field is mutually exclusive of the operationId field, and MUST point to an Operation Object. Relative operationRef values MAY be used to locate an existing Operation Object in the OpenAPI definition. See the rules for resolving Relative References. |
| [parameters](parameters.md) | [common]<br>val [parameters](parameters.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), JsonElement&gt;? = null<br>A map representing parameters to pass to an operation as specified with operationId or identified via operationRef. The key is the parameter name to be used, whereas the value can be a constant or an expression to be evaluated and passed to the linked operation. The parameter name can be qualified using the parameter location {in}.{name} for operations that use the same parameter name in different locations (e.g. path.id). |
| [requestBody](request-body.md) | [common]<br>val [requestBody](request-body.md): JsonElement? = null<br>A literal value or {expression} to use as a request body when calling the target operation. |
| [server](server.md) | [common]<br>val [server](server.md): [Server](../-server/index.md)? = null<br>A server object to be used by the target operation. |
