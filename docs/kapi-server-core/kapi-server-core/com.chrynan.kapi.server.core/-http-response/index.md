//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core](../index.md)/[HttpResponse](index.md)

# HttpResponse

[common]\
interface [HttpResponse](index.md)&lt;[T](index.md)&gt; : [Response](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md)&lt;[T](index.md)&gt; 

A [Response](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) instance that wraps a [HttpResponseData](../-http-response-data/index.md) model.

## Functions

| Name | Summary |
|---|---|
| [body](index.md#767346090%2FFunctions%2F444262181) | [common]<br>abstract suspend fun [body](index.md#767346090%2FFunctions%2F444262181)(): [T](index.md) |
| [code](index.md#840051647%2FFunctions%2F444262181) | [common]<br>abstract fun [code](index.md#840051647%2FFunctions%2F444262181)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [error](index.md#-1332476271%2FFunctions%2F444262181) | [common]<br>abstract suspend fun &lt;[E](index.md#-1332476271%2FFunctions%2F444262181)&gt; [error](index.md#-1332476271%2FFunctions%2F444262181)(typeInfo: TypeInfo): [E](index.md#-1332476271%2FFunctions%2F444262181) |
| [headers](index.md#921059144%2FFunctions%2F444262181) | [common]<br>abstract fun [headers](index.md#921059144%2FFunctions%2F444262181)(): Headers |
| [isSuccessful](index.md#2122135336%2FFunctions%2F444262181) | [common]<br>open fun [isSuccessful](index.md#2122135336%2FFunctions%2F444262181)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [message](index.md#67604967%2FFunctions%2F444262181) | [common]<br>abstract fun [message](index.md#67604967%2FFunctions%2F444262181)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [raw](raw.md) | [common]<br>abstract override fun [raw](raw.md)(): [HttpResponseData](../-http-response-data/index.md)&lt;[T](index.md)&gt; |

## Extensions

| Name | Summary |
|---|---|
| [rawHttpResponseData](../raw-http-response-data.md) | [common]<br>fun &lt;[T](../raw-http-response-data.md)&gt; [Response](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md)&lt;[T](../raw-http-response-data.md)&gt;.[rawHttpResponseData](../raw-http-response-data.md)(): [HttpResponseData](../-http-response-data/index.md)&lt;[T](../raw-http-response-data.md)&gt;?<br>Retrieves the raw [HttpResponseData](../-http-response-data/index.md) from this [Response](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) model if this [Response](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) model is a [HttpResponse](index.md) instance. |
