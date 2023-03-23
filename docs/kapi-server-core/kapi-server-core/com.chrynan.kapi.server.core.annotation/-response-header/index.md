//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[ResponseHeader](index.md)

# ResponseHeader

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [])

annotation class [ResponseHeader](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val safeOnly: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, val onlyIfAbsent: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

Represents a single HTTP header that can be appended to the response of an API function's HTTP response.

## Properties

| Name | Summary |
|---|---|
| [name](name.md) | [common]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The name of the header. This value must not be blank. |
| [onlyIfAbsent](only-if-absent.md) | [common]<br>val [onlyIfAbsent](only-if-absent.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>If this is `true`, this header is only appended to the response if there is no header with this [name](name.md) already present; `false` by default. |
| [safeOnly](safe-only.md) | [common]<br>val [safeOnly](safe-only.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true<br>Prevents from setting unsafe headers; `true` by default. |
| [value](value.md) | [common]<br>val [value](value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The value of the header. This value must not be blank. |
