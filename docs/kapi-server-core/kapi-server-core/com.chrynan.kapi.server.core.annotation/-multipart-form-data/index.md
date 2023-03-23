//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[MultipartFormData](index.md)

# MultipartFormData

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [[AnnotationTarget.FUNCTION](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-annotation-target/-f-u-n-c-t-i-o-n/index.html)])

@[Consumes](../-consumes/index.md)(contentType = &quot;multipart/form-data&quot;)

annotation class [MultipartFormData](index.md)

Indicates that the annotated API function's request body will use multipart form data encoding (multipart/form-data). Fields should be declared as parameters and annotated with [Part](../-part/index.md).

## Extensions

| Name | Summary |
|---|---|
| [contentType](../content-type.md) | [common]<br>val [MultipartFormData](index.md).[contentType](../content-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Represents the [Consumes.contentType](../-consumes/content-type.md) value for the [MultipartFormData](index.md) annotation. |
