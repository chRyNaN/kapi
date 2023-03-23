//[kapi-openapi](../../../index.md)/[com.chrynan.kapi.openapi](../index.md)/[OAuthFlow](index.md)

# OAuthFlow

[common]\
@Serializable

data class [OAuthFlow](index.md)(val authorizationUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val tokenUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val refreshUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val scopes: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;)

Configuration details for a supported OAuth Flow

This object MAY be extended with [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).

##  OAuthFlow Object Example

```json
{
    "authorizationUrl": "https://example.com/api/oauth/dialog",
    "scopes": {
        "write:pets": "modify pets in your account",
        "read:pets": "read your pets"
}
```

#### See also

common

| | |
|---|---|
|  | [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#oauth-flow-object) |

## Constructors

| | |
|---|---|
| [OAuthFlow](-o-auth-flow.md) | [common]<br>fun [OAuthFlow](-o-auth-flow.md)(authorizationUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tokenUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, refreshUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), scopes: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) |

## Properties

| Name | Summary |
|---|---|
| [authorizationUrl](authorization-url.md) | [common]<br>val [authorizationUrl](authorization-url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>**REQUIRED**. The authorization URL to be used for this flow. This MUST be in the form of a URL. The OAuth2 standard requires the use of TLS. Applies to `oauth2 ("implicit", "authorizationCode")`. |
| [refreshUrl](refresh-url.md) | [common]<br>val [refreshUrl](refresh-url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The URL to be used for obtaining refresh tokens. This MUST be in the form of a URL. The OAuth2 standard requires the use of TLS. Applies to `oauth2`. |
| [scopes](scopes.md) | [common]<br>val [scopes](scopes.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>**REQUIRED**. The available scopes for the OAuth2 security scheme. A map between the scope name and a short description for it. The map MAY be empty. Applies to `oauth2`. |
| [tokenUrl](token-url.md) | [common]<br>val [tokenUrl](token-url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>**REQUIRED**. The token URL to be used for this flow. This MUST be in the form of a URL. The OAuth2 standard requires the use of TLS. Applies to `oauth2 ("password", "clientCredentials", "authorizationCode")`. |
