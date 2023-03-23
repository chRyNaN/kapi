//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[Contact](index.md)

# Contact

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [])

annotation class [Contact](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

Represents contact information for an API. This class mirrors the Contact model from the OpenAPI Specification and can be used in the generation of an OpenAPI Specification model.

Note that blank [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) fields are equivalent to `null` and an instance with all blank fields can be considered equivalent to a `null` instance. This is due to Kotlin annotations not allowing nullable fields, so blank strings are defaults.

##  Example

```kotlin
Contact(
    name = "API Support",
    url = "https://www.example.com/support",
    email = "support@example.com")
```

## Properties

| Name | Summary |
|---|---|
| [email](email.md) | [common]<br>val [email](email.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The email address of the contact person/organization. This MUST be in the form of an email address. |
| [name](name.md) | [common]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The identifying name of the contact person/organization. |
| [url](url.md) | [common]<br>val [url](url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The URL pointing to the contact information. This MUST be in the form of a URL. |
