//[kapi-server-processor-core](../../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../../index.md)/[KotlinAnnotationUsage](../index.md)/[Argument](index.md)

# Argument

[jvm]\
@Serializable

data class [Argument](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val isSpread: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val type: [KotlinTypeUsage](../../-kotlin-type-usage/index.md)? = null, val value: [KotlinAnnotationUsage.Argument.Value](-value/index.md)? = null)

Represents an argument on a [KotlinAnnotationUsage](../index.md) use-site.

## Constructors

| | |
|---|---|
| [Argument](-argument.md) | [jvm]<br>fun [Argument](-argument.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, isSpread: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, type: [KotlinTypeUsage](../../-kotlin-type-usage/index.md)? = null, value: [KotlinAnnotationUsage.Argument.Value](-value/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [AnnotationValue](-annotation-value/index.md) | [jvm]<br>@Serializable<br>data class [AnnotationValue](-annotation-value/index.md)(val value: [KotlinAnnotationUsage](../index.md)? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [ArrayValue](-array-value/index.md) | [jvm]<br>@Serializable<br>data class [ArrayValue](-array-value/index.md)(val value: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage.Argument.Value](-value/index.md)&gt;? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [BooleanValue](-boolean-value/index.md) | [jvm]<br>@Serializable<br>data class [BooleanValue](-boolean-value/index.md)(val value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [ByteValue](-byte-value/index.md) | [jvm]<br>@Serializable<br>data class [ByteValue](-byte-value/index.md)(val value: [Byte](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [CharValue](-char-value/index.md) | [jvm]<br>@Serializable<br>data class [CharValue](-char-value/index.md)(val value: [Char](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char/index.html)? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [ClassValue](-class-value/index.md) | [jvm]<br>@Serializable<br>data class [ClassValue](-class-value/index.md)(val value: [KotlinName](../../-kotlin-name/index.md)? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [ComplexValue](-complex-value/index.md) | [jvm]<br>@Serializable<br>data class [ComplexValue](-complex-value/index.md)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [DoubleValue](-double-value/index.md) | [jvm]<br>@Serializable<br>data class [DoubleValue](-double-value/index.md)(val value: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [FloatValue](-float-value/index.md) | [jvm]<br>@Serializable<br>data class [FloatValue](-float-value/index.md)(val value: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [IntValue](-int-value/index.md) | [jvm]<br>@Serializable<br>data class [IntValue](-int-value/index.md)(val value: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [LongValue](-long-value/index.md) | [jvm]<br>@Serializable<br>data class [LongValue](-long-value/index.md)(val value: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [ShortValue](-short-value/index.md) | [jvm]<br>@Serializable<br>data class [ShortValue](-short-value/index.md)(val value: [Short](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html)? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [StringValue](-string-value/index.md) | [jvm]<br>@Serializable<br>data class [StringValue](-string-value/index.md)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) : [KotlinAnnotationUsage.Argument.Value](-value/index.md) |
| [Value](-value/index.md) | [jvm]<br>@Serializable<br>sealed class [Value](-value/index.md)<br>The value for a [KotlinAnnotationUsage.Argument](index.md). |

## Properties

| Name | Summary |
|---|---|
| [isSpread](is-spread.md) | [jvm]<br>val [isSpread](is-spread.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Whether this argument is a spread argument (i.e., has a &quot;*&quot; in front of the argument). |
| [name](name.md) | [jvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>The property name of this named argument, or `null` otherwise. |
| [type](type.md) | [jvm]<br>val [type](type.md): [KotlinTypeUsage](../../-kotlin-type-usage/index.md)? = null<br>The [KotlinTypeUsage](../../-kotlin-type-usage/index.md) representing this argument type. |
| [value](value.md) | [jvm]<br>val [value](value.md): [KotlinAnnotationUsage.Argument.Value](-value/index.md)? = null |
