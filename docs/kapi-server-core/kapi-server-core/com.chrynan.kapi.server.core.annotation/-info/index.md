//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[Info](index.md)

# Info

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [])

annotation class [Info](index.md)(val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val termsOfService: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val contact: [Contact](../-contact/index.md) = Contact(), val license: [License](../-license/index.md) = License(), val version: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

Represents information about an API. This class mirrors the Info model from the OpenAPI Specification (with some subtle differences) and can be used in the generation of an OpenAPI Specification model.

Note that blank fields are equivalent to `null` and an instance with all blank fields can be considered equivalent to a `null` instance. This is due to Kotlin annotations not allowing nullable fields, so blank strings are defaults.

## Properties

| Name | Summary |
|---|---|
| [contact](contact.md) | [common]<br>val [contact](contact.md): [Contact](../-contact/index.md)<br>The contact information for the exposed API. |
| [license](license.md) | [common]<br>val [license](license.md): [License](../-license/index.md)<br>The license information for the exposed API. |
| [summary](summary.md) | [common]<br>val [summary](summary.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>A short summary of the API. |
| [termsOfService](terms-of-service.md) | [common]<br>val [termsOfService](terms-of-service.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>A URL to the Terms of Service for the API. This MUST be in the form of a URL. |
| [title](title.md) | [common]<br>val [title](title.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The title of the API. If this value is blank, it is considered `null` and the [Api.name](../-api/name.md) or name of the annotated component will be used as a default. |
| [version](version.md) | [common]<br>val [version](version.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The version of this API. |
