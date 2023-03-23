//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[ApiResponseHeader](index.md)

# ApiResponseHeader

[jvm]\
@Serializable

data class [ApiResponseHeader](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val safeOnly: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, val onlyIfAbsent: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

Represents a [ResponseHeader](../../../../kapi-server-core/kapi-server-core/com.chrynan.kapi.server.core.annotation/-response-header/index.md) annotation value.

#### See also

jvm

| | |
|---|---|
| [HeaderParameter](../-header-parameter/index.md) | for a representation of the [Header](../../../../kapi-server-core/kapi-server-core/com.chrynan.kapi.server.core.annotation/-header/index.md) annotation when used on a parameter value of an API function. |

## Constructors

| | |
|---|---|
| [ApiResponseHeader](-api-response-header.md) | [jvm]<br>fun [ApiResponseHeader](-api-response-header.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), safeOnly: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, onlyIfAbsent: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |

## Properties

| Name | Summary |
|---|---|
| [name](name.md) | [jvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The header name. |
| [onlyIfAbsent](only-if-absent.md) | [jvm]<br>val [onlyIfAbsent](only-if-absent.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Only adds the header value if it is not already present. |
| [safeOnly](safe-only.md) | [jvm]<br>val [safeOnly](safe-only.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true<br>Determines whether to only add the header if it is considered safe. Defaults to `true`. |
| [value](value.md) | [jvm]<br>val [value](value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The header value. |
