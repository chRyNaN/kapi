//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[SupportedTypeParameter](index.md)

# SupportedTypeParameter

[jvm]\
@Serializable

data class [SupportedTypeParameter](index.md)(val declaration: [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md), val isDeprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : [ApiParameter](../-api-parameter/index.md)

Represents a function parameter that is not annotated with one of the supported annotations, but is a type that is supported and can be provided by the processor (ex: The ApplicationCall Ktor type).

## Constructors

| | |
|---|---|
| [SupportedTypeParameter](-supported-type-parameter.md) | [jvm]<br>fun [SupportedTypeParameter](-supported-type-parameter.md)(declaration: [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md), isDeprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |

## Properties

| Name | Summary |
|---|---|
| [declaration](declaration.md) | [jvm]<br>open override val [declaration](declaration.md): [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md)<br>The [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md) representing the Kotlin type, name, modifiers, whether there is a default value for the parameter, etc. |
| [isDeprecated](is-deprecated.md) | [jvm]<br>open override val [isDeprecated](is-deprecated.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>If this parameter value has been deprecated. |
| [value](value.md) | [jvm]<br>open override val [value](value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>An optional value that is provided by most, but not all, of the supported API annotations (@Path, |
