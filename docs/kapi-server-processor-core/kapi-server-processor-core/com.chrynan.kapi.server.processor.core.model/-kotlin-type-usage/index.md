//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[KotlinTypeUsage](index.md)

# KotlinTypeUsage

[jvm]\
@Serializable

data class [KotlinTypeUsage](index.md)(val name: [KotlinName](../-kotlin-name/index.md), val annotations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; = emptyList(), val typeArguments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinTypeArgument](../-kotlin-type-argument/index.md)&gt; = emptyList(), val isNullable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : [KotlinTyped](../-kotlin-typed/index.md)

Represents the usage (not declaration or definition) of a Kotlin type. For instance, a return type for a function can be represented as a [KotlinTypeUsage](index.md). This can be considered as the usage of an already defined (a.k.a. declared) type.

#### See also

jvm

| | |
|---|---|
| [KotlinTypeDeclaration](../-kotlin-type-declaration/index.md) | for a declaration of a Kotlin type. |

## Constructors

| | |
|---|---|
| [KotlinTypeUsage](-kotlin-type-usage.md) | [jvm]<br>fun [KotlinTypeUsage](-kotlin-type-usage.md)(name: [KotlinName](../-kotlin-name/index.md), annotations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; = emptyList(), typeArguments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinTypeArgument](../-kotlin-type-argument/index.md)&gt; = emptyList(), isNullable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |

## Properties

| Name | Summary |
|---|---|
| [annotations](annotations.md) | [jvm]<br>open override val [annotations](annotations.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt;<br>Any annotations applied to this [KotlinTypeUsage](index.md). |
| [isNullable](is-nullable.md) | [jvm]<br>val [isNullable](is-nullable.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Whether the type is nullable, meaning there is a trailing question mark at its use-site. |
| [name](name.md) | [jvm]<br>open override val [name](name.md): [KotlinName](../-kotlin-name/index.md)<br>The [KotlinName](../-kotlin-name/index.md) of the type. |
| [typeArguments](type-arguments.md) | [jvm]<br>val [typeArguments](type-arguments.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinTypeArgument](../-kotlin-type-argument/index.md)&gt;<br>The generic type arguments provided to this type at its use-site. |

## Extensions

| Name | Summary |
|---|---|
| [isApplicationCall](../is-application-call.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isApplicationCall](../is-application-call.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Ktor ApplicationCall. |
| [isArray](../is-array.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isArray](../is-array.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Array. |
| [isBoolean](../is-boolean.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isBoolean](../is-boolean.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Boolean. |
| [isBooleanArray](../is-boolean-array.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isBooleanArray](../is-boolean-array.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin [BooleanArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean-array/index.html). |
| [isByte](../is-byte.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isByte](../is-byte.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Byte. |
| [isByteArray](../is-byte-array.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isByteArray](../is-byte-array.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin ByteArray. |
| [isByteReadChannel](../is-byte-read-channel.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isByteReadChannel](../is-byte-read-channel.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Ktor ByteReadChannel. |
| [isChar](../is-char.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isChar](../is-char.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Char. |
| [isCharArray](../is-char-array.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isCharArray](../is-char-array.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin [CharArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-array/index.html). |
| [isCharSequence](../is-char-sequence.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isCharSequence](../is-char-sequence.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin CharSequence. |
| [isCollection](../is-collection.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isCollection](../is-collection.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Collection. |
| [isConvertibleByDefaultConversionService](../is-convertible-by-default-conversion-service.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isConvertibleByDefaultConversionService](../is-convertible-by-default-conversion-service.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type can be converted from path and query parameters of an HTTP request using the Ktor io.ktor.util.converters.DefaultConversionService. |
| [isDouble](../is-double.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isDouble](../is-double.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Double. |
| [isDoubleArray](../is-double-array.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isDoubleArray](../is-double-array.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin [DoubleArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/index.html). |
| [isFloat](../is-float.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isFloat](../is-float.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Float. |
| [isFloatArray](../is-float-array.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isFloatArray](../is-float-array.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin [FloatArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float-array/index.html). |
| [isHttpResponse](../is-http-response.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isHttpResponse](../is-http-response.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Ktor HttpResponse. |
| [isInput](../is-input.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isInput](../is-input.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Ktor Input. |
| [isInputStream](../is-input-stream.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isInputStream](../is-input-stream.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a java.io.InputStream. |
| [isInt](../is-int.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isInt](../is-int.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Int. |
| [isIntArray](../is-int-array.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isIntArray](../is-int-array.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin [IntArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html). |
| [isList](../is-list.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isList](../is-list.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin List. |
| [isLong](../is-long.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isLong](../is-long.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Long. |
| [isLongArray](../is-long-array.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isLongArray](../is-long-array.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin [LongArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long-array/index.html). |
| [isMultiPartData](../is-multi-part-data.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isMultiPartData](../is-multi-part-data.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Ktor MultiPartData. |
| [isNothing](../is-nothing.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isNothing](../is-nothing.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Nothing. |
| [isParameters](../is-parameters.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isParameters](../is-parameters.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Ktor Parameters. |
| [isPartData](../is-part-data.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isPartData](../is-part-data.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Ktor PartData. |
| [isResponse](../is-response.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isResponse](../is-response.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kapi Response. |
| [isRoute](../is-route.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isRoute](../is-route.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Ktor Route. |
| [isSet](../is-set.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isSet](../is-set.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Set. |
| [isShort](../is-short.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isShort](../is-short.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Short. |
| [isShortArray](../is-short-array.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isShortArray](../is-short-array.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin [ShortArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short-array/index.html). |
| [isString](../is-string.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isString](../is-string.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin String. |
| [isSupportedArrayType](../is-supported-array-type.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isSupportedArrayType](../is-supported-array-type.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this [KotlinTyped](../-kotlin-typed/index.md) instance is a Kotlin [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html) type or an immediate descendant of [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html). |
| [isSupportedCollectionType](../is-supported-collection-type.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isSupportedCollectionType](../is-supported-collection-type.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this [KotlinTyped](../-kotlin-typed/index.md) instance is a Kotlin [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) type or an immediate descendant of [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html), or [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html) if that information is available. |
| [isUByte](../is-u-byte.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isUByte](../is-u-byte.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin UByte. |
| [isUInt](../is-u-int.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isUInt](../is-u-int.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin UInt. |
| [isULong](../is-u-long.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isULong](../is-u-long.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin ULong. |
| [isUnit](../is-unit.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isUnit](../is-unit.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin Unit. |
| [isUShort](../is-u-short.md) | [jvm]<br>val [KotlinTyped](../-kotlin-typed/index.md).[isUShort](../is-u-short.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines if this type is a Kotlin UShort. |
