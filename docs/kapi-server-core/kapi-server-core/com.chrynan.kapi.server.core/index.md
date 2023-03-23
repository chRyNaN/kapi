//[kapi-server-core](../../index.md)/[com.chrynan.kapi.server.core](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [HttpResponse](-http-response/index.md) | [common]<br>interface [HttpResponse](-http-response/index.md)&lt;[T](-http-response/index.md)&gt; : [Response](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md)&lt;[T](-http-response/index.md)&gt; <br>A [Response](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) instance that wraps a [HttpResponseData](-http-response-data/index.md) model. |
| [HttpResponseData](-http-response-data/index.md) | [common]<br>sealed class [HttpResponseData](-http-response-data/index.md)&lt;[T](-http-response-data/index.md)&gt;<br>A wrapper around the data associated with an HTTP response. |

## Functions

| Name | Summary |
|---|---|
| [error](error.md) | [common]<br>inline fun &lt;[T](error.md)&gt; [Response.Companion](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/-companion/index.md).[error](error.md)(statusCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, error: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), headers: Headers = Headers.Empty): [HttpResponse](-http-response/index.md)&lt;[T](error.md)&gt;<br>Creates an instance of [HttpResponse](-http-response/index.md) for an error from the provided values. |
| [rawHttpResponseData](raw-http-response-data.md) | [common]<br>fun &lt;[T](raw-http-response-data.md)&gt; [Response](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md)&lt;[T](raw-http-response-data.md)&gt;.[rawHttpResponseData](raw-http-response-data.md)(): [HttpResponseData](-http-response-data/index.md)&lt;[T](raw-http-response-data.md)&gt;?<br>Retrieves the raw [HttpResponseData](-http-response-data/index.md) from this [Response](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) model if this [Response](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) model is a [HttpResponse](-http-response/index.md) instance. |
| [success](success.md) | [common]<br>inline fun &lt;[T](success.md)&gt; [Response.Companion](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/-companion/index.md).[success](success.md)(statusCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, body: [T](success.md), headers: Headers = Headers.Empty): [HttpResponse](-http-response/index.md)&lt;[T](success.md)&gt;<br>Creates an instance of [HttpResponse](-http-response/index.md) for a success from the provided values. |
