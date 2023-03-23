//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[ApiServer](index.md)

# ApiServer

[jvm]\
@Serializable

data class [ApiServer](index.md)(val url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val variables: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiServer.Variable](-variable/index.md)&gt; = emptyList())

Represents server information that implements an API. This directly corresponds to the @Server annotation provided to the @Api annotation.

## Constructors

| | |
|---|---|
| [ApiServer](-api-server.md) | [jvm]<br>fun [ApiServer](-api-server.md)(url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, variables: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiServer.Variable](-variable/index.md)&gt; = emptyList()) |

## Types

| Name | Summary |
|---|---|
| [Variable](-variable/index.md) | [jvm]<br>@Serializable<br>data class [Variable](-variable/index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val defaultValue: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val allowableValues: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList()) |

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [jvm]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [url](url.md) | [jvm]<br>val [url](url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [variables](variables.md) | [jvm]<br>val [variables](variables.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiServer.Variable](-variable/index.md)&gt; |
