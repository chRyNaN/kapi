//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[ApiInfo](index.md)

# ApiInfo

[jvm]\
@Serializable

data class [ApiInfo](index.md)(val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val termsOfService: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val contact: [ApiInfo.Contact](-contact/index.md)? = null, val license: [ApiInfo.License](-license/index.md)? = null, val version: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

Represents information about an API and directly corresponds to the @Info annotation provided to the @Api annotation.

## Constructors

| | |
|---|---|
| [ApiInfo](-api-info.md) | [jvm]<br>fun [ApiInfo](-api-info.md)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, termsOfService: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, contact: [ApiInfo.Contact](-contact/index.md)? = null, license: [ApiInfo.License](-license/index.md)? = null, version: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Types

| Name | Summary |
|---|---|
| [Contact](-contact/index.md) | [jvm]<br>@Serializable<br>data class [Contact](-contact/index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |
| [License](-license/index.md) | [jvm]<br>@Serializable<br>data class [License](-license/index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [contact](contact.md) | [jvm]<br>val [contact](contact.md): [ApiInfo.Contact](-contact/index.md)? = null |
| [license](license.md) | [jvm]<br>val [license](license.md): [ApiInfo.License](-license/index.md)? = null |
| [summary](summary.md) | [jvm]<br>val [summary](summary.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [termsOfService](terms-of-service.md) | [jvm]<br>val [termsOfService](terms-of-service.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [title](title.md) | [jvm]<br>val [title](title.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [version](version.md) | [jvm]<br>val [version](version.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
