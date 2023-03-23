//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[PartParameter](index.md)

# PartParameter

[jvm]\
@Serializable

data class [PartParameter](index.md)(val declaration: [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md), val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val isDeprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val encoding: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [ApiParameter](../-api-parameter/index.md)

Represents an [ApiParameter](../-api-parameter/index.md) annotated with the [Part](../../../../kapi-server-core/kapi-server-core/com.chrynan.kapi.server.core.annotation/-part/index.md) annotation.

## Constructors

| | |
|---|---|
| [PartParameter](-part-parameter.md) | [jvm]<br>fun [PartParameter](-part-parameter.md)(declaration: [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md), value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), isDeprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, encoding: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [declaration](declaration.md) | [jvm]<br>open override val [declaration](declaration.md): [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md)<br>The [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md) representing the Kotlin type, name, modifiers, whether there is a default value for the parameter, etc. |
| [encoding](encoding.md) | [jvm]<br>val [encoding](encoding.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Corresponds to the [Part.encoding](../../../../kapi-server-core/kapi-server-core/com.chrynan.kapi.server.core.annotation/-part/encoding.md) property value of the [Part](../../../../kapi-server-core/kapi-server-core/com.chrynan.kapi.server.core.annotation/-part/index.md) annotation on this parameter. |
| [isDeprecated](is-deprecated.md) | [jvm]<br>open override val [isDeprecated](is-deprecated.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>If this parameter value has been deprecated. |
| [value](value.md) | [jvm]<br>open override val [value](value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Corresponds to the [Part.value](../../../../kapi-server-core/kapi-server-core/com.chrynan.kapi.server.core.annotation/-part/value.md) property value of the [Part](../../../../kapi-server-core/kapi-server-core/com.chrynan.kapi.server.core.annotation/-part/index.md) annotation on this parameter. |
