//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[KotlinFunctionDeclaration](index.md)

# KotlinFunctionDeclaration

[jvm]\
@Serializable

data class [KotlinFunctionDeclaration](index.md)(val name: [KotlinName](../-kotlin-name/index.md), val annotations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; = emptyList(), val modifiers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinFunctionModifier](../-kotlin-function-modifier/index.md)&gt; = emptyList(), val kind: [KotlinFunctionDeclaration.Kind](-kind/index.md), val extensionReceiver: [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null, val returnType: [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null, val parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md)&gt; = emptyList(), val isConstructor: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val documentation: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val isSuspending: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

## Constructors

| | |
|---|---|
| [KotlinFunctionDeclaration](-kotlin-function-declaration.md) | [jvm]<br>fun [KotlinFunctionDeclaration](-kotlin-function-declaration.md)(name: [KotlinName](../-kotlin-name/index.md), annotations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; = emptyList(), modifiers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinFunctionModifier](../-kotlin-function-modifier/index.md)&gt; = emptyList(), kind: [KotlinFunctionDeclaration.Kind](-kind/index.md), extensionReceiver: [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null, returnType: [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null, parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md)&gt; = emptyList(), isConstructor: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, documentation: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, isSuspending: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |

## Types

| Name | Summary |
|---|---|
| [Kind](-kind/index.md) | [jvm]<br>@Serializable<br>enum [Kind](-kind/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[KotlinFunctionDeclaration.Kind](-kind/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [annotations](annotations.md) | [jvm]<br>val [annotations](annotations.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; |
| [documentation](documentation.md) | [jvm]<br>val [documentation](documentation.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [extensionReceiver](extension-receiver.md) | [jvm]<br>val [extensionReceiver](extension-receiver.md): [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null |
| [isConstructor](is-constructor.md) | [jvm]<br>val [isConstructor](is-constructor.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [isSuspending](is-suspending.md) | [jvm]<br>val [isSuspending](is-suspending.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [kind](kind.md) | [jvm]<br>val [kind](kind.md): [KotlinFunctionDeclaration.Kind](-kind/index.md) |
| [modifiers](modifiers.md) | [jvm]<br>val [modifiers](modifiers.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinFunctionModifier](../-kotlin-function-modifier/index.md)&gt; |
| [name](name.md) | [jvm]<br>val [name](name.md): [KotlinName](../-kotlin-name/index.md) |
| [parameters](parameters.md) | [jvm]<br>val [parameters](parameters.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md)&gt; |
| [returnType](return-type.md) | [jvm]<br>val [returnType](return-type.md): [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null |
