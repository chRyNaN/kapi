//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[Success](index.md)

# Success

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [])

annotation class [Success](index.md)(val statusCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 200, val contentType: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val headers: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[ResponseHeader](../-response-header/index.md)&gt; = [])

Represents a successful response from an API function. The HTTP response body is defined by the return type of the API function, but the [contentType](content-type.md) can be

## Properties

| Name | Summary |
|---|---|
| [contentType](content-type.md) | [common]<br>val [contentType](content-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The supported content type returned by an API function. The content type specified must be a single non-blank valid value. A blank content type provided results in a default value of `&#42;/&#42;`, which means it relies on the server framework's content negotiation support (which may allow any type or specific types). However, since the server framework's content negotiation support is separate from this library, it will be registered in any generated documentation as supporting any type. |
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The description of this response. If this is blank, a default description will be added to the generated documentation based on the [statusCode](status-code.md) value. |
| [headers](headers.md) | [common]<br>val [headers](headers.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[ResponseHeader](../-response-header/index.md)&gt;<br>The [ResponseHeader](../-response-header/index.md)s that are appended to this response. |
| [statusCode](status-code.md) | [common]<br>val [statusCode](status-code.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 200<br>The HTTP status code value to respond with when this successful response is returned. Defaults to 200 OK. If the return type of the API function is [Response](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-response/index.md), then the [Response.code](../../com.chrynan.kapi.server.core/-http-response/index.md#840051647%2FFunctions%2F444262181) value will override this value. It is important to keep these values in sync with each other as the value specified here will be used when generating documentation. |
