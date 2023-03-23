//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[License](index.md)

# License

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [])

annotation class [License](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

Represents license information for an API. This class mirrors the License model from the OpenAPI Specification and can be used in the generation of an OpenAPI Specification model.

Note that blank [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) fields are equivalent to `null` and an instance with all blank fields can be considered equivalent to a `null` instance. This is due to Kotlin annotations not allowing nullable fields, so blank strings can be defaults.

##  Example

```kotlin
License(
    name = "Apache 2.0",
    identifier = "Apache-2.0")
```

## Properties

| Name | Summary |
|---|---|
| [identifier](identifier.md) | [common]<br>val [identifier](identifier.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>An [SPDX](https://spdx.dev/spdx-specification-21-web-version/#h.jxpfx0ykyb60) license expression for the API. The identifier field is mutually exclusive of the url field. |
| [name](name.md) | [common]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>**REQUIRED**. The license name used for the API. |
| [url](url.md) | [common]<br>val [url](url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>A URL to the license used for the API. This MUST be in the form of a URL. The url field is mutually exclusive of the identifier field. |
