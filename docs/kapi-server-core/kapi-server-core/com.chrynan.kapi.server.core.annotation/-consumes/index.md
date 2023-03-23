//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[Consumes](index.md)

# Consumes

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [[AnnotationTarget.FUNCTION](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-annotation-target/-f-u-n-c-t-i-o-n/index.html), [AnnotationTarget.ANNOTATION_CLASS](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-annotation-target/-a-n-n-o-t-a-t-i-o-n_-c-l-a-s-s/index.html)])

annotation class [Consumes](index.md)(val contentType: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Defines the content types that an API function can accept for the request body. This annotation can be applied directly to an API function to indicate which content types that function accepts for the request body, like in the following example:

```kotlin
@Consumes("application/x-www-form-urlencoded")
suspend fun getItem(@Field id: String): Item
```

Or, this annotation can be applied to another annotation class definition to indicate that any function with that annotation accepts those specified types for the request body. For example:

```kotlin
@Consumes("application/x-www-form-urlencoded")
@Target(AnnotationTarget.FUNCTION)
annotation class FormUrlEncoded

@Api
interface ItemApi {

    @FormUrlEncoded
    suspend fun getItem(@Field id: String): Item
}
```

Both of the approaches illustrated above are functionally equivalent.

**Note:** That only a single [Consumes](index.md) annotation, or an annotation annotated with [Consumes](index.md), is allowed on an API function. Multiple of these annotations for a single API function will result in a compile-time error.

**Note:** That a [Body](../-body/index.md) parameter for an API function must be a supported type that works with the specified [contentType](content-type.md).

## Properties

| Name | Summary |
|---|---|
| [contentType](content-type.md) | [common]<br>val [contentType](content-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The supported content type accepted by an API function. The content type specified must be a single non-blank valid value. A blank content type provided results in a default value of `&#42;/&#42;`, which means it relies on the server framework's content negotiation support (which may allow any type or specific types). However, since the server framework's content negotiation support is separate from this library, it will be registered in any generated documentation as supporting any type. |
