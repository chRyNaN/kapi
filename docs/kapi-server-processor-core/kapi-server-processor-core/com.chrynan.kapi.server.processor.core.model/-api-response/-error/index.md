//[kapi-server-processor-core](../../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../../index.md)/[ApiResponse](../index.md)/[Error](index.md)

# Error

[jvm]\
@Serializable

data class [Error](index.md)(val statusCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val headers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiResponseHeader](../../-api-response-header/index.md)&gt; = emptyList(), val exception: [KotlinTypeUsage](../../-kotlin-type-usage/index.md), val value: [ApiError](../../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-api-error/index.md)) : [ApiResponse](../index.md)

## Constructors

| | |
|---|---|
| [Error](-error.md) | [jvm]<br>fun [Error](-error.md)(statusCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), headers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiResponseHeader](../../-api-response-header/index.md)&gt; = emptyList(), exception: [KotlinTypeUsage](../../-kotlin-type-usage/index.md), value: [ApiError](../../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-api-error/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [jvm]<br>open override val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [exception](exception.md) | [jvm]<br>val [exception](exception.md): [KotlinTypeUsage](../../-kotlin-type-usage/index.md) |
| [headers](headers.md) | [jvm]<br>open override val [headers](headers.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiResponseHeader](../../-api-response-header/index.md)&gt; |
| [statusCode](status-code.md) | [jvm]<br>open override val [statusCode](status-code.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [value](value.md) | [jvm]<br>val [value](value.md): [ApiError](../../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-api-error/index.md) |
