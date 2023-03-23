//[kapi-server-core](../../index.md)/[com.chrynan.kapi.server.core](index.md)/[error](error.md)

# error

[common]\
inline fun &lt;[T](error.md)&gt; [Response.Companion](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/-companion/index.md).[error](error.md)(statusCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, error: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), headers: Headers = Headers.Empty): [HttpResponse](-http-response/index.md)&lt;[T](error.md)&gt;

Creates an instance of [HttpResponse](-http-response/index.md) for an error from the provided values.
