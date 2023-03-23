//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[GET](index.md)

# GET

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [[AnnotationTarget.FUNCTION](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-annotation-target/-f-u-n-c-t-i-o-n/index.html)])

annotation class [GET](index.md)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

This annotation is provided on a function that represents an API function that responds to HTTP GET requests. An API function can only be annotated with one HTTP method annotation.

##  Example

```kotlin
@GET("/user/{id}")
suspend fun getUser(@Path("id") id: String)
```

## Properties

| Name | Summary |
|---|---|
| [path](path.md) | [common]<br>val [path](path.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The relative or absolute URL path of the API endpoint that the annotated function responds to. The [Api.basePath](../-api/base-path.md) value from the containing component with the [Api](../-api/index.md) annotation will be prepended to this value to make the full path, if it is not blank. A value for this property must be provided, but it may be blank, which indicates that the path is the [Api.basePath](../-api/base-path.md) value or the root path of the server. The code generation tools should appropriately handle leading and trailing forward slash characters (&quot;/&quot;) when creating the endpoints, so a preceding &quot;/&quot; character in the [path](path.md) value should be equivalent to no preceding slash character, and likewise for trailing slash characters. A blank value and a value containing only a forward slash character are considered equivalent. Path values may contain variables which can be extracted and provided to the API function as a parameter using the [Path](../-path/index.md) annotation. A path variable is surrounded by a leading &quot;{&quot; character and a trailing &quot;}&quot; character. For example, the following path value contains a path parameter with a name of &quot;id&quot;: &quot;/user/{id}&quot;. |
