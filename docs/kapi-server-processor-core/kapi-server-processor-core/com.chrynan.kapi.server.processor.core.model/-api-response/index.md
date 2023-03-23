//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[ApiResponse](index.md)

# ApiResponse

[jvm]\
@Serializable

sealed class [ApiResponse](index.md)

## Types

| Name | Summary |
|---|---|
| [Error](-error/index.md) | [jvm]<br>@Serializable<br>data class [Error](-error/index.md)(val statusCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val headers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiResponseHeader](../-api-response-header/index.md)&gt; = emptyList(), val exception: [KotlinTypeUsage](../-kotlin-type-usage/index.md), val value: [ApiError](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-api-error/index.md)) : [ApiResponse](index.md) |
| [Success](-success/index.md) | [jvm]<br>@Serializable<br>data class [Success](-success/index.md)(val statusCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val headers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiResponseHeader](../-api-response-header/index.md)&gt; = emptyList(), val contentType: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [ApiResponse](index.md) |

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [jvm]<br>abstract val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [headers](headers.md) | [jvm]<br>abstract val [headers](headers.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiResponseHeader](../-api-response-header/index.md)&gt; |
| [statusCode](status-code.md) | [jvm]<br>abstract val [statusCode](status-code.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

## Inheritors

| Name |
|---|
| [Success](-success/index.md) |
| [Error](-error/index.md) |
