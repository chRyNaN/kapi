//[kapi-core](../../../index.md)/[com.chrynan.kapi.core](../index.md)/[ApiError](index.md)

# ApiError

[common]\
@Serializable

data class [ApiError](index.md)(val type: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;about:blank&quot;, val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val details: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val status: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, val instance: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val timestamp: Instant? = null, val help: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val signature: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

Represents an API error response body. This loosely conforms to the [RFC-7807 specification](https://www.rfc-editor.org/rfc/rfc7807).

## Constructors

| | |
|---|---|
| [ApiError](-api-error.md) | [common]<br>fun [ApiError](-api-error.md)(type: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;about:blank&quot;, title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), details: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, status: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, instance: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, timestamp: Instant? = null, help: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, signature: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [details](details.md) | [common]<br>val [details](details.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A human-readable explanation specific to this occurrence of the problem. This should focus on helping the client correct the issue, rather than giving debugging information. Clients should not parse the [details](details.md) field for information. |
| [help](help.md) | [common]<br>val [help](help.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A URI reference to use to obtain more information about the error. |
| [instance](instance.md) | [common]<br>val [instance](instance.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A URI reference that identifies the specific occurrence of the problem. It may or may not yield further information if de-referenced. Accepts relative URIs. |
| [signature](signature.md) | [common]<br>val [signature](signature.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>A message signature value that can be used to determine that the error is correct and hasn't been tampered with. |
| [status](status.md) | [common]<br>val [status](status.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null<br>The HTTP status code ([RFC7231 Section 6](https://www.rfc-editor.org/rfc/rfc7231#section-6)) generated by the origin server for this occurrence of the problem. |
| [timestamp](timestamp.md) | [common]<br>val [timestamp](timestamp.md): Instant? = null<br>The Instant the error occurred. |
| [title](title.md) | [common]<br>val [title](title.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>A short, human-readable summary of the problem type. It SHOULD NOT change from occurrence to occurrence of the problem, except for purposes of localization (e.g., using proactive content negotiation; see [RFC7231, Section 3.4](https://www.rfc-editor.org/rfc/rfc7231#section-3.4)). |
| [type](type.md) | [common]<br>val [type](type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>A URI reference [RFC3986](https://www.rfc-editor.org/rfc/rfc3986) that identifies the problem type. This specification encourages that, when de-referenced, it provides human-readable documentation for the problem type (e.g., using HTML [W3C.REC-html5-20141028](https://www.rfc-editor.org/rfc/rfc7807#ref-W3C.REC-html5-20141028)). When this member is not present, its value is assumed to be &quot;about:blank&quot;. Accepts relative URIs. Consumers MUST use the [type](type.md) as the primary identifier for the problem type. The [title](title.md) is advisory and included only for users who are not aware of the semantics of the URI and do not have the ability to discover them (e.g., offline log analysis). Consumers SHOULD NOT automatically dereference the type URI. |