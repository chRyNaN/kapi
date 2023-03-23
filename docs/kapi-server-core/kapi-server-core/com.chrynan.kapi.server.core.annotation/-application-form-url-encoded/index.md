//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[ApplicationFormUrlEncoded](index.md)

# ApplicationFormUrlEncoded

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [[AnnotationTarget.FUNCTION](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-annotation-target/-f-u-n-c-t-i-o-n/index.html)])

@[Consumes](../-consumes/index.md)(contentType = &quot;application/x-www-form-urlencoded&quot;)

annotation class [ApplicationFormUrlEncoded](index.md)

Indicates that the annotated API function's request body will use form URL encoding (application/x-www-form-urlencoded). Fields should be declared as parameters and annotated with [Field](../-field/index.md).

## Extensions

| Name | Summary |
|---|---|
| [contentType](../content-type.md) | [common]<br>val [ApplicationFormUrlEncoded](index.md).[contentType](../content-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Represents the [Consumes.contentType](../-consumes/content-type.md) value for the [ApplicationFormUrlEncoded](index.md) annotation. |
