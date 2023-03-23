//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[ServerVariable](index.md)

# ServerVariable

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [])

annotation class [ServerVariable](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val defaultValue: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val allowableValues: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = [])

Represents a server variable for server URL template substitution. This corresponds to the ServerVariable model from the OpenAPI Specification.

Note that blank [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) properties are equivalent to `null`. This is due to Kotlin annotations not allowing nullable properties, so blank strings can be defaults. Therefore, required [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) fields must not be blank.

## Properties

| Name | Summary |
|---|---|
| [allowableValues](allowable-values.md) | [common]<br>val [allowableValues](allowable-values.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>An array of allowable values for this variable. |
| [defaultValue](default-value.md) | [common]<br>val [defaultValue](default-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The default value of this variable. |
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>An optional description for the server variable. If this value is blank, it is considered `null`. |
| [name](name.md) | [common]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The name of this variable. |
