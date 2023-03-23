//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[ApplicationJson](index.md)

# ApplicationJson

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [[AnnotationTarget.FUNCTION](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-annotation-target/-f-u-n-c-t-i-o-n/index.html)])

@[Consumes](../-consumes/index.md)(contentType = &quot;application/json&quot;)

annotation class [ApplicationJson](index.md)

Indicates that the annotated API function's request body will use JSON encoding (application/json). Request body fields should be a single parameter annotated with [Body](../-body/index.md) of a serializable type.

## Extensions

| Name | Summary |
|---|---|
| [contentType](../content-type.md) | [common]<br>val [ApplicationJson](index.md).[contentType](../content-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Represents the [Consumes.contentType](../-consumes/content-type.md) value for the [ApplicationJson](index.md) annotation. |
