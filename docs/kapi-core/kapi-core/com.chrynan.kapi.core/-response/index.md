//[kapi-core](../../../index.md)/[com.chrynan.kapi.core](../index.md)/[Response](index.md)

# Response

[common]\
interface [Response](index.md)&lt;[T](index.md)&gt;

Represents a response from an HTTP API request. This is similar to the [Retrofit Response](https://github.com/square/retrofit/blob/master/retrofit/src/main/java/retrofit2/Response.java) class but can wrap an HttpResponse from the Ktor library.

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [body](body.md) | [common]<br>abstract suspend fun [body](body.md)(): [T](index.md)<br>The deserialized response body of a successful HTTP request. |
| [code](code.md) | [common]<br>abstract fun [code](code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>The HTTP Status code. |
| [error](error.md) | [common]<br>abstract suspend fun &lt;[E](error.md)&gt; [error](error.md)(typeInfo: TypeInfo): [E](error.md)<br>The deserialized response body of an unsuccessful HTTP request. |
| [headers](headers.md) | [common]<br>abstract fun [headers](headers.md)(): Headers<br>The HTTP Headers. |
| [isSuccessful](is-successful.md) | [common]<br>open fun [isSuccessful](is-successful.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Returns `true` if the [code](code.md) falls within the range 200 (inclusive) to 300 (exclusive), `false` otherwise. |
| [message](message.md) | [common]<br>abstract fun [message](message.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>The HTTP Status message or `null` if unknown. |
| [raw](raw.md) | [common]<br>abstract fun [raw](raw.md)(): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?<br>The raw Http Response value that this interface wraps, or `null` if this implementation does not wrap another Http Response value. |

## Extensions

| Name | Summary |
|---|---|
| [bodyOrNull](../body-or-null.md) | [common]<br>suspend fun &lt;[T](../body-or-null.md)&gt; [Response](index.md)&lt;[T](../body-or-null.md)&gt;.[bodyOrNull](../body-or-null.md)(): [T](../body-or-null.md)?<br>Retrieves the [Response.body](body.md) model or `null` if an exception was thrown. |
| [error](../error.md) | [common]<br>inline suspend fun &lt;[T](../error.md), [E](../error.md)&gt; [Response](index.md)&lt;[T](../error.md)&gt;.[error](../error.md)(): [E](../error.md)<br>The deserialized response body of an unsuccessful HTTP request. |
| [errorOrNull](../error-or-null.md) | [common]<br>suspend fun &lt;[T](../error-or-null.md), [E](../error-or-null.md)&gt; [Response](index.md)&lt;[T](../error-or-null.md)&gt;.[errorOrNull](../error-or-null.md)(typeInfo: TypeInfo): [E](../error-or-null.md)?<br>inline suspend fun &lt;[T](../error-or-null.md), [E](../error-or-null.md)&gt; [Response](index.md)&lt;[T](../error-or-null.md)&gt;.[errorOrNull](../error-or-null.md)(): [E](../error-or-null.md)?<br>Retrieves the [error](../error.md) model or `null` if there was no [error](../error.md). |
