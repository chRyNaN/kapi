//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Parameter](index.md)

# Parameter

[common]\
@Serializable

data class [Parameter](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val inValue: [Parameter.InValue](-in-value/index.md), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val required: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val deprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val allowEmptyValue: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val style: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val explode: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val allowReserved: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val schema: [Schema](../-schema/index.md)? = null, val example: JsonElement? = null, val examples: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), JsonElement&gt;? = null, val content: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [MediaType](../-media-type/index.md)&gt;? = null)

Describes a single operation parameter.

A unique parameter is defined by a combination of a name and location.

##  Parameter Locations

There are four possible parameter locations specified by the in field:

- 
   path - Used together with [Path Templating](https://spec.openapis.org/oas/v3.1.0#pathTemplating), where the parameter value is actually part of the operation’s URL. This does not include the host or base path of the API. For example, in /items/{itemId}, the path parameter is itemId.
- 
   query - Parameters that are appended to the URL. For example, in /items?id=###, the query parameter is id.
- 
   header - Custom headers that are expected as part of the request. Note that [RFC7230](https://spec.openapis.org/oas/v3.1.0#bib-RFC7230) states header names are case-insensitive.
- 
   cookie - Used to pass a specific cookie value to the API.

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#parameter-object) |

## Constructors

| | |
|---|---|
| [Parameter](-parameter.md) | [common]<br>fun [Parameter](-parameter.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), inValue: [Parameter.InValue](-in-value/index.md), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, required: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, deprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, allowEmptyValue: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, style: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, explode: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, allowReserved: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, schema: [Schema](../-schema/index.md)? = null, example: JsonElement? = null, examples: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), JsonElement&gt;? = null, content: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [MediaType](../-media-type/index.md)&gt;? = null) |

## Types

| Name | Summary |
|---|---|
| [InValue](-in-value/index.md) | [common]<br>@Serializable<br>enum [InValue](-in-value/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Parameter.InValue](-in-value/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [allowEmptyValue](allow-empty-value.md) | [common]<br>val [allowEmptyValue](allow-empty-value.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Sets the ability to pass empty-valued parameters. This is valid only for query parameters and allows sending a parameter with an empty value. Default value is false. If style is used, and if behavior is n/a (cannot be serialized), the value of allowEmptyValue SHALL be ignored. Use of this property is NOT RECOMMENDED, as it is likely to be removed in a later revision. |
| [allowReserved](allow-reserved.md) | [common]<br>val [allowReserved](allow-reserved.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Determines whether the parameter value SHOULD allow reserved characters, as defined by [RFC3986](https://spec.openapis.org/oas/v3.1.0#bib-RFC3986) :/?#[]@!$&'()*+,;= to be included without percent-encoding. This property only applies to parameters with an in value of query. The default value is false. |
| [content](content.md) | [common]<br>val [content](content.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [MediaType](../-media-type/index.md)&gt;? = null<br>A map containing the representations for the parameter. The key is the media type and the value describes it. The map MUST only contain one entry. |
| [deprecated](deprecated.md) | [common]<br>val [deprecated](deprecated.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Specifies that a parameter is deprecated and SHOULD be transitioned out of usage. Default value is false. |
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A brief description of the parameter. This could contain examples of use. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation. |
| [example](example.md) | [common]<br>val [example](example.md): JsonElement? = null<br>Example of the parameter’s potential value. The example SHOULD match the specified schema and encoding properties if present. The example field is mutually exclusive of the examples field. Furthermore, if referencing a schema that contains an example, the example value SHALL override the example provided by the schema. To represent examples of media types that cannot naturally be represented in JSON or YAML, a string value can contain the example with escaping where necessary. |
| [examples](examples.md) | [common]<br>val [examples](examples.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), JsonElement&gt;? = null<br>Examples of the parameter’s potential value. Each example SHOULD contain a value in the correct format as specified in the parameter encoding. The examples field is mutually exclusive of the example field. Furthermore, if referencing a schema that contains an example, the examples value SHALL override the example provided by the schema. |
| [explode](explode.md) | [common]<br>val [explode](explode.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>When this is true, parameter values of type array or object generate separate parameters for each value of the array or key-value pair of the map. For other types of parameters this property has no effect. When style is form, the default value is true. For all other styles, the default value is false. |
| [inValue](in-value.md) | [common]<br>val [inValue](in-value.md): [Parameter.InValue](-in-value/index.md)<br>**REQUIRED**. The location of the parameter. Possible values are &quot;query&quot;, &quot;header&quot;, &quot;path&quot; or &quot;cookie&quot;. |
| [name](name.md) | [common]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>**REQUIRED**. The name of the parameter. Parameter names are case-sensitive. |
| [required](required.md) | [common]<br>val [required](required.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Determines whether this parameter is mandatory. If the [parameter location](https://spec.openapis.org/oas/v3.1.0#parameterIn) is &quot;path&quot;, this property is REQUIRED and its value MUST be true. Otherwise, the property MAY be included and its default value is false. |
| [schema](schema.md) | [common]<br>val [schema](schema.md): [Schema](../-schema/index.md)? = null<br>The schema defining the type used for the parameter. |
| [style](style.md) | [common]<br>val [style](style.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>Describes how the parameter value will be serialized depending on the type of the parameter value. Default values (based on value of in): for query - form; for path - simple; for header - simple; for cookie - form. |
