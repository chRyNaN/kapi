//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[Api](index.md)

# Api

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [[AnnotationTarget.CLASS](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-annotation-target/-c-l-a-s-s/index.html)])

annotation class [Api](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val basePath: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val info: [Info](../-info/index.md) = Info(), val servers: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Server](../-server/index.md)&gt; = [], val tags: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Tag](../-tag/index.md)&gt; = [])

Represents an API component. A component annotated with [Api](index.md) can have code auto-generated depending on the components structure, such as Ktor bindings and OpenAPI Specification files. A component annotated with this [Api](index.md) annotation can be a Kotlin interface, class, abstract class, or an object. Since the implementation of the component is defined by the developer, it doesn't matter from the perspective of the auto-generation tools whether the component is a class or an interface for example.

A component annotated with [Api](index.md) contains annotated functions which define the various HTTP methods of the API. This allows the API to be written idiomatically in Kotlin without conforming to particularly framework structures. For instance, the return type, of an API function, is the HTTP response body, instead of having to explicitly respond with an HTTP body using framework or library specific concepts within the function. This allows the developer to write familiar Kotlin code for their APIs.

Note that blank [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) properties are equivalent to `null`. This is due to Kotlin annotations not allowing nullable properties, so blank strings are defaults.

## Properties

| Name | Summary |
|---|---|
| [basePath](base-path.md) | [common]<br>val [basePath](base-path.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>A base path or route applied to each of this API component's functions before their paths are applied. For instance, if this value is equal to &quot;example&quot;, and this API component has an API function with a path value of &quot;test&quot;, then that API function's full path will be equal to &quot;example/test&quot;. If this value is blank, it is considered `null` and there will be no base path prepended to the API functions path values. |
| [info](info.md) | [common]<br>val [info](info.md): [Info](../-info/index.md)<br>Information about this API. If all the fields in the provided [Info](../-info/index.md) object are blank or considered `null`, then this value will be equivalent to `null`. |
| [name](name.md) | [common]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The name of this API. This will be used in the generation of code and should be a value that can be used as a valid Kotlin class name. If this value is blank, it is considered `null` and the name of the annotated component will be used instead. |
| [servers](servers.md) | [common]<br>val [servers](servers.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Server](../-server/index.md)&gt;<br>An array of known servers that implement this API. |
| [tags](tags.md) | [common]<br>val [tags](tags.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Tag](../-tag/index.md)&gt;<br>An array of tags applied to this API. |
