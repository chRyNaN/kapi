//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[ReferenceOrType](index.md)

# ReferenceOrType

[common]\
@Serializable(with = [ReferenceOrTypeSerializer::class](../../../../kapi-openapi/com.chrynan.kapi.openapi/-reference-or-type-serializer/index.md))

sealed class [ReferenceOrType](index.md)&lt;[T](index.md)&gt;

A wrapper class around either a [Reference](../-reference/index.md) or a generic type of [T](index.md). Often in the Open API Specification, a property type can either be a [Reference](../-reference/index.md) object or some other object. This class provides a simple way to serialize and deserialize those types and represent those union types within Kotlin code.

## Types

| Name | Summary |
|---|---|
| [ReferenceInstance](-reference-instance/index.md) | [common]<br>@Serializable<br>data class [ReferenceInstance](-reference-instance/index.md)&lt;[T](-reference-instance/index.md)&gt; : [ReferenceOrType](index.md)&lt;[T](-reference-instance/index.md)&gt; |
| [TypeInstance](-type-instance/index.md) | [common]<br>@Serializable<br>data class [TypeInstance](-type-instance/index.md)&lt;[T](-type-instance/index.md)&gt; : [ReferenceOrType](index.md)&lt;[T](-type-instance/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [value](value.md) | [common]<br>abstract val [value](value.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |

## Inheritors

| Name |
|---|
| [ReferenceInstance](-reference-instance/index.md) |
| [TypeInstance](-type-instance/index.md) |

## Extensions

| Name | Summary |
|---|---|
| [isReference](../is-reference.md) | [common]<br>fun [ReferenceOrType](index.md)&lt;*&gt;.[isReference](../is-reference.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this [ReferenceOrType](index.md) is a [ReferenceOrType.ReferenceInstance](-reference-instance/index.md). |
| [isType](../is-type.md) | [common]<br>fun &lt;[T](../is-type.md)&gt; [ReferenceOrType](index.md)&lt;[T](../is-type.md)&gt;.[isType](../is-type.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this [ReferenceOrType](index.md) is a [ReferenceOrType.TypeInstance](-type-instance/index.md). |
| [referenceValue](../reference-value.md) | [common]<br>fun [ReferenceOrType](index.md)&lt;*&gt;.[referenceValue](../reference-value.md)(): [Reference](../-reference/index.md)<br>Obtains the reference value if this [ReferenceOrType](index.md) is a [ReferenceOrType.ReferenceInstance](-reference-instance/index.md), otherwise throws an exception. |
| [referenceValueOrNull](../reference-value-or-null.md) | [common]<br>fun [ReferenceOrType](index.md)&lt;*&gt;.[referenceValueOrNull](../reference-value-or-null.md)(): [Reference](../-reference/index.md)?<br>Obtains the reference value if this [ReferenceOrType](index.md) is a [ReferenceOrType.ReferenceInstance](-reference-instance/index.md), otherwise returns `null`. |
| [typeValue](../type-value.md) | [common]<br>fun &lt;[T](../type-value.md)&gt; [ReferenceOrType](index.md)&lt;[T](../type-value.md)&gt;.[typeValue](../type-value.md)(): [T](../type-value.md)<br>Obtains the type value if this [ReferenceOrType](index.md) is a [ReferenceOrType.TypeInstance](-type-instance/index.md), otherwise throws an exception. |
| [typeValueOrNull](../type-value-or-null.md) | [common]<br>fun &lt;[T](../type-value-or-null.md)&gt; [ReferenceOrType](index.md)&lt;[T](../type-value-or-null.md)&gt;.[typeValueOrNull](../type-value-or-null.md)(): [T](../type-value-or-null.md)?<br>Obtains the type value if this [ReferenceOrType](index.md) is a [ReferenceOrType.TypeInstance](-type-instance/index.md), otherwise returns `null`. |
