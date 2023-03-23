//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[DefaultValueParameter](index.md)

# DefaultValueParameter

[jvm]\
@Serializable

data class [DefaultValueParameter](index.md)(val declaration: [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md), val isDeprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : [ApiParameter](../-api-parameter/index.md)

Represents a function parameter that is not annotated with one of the supported annotations but contains a default value so that it can still be used with an API function.

## Constructors

| | |
|---|---|
| [DefaultValueParameter](-default-value-parameter.md) | [jvm]<br>fun [DefaultValueParameter](-default-value-parameter.md)(declaration: [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md), isDeprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |

## Properties

| Name | Summary |
|---|---|
| [declaration](declaration.md) | [jvm]<br>open override val [declaration](declaration.md): [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md)<br>The [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md) representing the Kotlin type, name, modifiers, whether there is a default value for the parameter, etc. |
| [isDeprecated](is-deprecated.md) | [jvm]<br>open override val [isDeprecated](is-deprecated.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>If this parameter value has been deprecated. |
| [value](value.md) | [jvm]<br>open override val [value](value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>An optional value that is provided by most, but not all, of the supported API annotations (@Path, |
