//[kapi-core](../../index.md)/[com.chrynan.kapi.core](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [ApiError](-api-error/index.md) | [common]<br>@Serializable<br>data class [ApiError](-api-error/index.md)(val type: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;about:blank&quot;, val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val details: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val status: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, val instance: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val timestamp: Instant? = null, val help: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val signature: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)<br>Represents an API error response body. This loosely conforms to the [RFC-7807 specification](https://www.rfc-editor.org/rfc/rfc7807). |
| [HttpMethod](-http-method/index.md) | [common]<br>@Serializable<br>enum [HttpMethod](-http-method/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[HttpMethod](-http-method/index.md)&gt; |
| [Kapi](-kapi/index.md) | [common]<br>class [Kapi](-kapi/index.md) |
| [Response](-response/index.md) | [common]<br>interface [Response](-response/index.md)&lt;[T](-response/index.md)&gt;<br>Represents a response from an HTTP API request. This is similar to the [Retrofit Response](https://github.com/square/retrofit/blob/master/retrofit/src/main/java/retrofit2/Response.java) class but can wrap an HttpResponse from the Ktor library. |

## Functions

| Name | Summary |
|---|---|
| [bodyOrNull](body-or-null.md) | [common]<br>suspend fun &lt;[T](body-or-null.md)&gt; [Response](-response/index.md)&lt;[T](body-or-null.md)&gt;.[bodyOrNull](body-or-null.md)(): [T](body-or-null.md)?<br>Retrieves the [Response.body](-response/body.md) model or `null` if an exception was thrown. |
| [error](error.md) | [common]<br>inline suspend fun &lt;[T](error.md), [E](error.md)&gt; [Response](-response/index.md)&lt;[T](error.md)&gt;.[error](error.md)(): [E](error.md)<br>The deserialized response body of an unsuccessful HTTP request. |
| [errorOrNull](error-or-null.md) | [common]<br>inline suspend fun &lt;[T](error-or-null.md), [E](error-or-null.md)&gt; [Response](-response/index.md)&lt;[T](error-or-null.md)&gt;.[errorOrNull](error-or-null.md)(): [E](error-or-null.md)?<br>suspend fun &lt;[T](error-or-null.md), [E](error-or-null.md)&gt; [Response](-response/index.md)&lt;[T](error-or-null.md)&gt;.[errorOrNull](error-or-null.md)(typeInfo: TypeInfo): [E](error-or-null.md)?<br>Retrieves the [error](error.md) model or `null` if there was no [error](error.md). |
