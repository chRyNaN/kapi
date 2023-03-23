//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[ApiFunction](index.md)

# ApiFunction

[jvm]\
@Serializable

data class [ApiFunction](index.md)(val kotlinFunction: [KotlinFunctionDeclaration](../-kotlin-function-declaration/index.md), val method: [HttpMethod](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-http-method/index.md), val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val requestContentType: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val successResponse: [ApiResponse.Success](../-api-response/-success/index.md)? = null, val errorResponses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiResponse.Error](../-api-response/-error/index.md)&gt; = emptyList(), val parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiParameter](../-api-parameter/index.md)&gt; = emptyList(), val extensionReceiver: [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null, val isDeprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiTag](../-api-tag/index.md)&gt; = emptyList())

## Constructors

| | |
|---|---|
| [ApiFunction](-api-function.md) | [jvm]<br>fun [ApiFunction](-api-function.md)(kotlinFunction: [KotlinFunctionDeclaration](../-kotlin-function-declaration/index.md), method: [HttpMethod](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-http-method/index.md), path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), requestContentType: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, successResponse: [ApiResponse.Success](../-api-response/-success/index.md)? = null, errorResponses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiResponse.Error](../-api-response/-error/index.md)&gt; = emptyList(), parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiParameter](../-api-parameter/index.md)&gt; = emptyList(), extensionReceiver: [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null, isDeprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiTag](../-api-tag/index.md)&gt; = emptyList()) |

## Properties

| Name | Summary |
|---|---|
| [errorResponses](error-responses.md) | [jvm]<br>val [errorResponses](error-responses.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiResponse.Error](../-api-response/-error/index.md)&gt; |
| [extensionReceiver](extension-receiver.md) | [jvm]<br>val [extensionReceiver](extension-receiver.md): [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null |
| [isDeprecated](is-deprecated.md) | [jvm]<br>val [isDeprecated](is-deprecated.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [kotlinFunction](kotlin-function.md) | [jvm]<br>val [kotlinFunction](kotlin-function.md): [KotlinFunctionDeclaration](../-kotlin-function-declaration/index.md) |
| [method](method.md) | [jvm]<br>val [method](method.md): [HttpMethod](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-http-method/index.md) |
| [parameters](parameters.md) | [jvm]<br>val [parameters](parameters.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiParameter](../-api-parameter/index.md)&gt; |
| [path](path.md) | [jvm]<br>val [path](path.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [requestContentType](request-content-type.md) | [jvm]<br>val [requestContentType](request-content-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [successResponse](success-response.md) | [jvm]<br>val [successResponse](success-response.md): [ApiResponse.Success](../-api-response/-success/index.md)? = null |
| [tags](tags.md) | [jvm]<br>val [tags](tags.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiTag](../-api-tag/index.md)&gt; |

## Extensions

| Name | Summary |
|---|---|
| [bodyParameter](../body-parameter.md) | [jvm]<br>fun [ApiFunction](index.md).[bodyParameter](../body-parameter.md)(): [BodyParameter](../-body-parameter/index.md) |
| [bodyParameterOrNull](../body-parameter-or-null.md) | [jvm]<br>fun [ApiFunction](index.md).[bodyParameterOrNull](../body-parameter-or-null.md)(): [BodyParameter](../-body-parameter/index.md)? |
| [fieldParameters](../field-parameters.md) | [jvm]<br>val [ApiFunction](index.md).[fieldParameters](../field-parameters.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[FieldParameter](../-field-parameter/index.md)&gt; |
| [headerParameters](../header-parameters.md) | [jvm]<br>val [ApiFunction](index.md).[headerParameters](../header-parameters.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[HeaderParameter](../-header-parameter/index.md)&gt; |
| [partParameters](../part-parameters.md) | [jvm]<br>val [ApiFunction](index.md).[partParameters](../part-parameters.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PartParameter](../-part-parameter/index.md)&gt; |
| [pathParameters](../path-parameters.md) | [jvm]<br>val [ApiFunction](index.md).[pathParameters](../path-parameters.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PathParameter](../-path-parameter/index.md)&gt; |
| [queryParameters](../query-parameters.md) | [jvm]<br>val [ApiFunction](index.md).[queryParameters](../query-parameters.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[QueryParameter](../-query-parameter/index.md)&gt; |
