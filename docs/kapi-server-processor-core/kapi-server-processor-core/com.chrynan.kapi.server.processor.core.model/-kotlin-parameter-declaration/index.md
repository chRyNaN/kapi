//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[KotlinParameterDeclaration](index.md)

# KotlinParameterDeclaration

[jvm]\
@Serializable

data class [KotlinParameterDeclaration](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [KotlinTypeUsage](../-kotlin-type-usage/index.md), val isVararg: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val isNoInline: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val isCrossInline: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val isVal: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val isVar: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val hasDefaultValue: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val annotations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; = emptyList(), val modifiers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinParameterModifier](../-kotlin-parameter-modifier/index.md)&gt; = emptyList())

Represents the declaration (not usage) of a parameter within a Kotlin function or constructor. A [KotlinParameterDeclaration](index.md) is how the parameter is defined (ex: `val param: String = ""`) and not the usage or call-site of passing a value as a parameter to a function or constructor (ex: `param = ""`).

## Constructors

| | |
|---|---|
| [KotlinParameterDeclaration](-kotlin-parameter-declaration.md) | [jvm]<br>fun [KotlinParameterDeclaration](-kotlin-parameter-declaration.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [KotlinTypeUsage](../-kotlin-type-usage/index.md), isVararg: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, isNoInline: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, isCrossInline: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, isVal: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, isVar: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, hasDefaultValue: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, annotations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; = emptyList(), modifiers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinParameterModifier](../-kotlin-parameter-modifier/index.md)&gt; = emptyList()) |

## Properties

| Name | Summary |
|---|---|
| [annotations](annotations.md) | [jvm]<br>val [annotations](annotations.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt;<br>The annotations for this parameter. |
| [hasDefaultValue](has-default-value.md) | [jvm]<br>val [hasDefaultValue](has-default-value.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>If this parameter has a default value. |
| [isCrossInline](is-cross-inline.md) | [jvm]<br>val [isCrossInline](is-cross-inline.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>If this parameter has the `crossinline` modifier. |
| [isNoInline](is-no-inline.md) | [jvm]<br>val [isNoInline](is-no-inline.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>If this parameter has the `noinline` modifier. |
| [isVal](is-val.md) | [jvm]<br>val [isVal](is-val.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>If this parameter has the `val` modifier. |
| [isVar](is-var.md) | [jvm]<br>val [isVar](is-var.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>If this parameter has the `var` modifier. |
| [isVararg](is-vararg.md) | [jvm]<br>val [isVararg](is-vararg.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>If this parameter is a vararg parameter (has the `vararg` modifier). |
| [modifiers](modifiers.md) | [jvm]<br>val [modifiers](modifiers.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinParameterModifier](../-kotlin-parameter-modifier/index.md)&gt; |
| [name](name.md) | [jvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The name of the parameter. |
| [type](type.md) | [jvm]<br>val [type](type.md): [KotlinTypeUsage](../-kotlin-type-usage/index.md)<br>The [KotlinTypeUsage](../-kotlin-type-usage/index.md) representing the Kotlin type of the parameter. |
