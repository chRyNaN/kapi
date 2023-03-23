//[kapi-server-core](../../index.md)/[com.chrynan.kapi.server.core.util](index.md)/[getOrFail](get-or-fail.md)

# getOrFail

[common]\
inline fun &lt;[R](get-or-fail.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; Headers.[getOrFail](get-or-fail.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [R](get-or-fail.md)

Get headers value associated with this [name](get-or-fail.md) converting to type [R](get-or-fail.md) using DefaultConversionService or fail with MissingRequestParameterException

#### Throws

| | |
|---|---|
| MissingRequestParameterException | if no values associated with this [name](get-or-fail.md) |
| ParameterConversionException | when conversion from String to [R](get-or-fail.md) fails |
