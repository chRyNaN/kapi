//[kapi-server-processor-core](../../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../../index.md)/[ApiResponse](../index.md)/[Success](index.md)

# Success

[jvm]\
@Serializable

data class [Success](index.md)(val statusCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val headers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiResponseHeader](../../-api-response-header/index.md)&gt; = emptyList(), val contentType: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [ApiResponse](../index.md)

## Constructors

| | |
|---|---|
| [Success](-success.md) | [jvm]<br>fun [Success](-success.md)(statusCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), headers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiResponseHeader](../../-api-response-header/index.md)&gt; = emptyList(), contentType: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [contentType](content-type.md) | [jvm]<br>val [contentType](content-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [description](description.md) | [jvm]<br>open override val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [headers](headers.md) | [jvm]<br>open override val [headers](headers.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiResponseHeader](../../-api-response-header/index.md)&gt; |
| [statusCode](status-code.md) | [jvm]<br>open override val [statusCode](status-code.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
