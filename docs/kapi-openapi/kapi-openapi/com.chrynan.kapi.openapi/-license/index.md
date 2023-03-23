//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[License](index.md)

# License

[common]\
@Serializable

data class [License](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

License information for the exposed API.

##  License Object Example

```json
{
  "name": "Apache 2.0",
  "identifier": "Apache-2.0"
}
```

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#license-object) |

## Constructors

| | |
|---|---|
| [License](-license.md) | [common]<br>fun [License](-license.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [identifier](identifier.md) | [common]<br>val [identifier](identifier.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>An [SPDX](https://spdx.dev/spdx-specification-21-web-version/#h.jxpfx0ykyb60) license expression for the API. The identifier field is mutually exclusive of the url field. |
| [name](name.md) | [common]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>**REQUIRED**. The license name used for the API. |
| [url](url.md) | [common]<br>val [url](url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A URL to the license used for the API. This MUST be in the form of a URL. The url field is mutually exclusive of the identifier field. |
