//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[KotlinTypeParameter](index.md)

# KotlinTypeParameter

[jvm]\
@Serializable

data class [KotlinTypeParameter](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val variance: [KotlinGenericVariance](../-kotlin-generic-variance/index.md), val isReified: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val bounds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinTypeUsage](../-kotlin-type-usage/index.md)&gt; = emptyList())

Represents a Kotlin type parameter. Note that this represents a type parameter and not a type argument. A type parameter is a generic type parameter definition specified on a component like a function or class, whereas a type argument is a generic type argument usage provided at the call site. A [KotlinTypeParameter](index.md) is a variable used at the definition of a component, whereas the a [KotlinTypeArgument](../-kotlin-type-argument/index.md) is a value provided to a generic component.

#### See also

jvm

| | |
|---|---|
| [KotlinTypeArgument](../-kotlin-type-argument/index.md) | for a type argument instead of a type parameter. |

## Constructors

| | |
|---|---|
| [KotlinTypeParameter](-kotlin-type-parameter.md) | [jvm]<br>fun [KotlinTypeParameter](-kotlin-type-parameter.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), variance: [KotlinGenericVariance](../-kotlin-generic-variance/index.md), isReified: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, bounds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinTypeUsage](../-kotlin-type-usage/index.md)&gt; = emptyList()) |

## Properties

| Name | Summary |
|---|---|
| [bounds](bounds.md) | [jvm]<br>val [bounds](bounds.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinTypeUsage](../-kotlin-type-usage/index.md)&gt;<br>The upper bounds of this type parameter. |
| [isReified](is-reified.md) | [jvm]<br>val [isReified](is-reified.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Whether this type parameter contains the reified modifier. |
| [name](name.md) | [jvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The name of this type parameter. |
| [variance](variance.md) | [jvm]<br>val [variance](variance.md): [KotlinGenericVariance](../-kotlin-generic-variance/index.md)<br>The [KotlinGenericVariance](../-kotlin-generic-variance/index.md) of this type parameter. |
