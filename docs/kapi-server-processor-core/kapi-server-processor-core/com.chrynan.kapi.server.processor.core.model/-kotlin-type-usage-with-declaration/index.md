//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[KotlinTypeUsageWithDeclaration](index.md)

# KotlinTypeUsageWithDeclaration

[jvm]\
@Serializable

data class [KotlinTypeUsageWithDeclaration](index.md)(val usage: [KotlinTypeUsage](../-kotlin-type-usage/index.md), val declaration: [KotlinTypeDeclaration](../-kotlin-type-declaration/index.md), val declaredGenericTypes: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinTypeDeclaration](../-kotlin-type-declaration/index.md)&gt; = emptyList())

Represents a fully resolved Kotlin type usage. This is different from the [KotlinTypeUsage](../-kotlin-type-usage/index.md) because it contains information about the [KotlinTypeUsage](../-kotlin-type-usage/index.md) and its associated [KotlinTypeDeclaration](../-kotlin-type-declaration/index.md), as well as more resolved type data, such as resolved generic type declarations. This can be useful for scenarios that you need to fully construct a model tree. Note that this class will be slow to obtain as it will require resolving each of the associated types.

## Constructors

| | |
|---|---|
| [KotlinTypeUsageWithDeclaration](-kotlin-type-usage-with-declaration.md) | [jvm]<br>fun [KotlinTypeUsageWithDeclaration](-kotlin-type-usage-with-declaration.md)(usage: [KotlinTypeUsage](../-kotlin-type-usage/index.md), declaration: [KotlinTypeDeclaration](../-kotlin-type-declaration/index.md), declaredGenericTypes: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinTypeDeclaration](../-kotlin-type-declaration/index.md)&gt; = emptyList()) |

## Properties

| Name | Summary |
|---|---|
| [declaration](declaration.md) | [jvm]<br>val [declaration](declaration.md): [KotlinTypeDeclaration](../-kotlin-type-declaration/index.md) |
| [declaredGenericTypes](declared-generic-types.md) | [jvm]<br>val [declaredGenericTypes](declared-generic-types.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinTypeDeclaration](../-kotlin-type-declaration/index.md)&gt; |
| [usage](usage.md) | [jvm]<br>val [usage](usage.md): [KotlinTypeUsage](../-kotlin-type-usage/index.md) |
