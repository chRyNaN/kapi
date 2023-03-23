//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[KotlinGenericVariance](index.md)

# KotlinGenericVariance

[jvm]\
@Serializable

enum [KotlinGenericVariance](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[KotlinGenericVariance](index.md)&gt; 

Represents both declaration-site and use-site variance. [STAR](-s-t-a-r/index.md) is only used and valid in use-site variance, while others can be used in both.

## Entries

| | |
|---|---|
| [STAR](-s-t-a-r/index.md) | [jvm]<br>[STAR](-s-t-a-r/index.md) |
| [INVARIANT](-i-n-v-a-r-i-a-n-t/index.md) | [jvm]<br>[INVARIANT](-i-n-v-a-r-i-a-n-t/index.md) |
| [COVARIANT](-c-o-v-a-r-i-a-n-t/index.md) | [jvm]<br>[COVARIANT](-c-o-v-a-r-i-a-n-t/index.md) |
| [CONTRAVARIANT](-c-o-n-t-r-a-v-a-r-i-a-n-t/index.md) | [jvm]<br>[CONTRAVARIANT](-c-o-n-t-r-a-v-a-r-i-a-n-t/index.md) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [jvm]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [KotlinGenericVariance](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [jvm]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[KotlinGenericVariance](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [label](label.md) | [jvm]<br>val [label](label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [name](../-kotlin-type-declaration/-kind/-a-n-n-o-t-a-t-i-o-n_-c-l-a-s-s/index.md#-372974862%2FProperties%2F-2055083147) | [jvm]<br>val [name](../-kotlin-type-declaration/-kind/-a-n-n-o-t-a-t-i-o-n_-c-l-a-s-s/index.md#-372974862%2FProperties%2F-2055083147): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../-kotlin-type-declaration/-kind/-a-n-n-o-t-a-t-i-o-n_-c-l-a-s-s/index.md#-739389684%2FProperties%2F-2055083147) | [jvm]<br>val [ordinal](../-kotlin-type-declaration/-kind/-a-n-n-o-t-a-t-i-o-n_-c-l-a-s-s/index.md#-739389684%2FProperties%2F-2055083147): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [serialName](serial-name.md) | [jvm]<br>val [serialName](serial-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
