//[kapi-server-core](../../../../index.md)/[com.chrynan.kapi.server.core](../../index.md)/[HttpResponseData](../index.md)/[Error](index.md)

# Error

[common]\
data class [Error](index.md)&lt;[T](index.md), [E](index.md)&gt; : [HttpResponseData](../index.md)&lt;[T](index.md)&gt; 

The data associated with an error HTTP response.

## Properties

| Name | Summary |
|---|---|
| [error](error.md) | [common]<br>val [error](error.md): [E](index.md)? = null |
| [message](message.md) | [common]<br>open override val [message](message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [statusCode](status-code.md) | [common]<br>open override val [statusCode](status-code.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
