//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[ContentNegotiation](index.md)

# ContentNegotiation

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [[AnnotationTarget.FUNCTION](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-annotation-target/-f-u-n-c-t-i-o-n/index.html)])

@[Consumes](../-consumes/index.md)(contentType = &quot;&quot;)

annotation class [ContentNegotiation](index.md)

Indicates that the annotated API function's request body will use content negotiation that should already be set up with the server framework. Request body fields should be a single parameter annotated with [Body](../-body/index.md)  of a serializable type.

## Extensions

| Name | Summary |
|---|---|
| [contentType](../content-type.md) | [common]<br>val [ContentNegotiation](index.md).[contentType](../content-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Represents the [Consumes.contentType](../-consumes/content-type.md) value for the [ContentNegotiation](index.md) annotation. The actual value used in the [Consumes](../-consumes/index.md) annotation is an empty [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) since annotation properties cannot have null values, but that is essentially equivalent to `null` and will be converted to it. |
