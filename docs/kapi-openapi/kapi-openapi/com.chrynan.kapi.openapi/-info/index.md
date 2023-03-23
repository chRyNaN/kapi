//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[Info](index.md)

# Info

[common]\
@Serializable

data class [Info](index.md)(val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val termsOfService: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val contact: [Contact](../-contact/index.md)? = null, val license: [License](../-license/index.md)? = null, val version: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

The object provides metadata about the API. The metadata MAY be used by the clients if needed, and MAY be presented in editing or documentation generation tools for convenience.

##  Info Object Example

```json
{
  "title": "Sample Pet Store App",
  "summary": "A pet store manager.",
  "description": "This is a sample server for a pet store.",
  "termsOfService": "https://example.com/terms/",
  "contact": {
    "name": "API Support",
    "url": "https://www.example.com/support",
    "email": "support@example.com"
  },
  "license": {
    "name": "Apache 2.0",
    "url": "https://www.apache.org/licenses/LICENSE-2.0.html"
  },
  "version": "1.0.1"
}
```

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#info-object) |

## Constructors

| | |
|---|---|
| [Info](-info.md) | [common]<br>fun [Info](-info.md)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), summary: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, termsOfService: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, contact: [Contact](../-contact/index.md)? = null, license: [License](../-license/index.md)? = null, version: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [contact](contact.md) | [common]<br>val [contact](contact.md): [Contact](../-contact/index.md)? = null<br>The contact information for the exposed API. |
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A description of the API. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation. |
| [license](license.md) | [common]<br>val [license](license.md): [License](../-license/index.md)? = null<br>The license information for the exposed API. |
| [summary](summary.md) | [common]<br>val [summary](summary.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A short summary of the API. |
| [termsOfService](terms-of-service.md) | [common]<br>val [termsOfService](terms-of-service.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A URL to the Terms of Service for the API. This MUST be in the form of a URL. |
| [title](title.md) | [common]<br>val [title](title.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>**REQUIRED**. The title of the API. |
| [version](version.md) | [common]<br>val [version](version.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>**REQUIRED**. The version of the OpenAPI document (which is distinct from the [OpenAPI Specification version](https://spec.openapis.org/oas/v3.1.0#oasVersion) or the API implementation version). |
