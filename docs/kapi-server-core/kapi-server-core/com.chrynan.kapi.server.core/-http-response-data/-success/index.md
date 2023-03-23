//[kapi-server-core](../../../../index.md)/[com.chrynan.kapi.server.core](../../index.md)/[HttpResponseData](../index.md)/[Success](index.md)

# Success

[common]\
data class [Success](index.md)&lt;[T](index.md)&gt; : [HttpResponseData](../index.md)&lt;[T](index.md)&gt; 

The data associated with a successfully HTTP response.

## Properties

| Name | Summary |
|---|---|
| [body](body.md) | [common]<br>val [body](body.md): [T](index.md) |
| [message](message.md) | [common]<br>open override val [message](message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [statusCode](status-code.md) | [common]<br>open override val [statusCode](status-code.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
