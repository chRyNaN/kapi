//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[QueryParameter](index.md)

# QueryParameter

[jvm]\
@Serializable

data class [QueryParameter](index.md)(val declaration: [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md), val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val isDeprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val encoded: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : [ApiParameter](../-api-parameter/index.md)

Represents an [ApiParameter](../-api-parameter/index.md) annotated with the [Query](../../../../kapi-server-core/kapi-server-core/com.chrynan.kapi.server.core.annotation/-query/index.md) annotation.

## Constructors

| | |
|---|---|
| [QueryParameter](-query-parameter.md) | [jvm]<br>fun [QueryParameter](-query-parameter.md)(declaration: [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md), value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), isDeprecated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, encoded: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |

## Properties

| Name | Summary |
|---|---|
| [declaration](declaration.md) | [jvm]<br>open override val [declaration](declaration.md): [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md)<br>The [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md) representing the Kotlin type, name, modifiers, whether there is a default value for the parameter, etc. |
| [encoded](encoded.md) | [jvm]<br>val [encoded](encoded.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Corresponds to the Query.encoded property value of the [Query](../../../../kapi-server-core/kapi-server-core/com.chrynan.kapi.server.core.annotation/-query/index.md) annotation on this parameter. |
| [isDeprecated](is-deprecated.md) | [jvm]<br>open override val [isDeprecated](is-deprecated.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>If this parameter value has been deprecated. |
| [value](value.md) | [jvm]<br>open override val [value](value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Corresponds to the [Query.value](../../../../kapi-server-core/kapi-server-core/com.chrynan.kapi.server.core.annotation/-query/value.md) property value of the [Query](../../../../kapi-server-core/kapi-server-core/com.chrynan.kapi.server.core.annotation/-query/index.md) annotation on this parameter. |
