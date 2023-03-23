//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[ApiParameter](index.md)

# ApiParameter

[jvm]\
@Serializable

sealed class [ApiParameter](index.md)

Represents a parameter to an API function (a function of a type annotated with the `@Api` annotation). This model contains data about the Kotlin parameter declaration and API related values that are extracted from expected annotations.

Note that this is a sealed class and subclasses may contain further information about the parameter.

## Properties

| Name | Summary |
|---|---|
| [declaration](declaration.md) | [jvm]<br>abstract val [declaration](declaration.md): [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md)<br>The [KotlinParameterDeclaration](../-kotlin-parameter-declaration/index.md) representing the Kotlin type, name, modifiers, whether there is a default value for the parameter, etc. |
| [isDeprecated](is-deprecated.md) | [jvm]<br>abstract val [isDeprecated](is-deprecated.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>If this parameter value has been deprecated. |
| [value](value.md) | [jvm]<br>abstract val [value](value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>An optional value that is provided by most, but not all, of the supported API annotations (@Path, |

## Inheritors

| Name |
|---|
| [PathParameter](../-path-parameter/index.md) |
| [QueryParameter](../-query-parameter/index.md) |
| [FieldParameter](../-field-parameter/index.md) |
| [PartParameter](../-part-parameter/index.md) |
| [HeaderParameter](../-header-parameter/index.md) |
| [BodyParameter](../-body-parameter/index.md) |
| [DefaultValueParameter](../-default-value-parameter/index.md) |
| [SupportedTypeParameter](../-supported-type-parameter/index.md) |
