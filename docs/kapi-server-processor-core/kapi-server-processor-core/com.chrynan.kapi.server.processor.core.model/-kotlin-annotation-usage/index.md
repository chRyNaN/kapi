//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[KotlinAnnotationUsage](index.md)

# KotlinAnnotationUsage

[jvm]\
@Serializable

data class [KotlinAnnotationUsage](index.md)(val type: [KotlinTypeUsage](../-kotlin-type-usage/index.md), val arguments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage.Argument](-argument/index.md)&gt; = emptyList(), val defaultArguments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage.Argument](-argument/index.md)&gt; = emptyList())

Represents the use of a Kotlin Annotation.

## Constructors

| | |
|---|---|
| [KotlinAnnotationUsage](-kotlin-annotation-usage.md) | [jvm]<br>fun [KotlinAnnotationUsage](-kotlin-annotation-usage.md)(type: [KotlinTypeUsage](../-kotlin-type-usage/index.md), arguments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage.Argument](-argument/index.md)&gt; = emptyList(), defaultArguments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage.Argument](-argument/index.md)&gt; = emptyList()) |

## Types

| Name | Summary |
|---|---|
| [Argument](-argument/index.md) | [jvm]<br>@Serializable<br>data class [Argument](-argument/index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val isSpread: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val type: [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null, val value: [KotlinAnnotationUsage.Argument.Value](-argument/-value/index.md)? = null)<br>Represents an argument on a [KotlinAnnotationUsage](index.md) use-site. |

## Properties

| Name | Summary |
|---|---|
| [arguments](arguments.md) | [jvm]<br>val [arguments](arguments.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage.Argument](-argument/index.md)&gt;<br>The arguments explicitly specified on this annotation. |
| [defaultArguments](default-arguments.md) | [jvm]<br>val [defaultArguments](default-arguments.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage.Argument](-argument/index.md)&gt;<br>The default arguments that are not explicitly specified on the use-site, but are defaults within the annotation class. |
| [type](type.md) | [jvm]<br>val [type](type.md): [KotlinTypeUsage](../-kotlin-type-usage/index.md)<br>The [KotlinTypeUsage](../-kotlin-type-usage/index.md) representing this annotation class. |
