//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[ApiDefinition](index.md)

# ApiDefinition

[jvm]\
@Serializable

data class [ApiDefinition](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val basePath: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val info: [ApiInfo](../-api-info/index.md)? = null, val servers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiServer](../-api-server/index.md)&gt; = emptyList(), val tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiTag](../-api-tag/index.md)&gt; = emptyList(), val type: [KotlinTypeDeclaration](../-kotlin-type-declaration/index.md), val documentation: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val functions: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiFunction](../-api-function/index.md)&gt; = emptyList(), val annotations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; = emptyList())

## Constructors

| | |
|---|---|
| [ApiDefinition](-api-definition.md) | [jvm]<br>fun [ApiDefinition](-api-definition.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), basePath: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, info: [ApiInfo](../-api-info/index.md)? = null, servers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiServer](../-api-server/index.md)&gt; = emptyList(), tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiTag](../-api-tag/index.md)&gt; = emptyList(), type: [KotlinTypeDeclaration](../-kotlin-type-declaration/index.md), documentation: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, functions: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiFunction](../-api-function/index.md)&gt; = emptyList(), annotations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; = emptyList()) |

## Properties

| Name | Summary |
|---|---|
| [annotations](annotations.md) | [jvm]<br>val [annotations](annotations.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; |
| [basePath](base-path.md) | [jvm]<br>val [basePath](base-path.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [documentation](documentation.md) | [jvm]<br>val [documentation](documentation.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [functions](functions.md) | [jvm]<br>val [functions](functions.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiFunction](../-api-function/index.md)&gt; |
| [info](info.md) | [jvm]<br>val [info](info.md): [ApiInfo](../-api-info/index.md)? = null |
| [name](name.md) | [jvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [servers](servers.md) | [jvm]<br>val [servers](servers.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiServer](../-api-server/index.md)&gt; |
| [tags](tags.md) | [jvm]<br>val [tags](tags.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiTag](../-api-tag/index.md)&gt; |
| [type](type.md) | [jvm]<br>val [type](type.md): [KotlinTypeDeclaration](../-kotlin-type-declaration/index.md) |
