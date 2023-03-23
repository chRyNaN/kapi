//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[Part](index.md)

# Part

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [[AnnotationTarget.VALUE_PARAMETER](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-annotation-target/-v-a-l-u-e_-p-a-r-a-m-e-t-e-r/index.html)])

annotation class [Part](index.md)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val encoding: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

The parameter of an API function annotated with [Part](index.md) represents a single part, or all parts, of a multipart request. A [Field](../-field/index.md) parameter can be one of the following types:

- 
   Kotlin Primitive
- 
   io.ktor.http.content.PartData(.*) (Ktor)
- 
   io.ktor.http.content.MultiPartData (Ktor)
- 
   java.io.InputStream
- 
   io.ktor.utils.io.core.Input (Ktor)
- 
   io.ktor.utils.io.ByteReadChannel (Ktor)
- 
   kotlin.ByteArray

**Note:** To use this annotation on a parameter in an API function, the API function has to be annotated with [MultipartFormData](../-multipart-form-data/index.md).

**Note:** That this annotation cannot be provided on a parameter of an API function if a [Field](../-field/index.md) or [Body](../-body/index.md) annotation is present on a parameter of that same API function.

## Properties

| Name | Summary |
|---|---|
| [encoding](encoding.md) | [common]<br>val [encoding](encoding.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The MIME type or Content-Transfer-Encoding of this part. |
| [value](value.md) | [common]<br>val [value](value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The name of the part. If this value is blank, then the name of the parameter will be used instead. In the case that this represents all the parts of a multipart request, then this value can be blank as a name is not needed. |
