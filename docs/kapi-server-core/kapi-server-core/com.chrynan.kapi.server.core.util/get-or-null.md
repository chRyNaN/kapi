//[kapi-server-core](../../index.md)/[com.chrynan.kapi.server.core.util](index.md)/[getOrNull](get-or-null.md)

# getOrNull

[common]\
inline fun &lt;[R](get-or-null.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; Parameters.[getOrNull](get-or-null.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [R](get-or-null.md)?

Retrieves the parameters value associated with this [name](get-or-null.md) converting to type [R](get-or-null.md) using DefaultConversionService or `null` if the value cannot be converted or there is no value associated with [name](get-or-null.md).

[common]\
inline fun &lt;[R](get-or-null.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; Headers.[getOrNull](get-or-null.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [R](get-or-null.md)?

Retrieves the headers value associated with this [name](get-or-null.md) converting to type [R](get-or-null.md) using DefaultConversionService or `null` if the value cannot be converted or there is no value associated with [name](get-or-null.md).
