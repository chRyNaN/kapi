//[kapi-client-core](../../../index.md)/[com.chrynan.kapi.client.core](../index.md)/[ClientResponse](index.md)

# ClientResponse

[common]\
interface [ClientResponse](index.md)&lt;[T](index.md)&gt; : [Response](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md)&lt;[T](index.md)&gt; 

A [Response](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) instance that wraps an HttpResponse value from Ktor, and, therefore, whose [raw](raw.md) function returns an HttpResponse.

## Functions

| Name | Summary |
|---|---|
| [body](index.md#767346090%2FFunctions%2F618853277) | [common]<br>abstract suspend fun [body](index.md#767346090%2FFunctions%2F618853277)(): [T](index.md) |
| [code](index.md#840051647%2FFunctions%2F618853277) | [common]<br>abstract fun [code](index.md#840051647%2FFunctions%2F618853277)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [error](index.md#-1332476271%2FFunctions%2F618853277) | [common]<br>abstract suspend fun &lt;[E](index.md#-1332476271%2FFunctions%2F618853277)&gt; [error](index.md#-1332476271%2FFunctions%2F618853277)(typeInfo: TypeInfo): [E](index.md#-1332476271%2FFunctions%2F618853277) |
| [headers](index.md#921059144%2FFunctions%2F618853277) | [common]<br>abstract fun [headers](index.md#921059144%2FFunctions%2F618853277)(): Headers |
| [isSuccessful](index.md#2122135336%2FFunctions%2F618853277) | [common]<br>open fun [isSuccessful](index.md#2122135336%2FFunctions%2F618853277)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [message](index.md#67604967%2FFunctions%2F618853277) | [common]<br>abstract fun [message](index.md#67604967%2FFunctions%2F618853277)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [raw](raw.md) | [common]<br>abstract override fun [raw](raw.md)(): HttpResponse |

## Extensions

| Name | Summary |
|---|---|
| [rawHttpResponse](../raw-http-response.md) | [common]<br>fun &lt;[T](../raw-http-response.md)&gt; [Response](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md)&lt;[T](../raw-http-response.md)&gt;.[rawHttpResponse](../raw-http-response.md)(): HttpResponse?<br>Retrieves the raw HttpResponse from this [Response](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) model if this [Response](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) model is a [ClientResponse](index.md) instance. |
