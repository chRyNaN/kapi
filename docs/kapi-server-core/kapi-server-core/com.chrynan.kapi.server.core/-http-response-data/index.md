//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core](../index.md)/[HttpResponseData](index.md)

# HttpResponseData

[common]\
sealed class [HttpResponseData](index.md)&lt;[T](index.md)&gt;

A wrapper around the data associated with an HTTP response.

## Types

| Name | Summary |
|---|---|
| [Error](-error/index.md) | [common]<br>data class [Error](-error/index.md)&lt;[T](-error/index.md), [E](-error/index.md)&gt; : [HttpResponseData](index.md)&lt;[T](-error/index.md)&gt; <br>The data associated with an error HTTP response. |
| [Success](-success/index.md) | [common]<br>data class [Success](-success/index.md)&lt;[T](-success/index.md)&gt; : [HttpResponseData](index.md)&lt;[T](-success/index.md)&gt; <br>The data associated with a successfully HTTP response. |

## Properties

| Name | Summary |
|---|---|
| [message](message.md) | [common]<br>abstract val [message](message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [statusCode](status-code.md) | [common]<br>abstract val [statusCode](status-code.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

## Inheritors

| Name |
|---|
| [Success](-success/index.md) |
| [Error](-error/index.md) |
