//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[BodyParameter](index.md)

# BodyParameter

[jvm]\
@Serializable

data class [BodyParameter](index.md)(val declaration: [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md), val kotlinType: [KotlinTypeUsageWithDeclaration](../-kotlin-type-usage-with-declaration/index.md), val isDeprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : [ApiParameter](../-api-parameter/index.md)

Represents an [ApiParameter](../-api-parameter/index.md) annotated with the [Body](../../../../kapi-server-core/kapi-server-core/com.chrynan.kapi.server.core.annotation/-body/index.md) annotation.

## Constructors

| | |
|---|---|
| [BodyParameter](-body-parameter.md) | [jvm]<br>fun [BodyParameter](-body-parameter.md)(declaration: [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md), kotlinType: [KotlinTypeUsageWithDeclaration](../-kotlin-type-usage-with-declaration/index.md), isDeprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |

## Properties

| Name | Summary |
|---|---|
| [declaration](declaration.md) | [jvm]<br>open override val [declaration](declaration.md): [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md)<br>The [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md) representing the Kotlin type, name, modifiers, whether there is a default value for the parameter, etc. |
| [isDeprecated](is-deprecated.md) | [jvm]<br>open override val [isDeprecated](is-deprecated.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>If this parameter value has been deprecated. |
| [kotlinType](kotlin-type.md) | [jvm]<br>val [kotlinType](kotlin-type.md): [KotlinTypeUsageWithDeclaration](../-kotlin-type-usage-with-declaration/index.md) |
| [value](value.md) | [jvm]<br>open override val [value](value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>An optional value that is provided by most, but not all, of the supported API annotations (@Path, |
