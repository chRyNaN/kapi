//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[PathItem](index.md)

# PathItem

[common]\
@Serializable

data class [PathItem](index.md)(val ref: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val get: [Operation](../-operation/index.md)? = null, val put: [Operation](../-operation/index.md)? = null, val post: [Operation](../-operation/index.md)? = null, val delete: [Operation](../-operation/index.md)? = null, val options: [Operation](../-operation/index.md)? = null, val head: [Operation](../-operation/index.md)? = null, val patch: [Operation](../-operation/index.md)? = null, val trace: [Operation](../-operation/index.md)? = null, val servers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Server](../-server/index.md)&gt;? = null, val parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ReferenceOrType](../-reference-or-type/index.md)&lt;[Parameter](../-parameter/index.md)&gt;&gt;? = null)

Describes the operations available on a single path. A Path Item MAY be empty, due to ACL constraints. The path itself is still exposed to the documentation viewer but they will not know which operations and parameters are available.

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#path-item-object) |

## Constructors

| | |
|---|---|
| [PathItem](-path-item.md) | [common]<br>fun [PathItem](-path-item.md)(ref: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, get: [Operation](../-operation/index.md)? = null, put: [Operation](../-operation/index.md)? = null, post: [Operation](../-operation/index.md)? = null, delete: [Operation](../-operation/index.md)? = null, options: [Operation](../-operation/index.md)? = null, head: [Operation](../-operation/index.md)? = null, patch: [Operation](../-operation/index.md)? = null, trace: [Operation](../-operation/index.md)? = null, servers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Server](../-server/index.md)&gt;? = null, parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ReferenceOrType](../-reference-or-type/index.md)&lt;[Parameter](../-parameter/index.md)&gt;&gt;? = null) |

## Properties

| Name | Summary |
|---|---|
| [delete](delete.md) | [common]<br>val [delete](delete.md): [Operation](../-operation/index.md)? = null<br>A definition of a DELETE operation on this path. |
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>An optional, string description, intended to apply to all operations in this path. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation. |
| [get](get.md) | [common]<br>val [get](get.md): [Operation](../-operation/index.md)? = null<br>A definition of a GET operation on this path. |
| [head](head.md) | [common]<br>val [head](head.md): [Operation](../-operation/index.md)? = null<br>A definition of a HEAD operation on this path. |
| [options](options.md) | [common]<br>val [options](options.md): [Operation](../-operation/index.md)? = null<br>A definition of a OPTIONS operation on this path. |
| [parameters](parameters.md) | [common]<br>val [parameters](parameters.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ReferenceOrType](../-reference-or-type/index.md)&lt;[Parameter](../-parameter/index.md)&gt;&gt;? = null<br>A list of parameters that are applicable for all the operations described under this path. These parameters can be overridden at the operation level, but cannot be removed there. The list MUST NOT include duplicated parameters. A unique parameter is defined by a combination of a name and location. The list can use the Reference Object to link to parameters that are defined at the OpenAPI Object’s components/parameters. |
| [patch](patch.md) | [common]<br>val [patch](patch.md): [Operation](../-operation/index.md)? = null<br>A definition of a PATCH operation on this path. |
| [post](post.md) | [common]<br>val [post](post.md): [Operation](../-operation/index.md)? = null<br>A definition of a POST operation on this path. |
| [put](put.md) | [common]<br>val [put](put.md): [Operation](../-operation/index.md)? = null<br>A definition of a PUT operation on this path. |
| [ref](ref.md) | [common]<br>val [ref](ref.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>Allows for a referenced definition of this path item. The referenced structure MUST be in the form of a Path Item Object. In case a Path Item Object field appears both in the defined object and the referenced object, the behavior is undefined. See the rules for resolving Relative References. |
| [servers](servers.md) | [common]<br>val [servers](servers.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Server](../-server/index.md)&gt;? = null<br>An alternative server array to service all operations in this path. |
| [summary](summary.md) | [common]<br>val [summary](summary.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>An optional, string summary, intended to apply to all operations in this path. |
| [trace](trace.md) | [common]<br>val [trace](trace.md): [Operation](../-operation/index.md)? = null<br>A definition of a TRACE operation on this path. |
