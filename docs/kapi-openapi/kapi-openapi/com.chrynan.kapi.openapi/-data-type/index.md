//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[DataType](index.md)

# DataType

[common]\
@Serializable(with = [DataTypeSerializer::class](../../../../kapi-openapi/com.chrynan.kapi.openapi/-data-type-serializer/index.md))

enum [DataType](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[DataType](index.md)&gt; 

Represents the supported data types for the Open API Specification. Some data types require further information to differentiate between other similar types (ex: the integer and number data types). This extra information often comes as a &quot;format&quot; field whose supported values are further defined in the chart below.

Data types in the OAS are based on the types supported by the JSON Schema Specification Draft 2020-12. Note that integer as a type is also supported and is defined as a JSON number without a fraction or exponent part. Models are defined using the Schema Object, which is a superset of JSON Schema Specification Draft 2020-12.

As defined by the JSON Schema Validation vocabulary, data types can have an optional modifier property: format. OAS defines additional formats to provide fine detail for primitive data types.

The formats defined by the OAS are:

| type | format | Comments |
|---|---|---|
| integer | int32 | signed 32 bits |
| integer | int64 | signed 64 bits (a.k.a long) |
| number | float |  |
| number | double |  |
| string | password | A hint to UIs to obscure input. |
| ------- | --------- | -------------------------------- |

#### See also

common

| | |
|---|---|
|  | [JSON Schema Specification](https://datatracker.ietf.org/doc/html/draft-bhutton-json-schema-00#section-4.2.1) |

## Entries

| | |
|---|---|
| [NULL](-n-u-l-l/index.md) | [common]<br>[NULL](-n-u-l-l/index.md) |
| [BOOLEAN](-b-o-o-l-e-a-n/index.md) | [common]<br>[BOOLEAN](-b-o-o-l-e-a-n/index.md) |
| [NUMBER](-n-u-m-b-e-r/index.md) | [common]<br>[NUMBER](-n-u-m-b-e-r/index.md) |
| [INTEGER](-i-n-t-e-g-e-r/index.md) | [common]<br>[INTEGER](-i-n-t-e-g-e-r/index.md) |
| [STRING](-s-t-r-i-n-g/index.md) | [common]<br>[STRING](-s-t-r-i-n-g/index.md) |
| [OBJECT](-o-b-j-e-c-t/index.md) | [common]<br>[OBJECT](-o-b-j-e-c-t/index.md) |
| [ARRAY](-a-r-r-a-y/index.md) | [common]<br>[ARRAY](-a-r-r-a-y/index.md) |
| [UNKNOWN](-u-n-k-n-o-w-n/index.md) | [common]<br>[UNKNOWN](-u-n-k-n-o-w-n/index.md) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [DataType](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[DataType](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../-parameter/-in-value/-c-o-o-k-i-e/index.md#-372974862%2FProperties%2F-245161012) | [common]<br>val [name](../-parameter/-in-value/-c-o-o-k-i-e/index.md#-372974862%2FProperties%2F-245161012): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../-parameter/-in-value/-c-o-o-k-i-e/index.md#-739389684%2FProperties%2F-245161012) | [common]<br>val [ordinal](../-parameter/-in-value/-c-o-o-k-i-e/index.md#-739389684%2FProperties%2F-245161012): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [serialName](serial-name.md) | [common]<br>val [serialName](serial-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
