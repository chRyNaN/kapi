//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[Produces](index.md)

# Produces

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [[AnnotationTarget.FUNCTION](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-annotation-target/-f-u-n-c-t-i-o-n/index.html)])

annotation class [Produces](index.md)(val success: [Success](../-success/index.md) = Success(), val errors: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Error](../-error/index.md)&lt;*&gt;&gt; = [])

Represents information about the possible HTTP responses of an API function.

## Properties

| Name | Summary |
|---|---|
| [errors](errors.md) | [common]<br>val [errors](errors.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Error](../-error/index.md)&lt;*&gt;&gt;<br>Represents the possible HTTP error responses from this API function. When an [Error.exception](../-error/exception.md) is thrown from the API function, it is caught and an [ApiError](../../../../kapi-core/kapi-core/com.chrynan.kapi.core/-api-error/index.md) will be emitted as the HTTP response, using the values provided in the [Error](../-error/index.md) instance. |
| [success](success.md) | [common]<br>val [success](success.md): [Success](../-success/index.md)<br>Represents the successful HTTP response from this API function. This will be used in conjunction with the API function's return type to determine the HTTP response of the API function. The default value is an empty [Success](../-success/index.md) instance with a [Success.statusCode](../-success/status-code.md) value of `200`. |
