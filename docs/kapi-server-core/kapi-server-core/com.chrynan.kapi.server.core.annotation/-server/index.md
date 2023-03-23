//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[Server](index.md)

# Server

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [])

annotation class [Server](index.md)(val url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val variables: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[ServerVariable](../-server-variable/index.md)&gt; = [])

Represents a server endpoint hosting an instance of an API. This corresponds to the Server model from the OpenAPI Specification.

Note that blank [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) properties are equivalent to `null`. This is due to Kotlin annotations not allowing nullable properties, so blank strings can be defaults. Therefore, required [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) fields must not be blank.

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>An optional string describing the host designated by the URL. CommonMark syntax MAY be used for rich text representation. If this value is blank, it is considered `null`. |
| [url](url.md) | [common]<br>val [url](url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>A URL to the target host. This URL supports Server Variables. Variable substitutions will be made when a variable is named in {brackets}. |
| [variables](variables.md) | [common]<br>val [variables](variables.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[ServerVariable](../-server-variable/index.md)&gt;<br>An array of variables used for substitution in the server's URL template. |
