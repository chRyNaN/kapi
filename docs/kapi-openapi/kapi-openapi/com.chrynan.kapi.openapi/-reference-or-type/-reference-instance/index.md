//[kapi-openapi](../../../../index.md)/[com.chrynan.kapi.openapi](../../index.md)/[ReferenceOrType](../index.md)/[ReferenceInstance](index.md)

# ReferenceInstance

[common]\
@Serializable

data class [ReferenceInstance](index.md)&lt;[T](index.md)&gt; : [ReferenceOrType](../index.md)&lt;[T](index.md)&gt;

## Properties

| Name | Summary |
|---|---|
| [value](value.md) | [common]<br>open override val [value](value.md): [Reference](../../-reference/index.md) |

## Extensions

| Name | Summary |
|---|---|
| [isReference](../../is-reference.md) | [common]<br>fun [ReferenceOrType](../index.md)&lt;*&gt;.[isReference](../../is-reference.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this [ReferenceOrType](../index.md) is a [ReferenceOrType.ReferenceInstance](index.md). |
| [isType](../../is-type.md) | [common]<br>fun &lt;[T](../../is-type.md)&gt; [ReferenceOrType](../index.md)&lt;[T](../../is-type.md)&gt;.[isType](../../is-type.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this [ReferenceOrType](../index.md) is a [ReferenceOrType.TypeInstance](../-type-instance/index.md). |
| [referenceValue](../../reference-value.md) | [common]<br>fun [ReferenceOrType](../index.md)&lt;*&gt;.[referenceValue](../../reference-value.md)(): [Reference](../../-reference/index.md)<br>Obtains the reference value if this [ReferenceOrType](../index.md) is a [ReferenceOrType.ReferenceInstance](index.md), otherwise throws an exception. |
| [referenceValueOrNull](../../reference-value-or-null.md) | [common]<br>fun [ReferenceOrType](../index.md)&lt;*&gt;.[referenceValueOrNull](../../reference-value-or-null.md)(): [Reference](../../-reference/index.md)?<br>Obtains the reference value if this [ReferenceOrType](../index.md) is a [ReferenceOrType.ReferenceInstance](index.md), otherwise returns `null`. |
| [typeValue](../../type-value.md) | [common]<br>fun &lt;[T](../../type-value.md)&gt; [ReferenceOrType](../index.md)&lt;[T](../../type-value.md)&gt;.[typeValue](../../type-value.md)(): [T](../../type-value.md)<br>Obtains the type value if this [ReferenceOrType](../index.md) is a [ReferenceOrType.TypeInstance](../-type-instance/index.md), otherwise throws an exception. |
| [typeValueOrNull](../../type-value-or-null.md) | [common]<br>fun &lt;[T](../../type-value-or-null.md)&gt; [ReferenceOrType](../index.md)&lt;[T](../../type-value-or-null.md)&gt;.[typeValueOrNull](../../type-value-or-null.md)(): [T](../../type-value-or-null.md)?<br>Obtains the type value if this [ReferenceOrType](../index.md) is a [ReferenceOrType.TypeInstance](../-type-instance/index.md), otherwise returns `null`. |
