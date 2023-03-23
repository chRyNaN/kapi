//[kapi-client-core](../../index.md)/[com.chrynan.kapi.client.core](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [ClientResponse](-client-response/index.md) | [common]<br>interface [ClientResponse](-client-response/index.md)&lt;[T](-client-response/index.md)&gt; : [Response](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md)&lt;[T](-client-response/index.md)&gt; <br>A [Response](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) instance that wraps an HttpResponse value from Ktor, and, therefore, whose [raw](-client-response/raw.md) function returns an HttpResponse. |

## Functions

| Name | Summary |
|---|---|
| [of](of.md) | [common]<br>inline fun &lt;[T](of.md)&gt; [Response.Companion](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/-companion/index.md).[of](of.md)(httpResponse: HttpResponse): [ClientResponse](-client-response/index.md)&lt;[T](of.md)&gt;<br>fun &lt;[T](of.md)&gt; [Response.Companion](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/-companion/index.md).[of](of.md)(httpResponse: HttpResponse, typeInfo: TypeInfo): [ClientResponse](-client-response/index.md)&lt;[T](of.md)&gt;<br>Creates an instance of [Response](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) from the provided [httpResponse](of.md). |
| [rawHttpResponse](raw-http-response.md) | [common]<br>fun &lt;[T](raw-http-response.md)&gt; [Response](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md)&lt;[T](raw-http-response.md)&gt;.[rawHttpResponse](raw-http-response.md)(): HttpResponse?<br>Retrieves the raw HttpResponse from this [Response](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) model if this [Response](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) model is a [ClientResponse](-client-response/index.md) instance. |
| [toResponse](to-response.md) | [common]<br>inline fun &lt;[T](to-response.md)&gt; HttpResponse.[toResponse](to-response.md)(): [ClientResponse](-client-response/index.md)&lt;[T](to-response.md)&gt;<br>fun &lt;[T](to-response.md)&gt; HttpResponse.[toResponse](to-response.md)(typeInfo: TypeInfo): [ClientResponse](-client-response/index.md)&lt;[T](to-response.md)&gt;<br>Creates an instance of [Response](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md) from this HttpResponse. |
